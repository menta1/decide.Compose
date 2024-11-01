package com.decide.app.account.statisticsClient

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.decide.app.account.SINGLE
import com.decide.app.account.data.AccountRepositoryImpl.Companion.KEY_USER_ID
import com.decide.app.account.statisticsClient.modals.GlobalStatistics
import com.decide.app.account.statisticsClient.modals.GlobalTemperament
import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.entities.StatisticEntity
import com.decide.app.database.remote.RemoteAssayStorageImpl.Companion.ANXIETY
import com.decide.app.database.remote.RemoteAssayStorageImpl.Companion.DEPRESSION
import com.decide.app.database.remote.RemoteAssayStorageImpl.Companion.STATISTICS
import com.decide.app.database.remote.RemoteAssayStorageImpl.Companion.TEMPERAMENT
import com.decide.app.database.remote.RemoteAssayStorageImpl.Companion.USERS
import com.decide.app.database.remote.dto.StatisticDTO
import com.decide.app.database.remote.exceptions.DecideDatabaseException
import com.decide.app.database.remote.exceptions.firestoreExceptionMapper
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import java.util.Date
import javax.inject.Inject

class StatisticsClientImpl @Inject constructor(
    private val remoteDatabase: FirebaseFirestore,
    private val localStorage: AppDatabase,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO),
    private val dataStore: DataStore<Preferences>
) : StatisticsClient {

    /**
     * ПОКА ЧТО ПРОПУСКАЮТСЯ РЕЗУЛЬТАТЫ С количеством KEYRESULT БОЛЬШЕ 1
     *
     * Берутся результаты с базы данных Assay куда сохраняются последние 5 результата
     * После этого берется Statistics в который записываются данные которые были внесены в общую базу
     * Сравниваются результаты Assay с Statistics
     * Если результат с Assay еще нет в Statistics, значит в общую базу добавляется +1
     * Если результаты одинаковые, тогда пропускается запись данного результата
     * Если разные результаты, то надо удалить -1 по ID с общей базы по KeyResult
     * И после внести новую запись по KeyResult новый результат
     */
    override suspend fun updateStatistic() {
        val forStatisticAnxiety = setOf(8, 9, 15)//тесты по тревожности
        //Оценка реактивной тревожности
        //Оценка личностной тревожности
        //Личностная шкала проявления тревоги (Дж. Тейлор)
        val forStatisticTemperament = 4//тест по темпераменту
        //Личностный тест Айзенка (EPi)
        val forStatisticDepression = setOf(5, 6)//тест по депрессии
        //Тест тревожности и депрессии
        //Шкала депрессии
        var countTestAnxiety = 0
        var countTestDepression = 0
        val getAllResults = localStorage.assayDao().getAllAssays()
        val statisticsAll = localStorage.statisticsDao().getAll()
        coroutineScope.launch {
            var resultAnxiety = 0.0//Счетчик тревожности
            var resultTemperament = 0.0//Счетчик темперамент
            var resultDepression = 0.0//Счетчик депрессии

            getAllResults.forEach { result ->

                if (result.id in forStatisticAnxiety && result.results.isNotEmpty()) {
                    countTestAnxiety++//Считаем сколько тестов завершено, нужно чтобы было в итоге 3
                    resultAnxiety += result.results.last().resultForStatistic
                }

                if (result.id in forStatisticDepression && result.results.isNotEmpty()) {
                    countTestDepression++//Считаем сколько тестов завершено, нужно чтобы было в итоге 2
                    resultDepression += result.results.last().resultForStatistic
                }

                if (result.id == forStatisticTemperament && result.results.isNotEmpty()) {
                    resultTemperament = result.results.last().resultForStatistic
                }
            }

            /**
             * Проверка и запуск записи по тревожности
             */
            if (countTestAnxiety == forStatisticAnxiety.size) {
                insert(statisticsAll, resultAnxiety, ANXIETY)
            }

            /**
             * Проверка и запуск записи по депрессии
             */
            if (countTestDepression == forStatisticDepression.size) {
                /**
                 * Ограничиваем максимальные диапозоны
                 */
                if (resultDepression > 80.0) {
                    resultDepression = 80.0
                } else if (resultDepression < 0) {
                    resultDepression = 0.0
                }
                insert(statisticsAll, resultDepression, DEPRESSION)
            }

            /**
             * Проверка и запуск записи по темперамент
             */
            if (resultTemperament != 0.0) {
                insertTemperament(statisticsAll, resultTemperament)
            }
        }
    }

    private fun getNameTemperament(typeTemperament: Double): String {
        return when (typeTemperament) {
            18.0 -> {
                TEMPERAMENT_CHOLERIC
            }

            19.0 -> {
                TEMPERAMENT_SANGUINE
            }

            20.0 -> {
                TEMPERAMENT_PHLEGMATIC
            }

            else -> {
                TEMPERAMENT_MELANCHOLIC
            }
        }
    }

    private suspend fun insertTemperament(
        statisticsAll: List<StatisticEntity>,
        resultTemperament: Double
    ) {
        val oldResult = statisticsAll.find { it.id == TEMPERAMENT }
        if (oldResult != null) {//если не null значит запись в общую базу была, а если null, то надо записать новый результат в общую бд
            if (oldResult.result != resultTemperament) {//Проверяем есть ли изменения в нынешним результатом и предыдущем
                Timber.tag("TAG")
                    .d("oldResult.result = ${oldResult.result}  resultTemperament = $resultTemperament")
                remoteDatabase.collection(STATISTICS).document(TEMPERAMENT.toString()).update(
                    getNameTemperament(oldResult.result),
                    FieldValue.increment(-1)//удаляем предыдущюю запись
                )
                delay(1000)//Ограничение firebase, запись возможoна с задержкой в 1 секунду
                remoteDatabase.collection(STATISTICS).document(TEMPERAMENT.toString()).update(
                    getNameTemperament(resultTemperament),
                    FieldValue.increment(1)//добавляем новую запись
                ).addOnSuccessListener {

                }.addOnFailureListener {

                }
                remoteDatabase.collection(STATISTICS).document(TEMPERAMENT.toString()).get()
                    .addOnSuccessListener {
                        val statistics = it.toObject(GlobalTemperament::class.java)
                        if (statistics != null) {
                            val globalResult = mapOf(
                                TEMPERAMENT_CHOLERIC to statistics.choleric,
                                TEMPERAMENT_SANGUINE to statistics.sanguine,
                                TEMPERAMENT_PHLEGMATIC to statistics.phlegmatic,
                                TEMPERAMENT_MELANCHOLIC to statistics.melancholic
                            )
                            coroutineScope.launch {
                                localStorage.statisticsDao().insert(
                                    StatisticEntity(
                                        id = TEMPERAMENT,
                                        result = resultTemperament,
                                        oldResult = oldResult.result,
                                        globalResults = globalResult,
                                        users = statistics.members,
                                        date = Date().time
                                    )
                                )
                                updateRemoteStatistics(idStatistic = TEMPERAMENT)
                            }
                        }
                    }.addOnFailureListener {

                    }
                Timber.tag("TAG").d("remotedatabase 183")
            }
//            else {
//                remoteDatabase.collection(STATISTICS).document(TEMPERAMENT.toString()).get()
//                    .addOnSuccessListener {
//                        val statistics = it.toObject(GlobalTemperament::class.java)
//                        if (statistics != null) {
//                            val globalResult = mapOf(
//                                TEMPERAMENT_CHOLERIC to statistics.choleric,
//                                TEMPERAMENT_SANGUINE to statistics.sanguine,
//                                TEMPERAMENT_PHLEGMATIC to statistics.phlegmatic,
//                                TEMPERAMENT_MELANCHOLIC to statistics.melancholic
//                            )
//                            coroutineScope.launch {
//                                localStorage.statisticsDao().insert(
//                                    StatisticEntity(
//                                        id = TEMPERAMENT,
//                                        result = resultTemperament,
//                                        oldResult = resultTemperament,
//                                        globalResults = globalResult,
//                                        users = statistics.members,
//                                        date = Date().time
//                                    )
//                                )
//                                updateRemoteStatistics(idStatistic = TEMPERAMENT)
//                            }
//                        }
//                    }.addOnFailureListener {
//
//                    }
//            }
        } else {//если null, то надо записать новый результат в общую бд
            var isGlobalFirstWrite = false
            remoteDatabase.collection(STATISTICS).document(TEMPERAMENT.toString()).update(
                getNameTemperament(resultTemperament),
                FieldValue.increment(1),//добавляем новую запись
                MEMBERS,
                FieldValue.increment(1)
            ).addOnSuccessListener {

            }.addOnFailureListener {
                if (firestoreExceptionMapper(it) is DecideDatabaseException.NotFoundDocument) {
                    remoteDatabase.collection(STATISTICS).document(TEMPERAMENT.toString()).set(
                        GlobalTemperament(
                            choleric = 1.0,
                            sanguine = 1.0,
                            phlegmatic = 1.0,
                            melancholic = 1.0
                        )
                    )
                    coroutineScope.launch(Dispatchers.IO) {
                        insertTemperament(statisticsAll, resultTemperament)
                    }
                    isGlobalFirstWrite = true
                }
            }
            Timber.tag("TAG").d("remotedatabase 238")
            if (isGlobalFirstWrite) return
            remoteDatabase.collection(STATISTICS).document(TEMPERAMENT.toString()).get()
                .addOnSuccessListener {
                    val statistics = it.toObject(GlobalTemperament::class.java)
                    if (statistics != null) {
                        val globalResult = mapOf(
                            TEMPERAMENT_CHOLERIC to statistics.choleric,
                            TEMPERAMENT_SANGUINE to statistics.sanguine,
                            TEMPERAMENT_PHLEGMATIC to statistics.phlegmatic,
                            TEMPERAMENT_MELANCHOLIC to statistics.melancholic
                        )
                        coroutineScope.launch {
                            localStorage.statisticsDao().insert(
                                StatisticEntity(
                                    id = TEMPERAMENT,
                                    result = resultTemperament,
                                    oldResult = resultTemperament,
                                    globalResults = globalResult,
                                    users = statistics.members,
                                    date = Date().time
                                )
                            )
                            updateRemoteStatistics(idStatistic = TEMPERAMENT)
                        }
                    }
                }.addOnFailureListener {

                }
            Timber.tag("TAG").d("remotedatabase 266")
        }
    }


    private suspend fun insert(
        statisticsAll: List<StatisticEntity>,
        result: Double,
        documentName: Int
    ) {
        val oldResult = statisticsAll.find { it.id == documentName }
        if (oldResult != null) {//если не null значит запись в общую базу была, а если null, то надо записать новый результат в общую бд

            if (oldResult.result != result) {//Проверяем есть ли изменения c нынешним результатом и предыдущем
                remoteDatabase.collection(STATISTICS).document(DEPRESSION.toString()).update(
                    RESULT, FieldValue.increment(-oldResult.result)//удаляем предыдущюю запись
                )
                delay(1000)//Ограничение firebase, запись возможoна с задержкой в 1 секунду
                remoteDatabase.collection(STATISTICS).document(documentName.toString()).update(
                    RESULT, FieldValue.increment(result)//добавляем новую запись
                ).addOnSuccessListener {

                }.addOnFailureListener {

                }
                remoteDatabase.collection(STATISTICS).document(documentName.toString()).get()
                    .addOnSuccessListener {
                        val statistics = it.toObject(GlobalStatistics::class.java)
                        if (statistics != null) {
                            coroutineScope.launch {
                                localStorage.statisticsDao().insert(
                                    StatisticEntity(
                                        id = documentName,
                                        result = result,
                                        oldResult = oldResult.result,
                                        globalResults = mapOf(SINGLE to statistics.result),
                                        users = statistics.members,
                                        date = Date().time
                                    )
                                )
                                updateRemoteStatistics(idStatistic = documentName)
                            }
                        }
                    }.addOnFailureListener {

                    }
                Timber.tag("TAG").d("remotedatabase 312")
            } else {
                remoteDatabase.collection(STATISTICS).document(documentName.toString()).get()
                    .addOnSuccessListener {
                        val statistics = it.toObject(GlobalStatistics::class.java)
                        if (statistics != null) {
                            coroutineScope.launch {
                                localStorage.statisticsDao().insert(
                                    StatisticEntity(
                                        id = documentName,
                                        result = result,
                                        oldResult = result,
                                        globalResults = mapOf(SINGLE to statistics.result),
                                        users = statistics.members,
                                        date = Date().time
                                    )
                                )
                                updateRemoteStatistics(idStatistic = documentName)
                            }
                        }
                    }.addOnFailureListener {

                    }
            }
        } else {//если null, то надо записать новый результат в общую бд
            remoteDatabase.collection(STATISTICS).document(documentName.toString()).update(
                MEMBERS,
                FieldValue.increment(1),
                RESULT,
                FieldValue.increment(result)//добавляем новую запись
            ).addOnSuccessListener {

            }.addOnFailureListener {
                if (firestoreExceptionMapper(it) is DecideDatabaseException.NotFoundDocument) {
                    remoteDatabase.collection(STATISTICS).document(documentName.toString()).set(
                        GlobalStatistics(
                            members = 1, result = result
                        )
                    )
                }
            }

            remoteDatabase.collection(STATISTICS).document(documentName.toString()).get()
                .addOnSuccessListener {
                    val statistics = it.toObject(GlobalStatistics::class.java)
                    if (statistics != null) {
                        coroutineScope.launch {
                            localStorage.statisticsDao().insert(
                                StatisticEntity(
                                    id = documentName,
                                    result = result,
                                    oldResult = result,
                                    globalResults = mapOf(SINGLE to statistics.result),
                                    users = statistics.members,
                                    date = Date().time
                                )
                            )
                            updateRemoteStatistics(idStatistic = documentName)
                        }
                    }
                }.addOnFailureListener {

                }
            Timber.tag("TAG").d("remotedatabase 374")
        }
    }


    override suspend fun getRemoteStatistic(
        idUser: String,
        hasUpdate: Boolean
    ) {
        try {
            remoteDatabase.collection(USERS).document(idUser).collection(STATISTIC).get()
                .addOnSuccessListener { results ->
                    results.documents.forEach {
                        val statistics = it.toObject(StatisticDTO::class.java)
                        if (statistics != null) {
                            coroutineScope.launch {
                                localStorage.statisticsDao().insert(
                                    StatisticEntity(
                                        id = statistics.id,
                                        result = statistics.result,
                                        oldResult = statistics.oldResult,
                                        globalResults = statistics.globalResults,
                                        users = statistics.users,
                                        date = Date().time
                                    )
                                )
                                if (!hasUpdate) {
                                    updateRemoteStatistics(idStatistic = ANXIETY)
                                }
                            }
                        }
                    }

                }.addOnFailureListener {

                }//при авторизации брать статистику
        } catch (e: Exception) {

        }
        Timber.tag("TAG").d("remotedatabase 408")

    }

    private suspend fun updateRemoteStatistics(idStatistic: Int) {//Обновляются удаленные данные при каждом подсчете статистики
        try {
            val userId = dataStore.data.map { it[KEY_USER_ID] }.first()
            if (userId != null) {
                val userStatistic = localStorage.statisticsDao().getStatistic(idStatistic)
                remoteDatabase.collection(USERS).document(userId).collection(STATISTIC)
                    .document(idStatistic.toString()).set(userStatistic).addOnSuccessListener {

                    }.addOnFailureListener {

                    }
            }
        } catch (e: Exception) {

        }
        Timber.tag("TAG").d("remotedatabase 430")
    }

    private companion object {
        const val STATISTIC = "STATISTIC"
        const val RESULT = "result"
        const val MEMBERS = "members"
    }
}