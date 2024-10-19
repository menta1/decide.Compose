package com.decide.app.account.statisticsClient

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.decide.app.account.data.AccountRepositoryImpl.Companion.KEY_USER_ID
import com.decide.app.account.statisticsClient.modals.GlobalStatistics
import com.decide.app.account.statisticsClient.modals.GlobalTemperament
import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.entities.StatisticEntity
import com.decide.app.database.remote.RemoteAssayStorageImpl.Companion.ANXIETY
import com.decide.app.database.remote.RemoteAssayStorageImpl.Companion.STATISTICS
import com.decide.app.database.remote.RemoteAssayStorageImpl.Companion.TEMPERAMENT
import com.decide.app.database.remote.RemoteAssayStorageImpl.Companion.USERS
import com.decide.app.database.remote.dto.StatisticDTO
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
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
        val forStatisticTemperament = 4//тест по темпераменту

        var countTestAnxiety = 0

        val getAllResults = localStorage.assayDao().getAllAssays()
        val statisticsAll = localStorage.statisticsDao().getAll()
        coroutineScope.launch {
            var resultAnxiety = 0.0//Счетчик баллов
            var resultTemperament = 0.0//Счетчик темперамент
            getAllResults.forEach { result ->

                if (result.id in forStatisticAnxiety && result.results.isNotEmpty()) {
                    countTestAnxiety++//Считаем сколько тестов завершено, нужно чтобы было в итоге 3
                    Timber.tag("TAG")
                        .d("getAllResults.forEach countTestAnxiety = $countTestAnxiety")
                    resultAnxiety += result.results.last().resultForStatistic
                }

                if (result.id == forStatisticTemperament && result.results.isNotEmpty()) {
                    resultTemperament =
                        result.results.last().resultForStatistic
                    Timber.tag("TAG").d("countTestTemperament find = $resultTemperament")
                }
            }

            /**
             * Проверка и запуск записи по тревожности
             */
            if (countTestAnxiety == 3) {
                insertAnxiety(statisticsAll, resultAnxiety)
            } else {
                Timber.tag("TAG").d("countTestAnxiety != 3 countTestAnxiety = $countTestAnxiety")
            }

            /**
             * Проверка и запуск записи по темперамент
             */
            if (resultTemperament != 0.0) {
                insertTemperament(statisticsAll, resultTemperament)
            } else {
                Timber.tag("TAG").d("resultTemperament == 0  = $resultTemperament")
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

                remoteDatabase.collection(STATISTICS).document(TEMPERAMENT.toString()).update(
                    getNameTemperament(oldResult.result),
                    FieldValue.increment(-1)//удаляем предыдущюю запись
                )
                delay(1000)//Ограничение firebase, запись возможoна с задержкой в 1 секунду
                remoteDatabase.collection(STATISTICS).document(TEMPERAMENT.toString()).update(
                    getNameTemperament(resultTemperament),
                    FieldValue.increment(1)//добавляем новую запись
                ).addOnSuccessListener {
                    Timber.tag("TAG").d("StatisticsClientImpl addOnSuccessListener update")
                }.addOnFailureListener {
                    Timber.tag("TAG")
                        .d("StatisticsClientImpl addOnFailureListener = ${it.message}")
                }
                remoteDatabase.collection(STATISTICS).document(TEMPERAMENT.toString()).get()
                    .addOnSuccessListener {
                        val statistics = it.toObject(GlobalTemperament::class.java)
                        if (statistics != null) {
                            Timber.tag("TAG")
                                .d("StatisticsClientImpl addOnSuccessListener statistics != null")
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
                                        users = statistics.members
                                    )
                                )
                                updateRemoteStatistics(idStatistic = TEMPERAMENT)
                            }
                        }
                    }.addOnFailureListener {
                        Timber.tag("TAG")
                            .d("StatisticsClientImpl addOnFailureListener = ${it.message}")
                    }
            } else {
                remoteDatabase.collection(STATISTICS).document(TEMPERAMENT.toString()).get()
                    .addOnSuccessListener {
                        val statistics = it.toObject(GlobalTemperament::class.java)
                        if (statistics != null) {
                            Timber.tag("TAG")
                                .d("StatisticsClientImpl addOnSuccessListener else statistics != null")
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
                                        users = statistics.members
                                    )
                                )
                                updateRemoteStatistics(idStatistic = TEMPERAMENT)
                            }
                        }
                    }.addOnFailureListener {
                        Timber.tag("TAG")
                            .d("StatisticsClientImpl addOnFailureListener = ${it.message}")
                    }
            }
        } else {//если null, то надо записать новый результат в общую бд
            remoteDatabase.collection(STATISTICS).document(TEMPERAMENT.toString()).update(
                getNameTemperament(resultTemperament),
                FieldValue.increment(1),//добавляем новую запись
                "members",
                FieldValue.increment(1)
            ).addOnSuccessListener {
                Timber.tag("TAG").d("StatisticsClientImpl addOnSuccessListener")
            }.addOnFailureListener {
                Timber.tag("TAG")
                    .d("StatisticsClientImpl addOnFailureListener = ${it.message}")
            }

            remoteDatabase.collection(STATISTICS).document(TEMPERAMENT.toString()).get()
                .addOnSuccessListener {
                    val statistics = it.toObject(GlobalTemperament::class.java)
                    if (statistics != null) {
                        Timber.tag("TAG")
                            .d("StatisticsClientImpl addOnSuccessListener else statistics != null")
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
                                    users = statistics.members
                                )
                            )
                            updateRemoteStatistics(idStatistic = TEMPERAMENT)
                        }
                    }
                }.addOnFailureListener {
                    Timber.tag("TAG")
                        .d("StatisticsClientImpl addOnFailureListener = ${it.message}")
                }
        }
    }

    private suspend fun insertAnxiety(
        statisticsAll: List<StatisticEntity>,
        resultAnxiety: Double
    ) {

        val oldResult = statisticsAll.find { it.id == ANXIETY }

        if (oldResult != null) {//если не null значит запись в общую базу была, а если null, то надо записать новый результат в общую бд

            if (oldResult.result != resultAnxiety) {//Проверяем есть ли изменения в нынешним результатом и предыдущем
                remoteDatabase.collection(STATISTICS).document(ANXIETY.toString()).update(
                    "result",
                    FieldValue.increment(-oldResult.result)//удаляем предыдущюю запись
                )
                delay(1000)//Ограничение firebase, запись возможoна с задержкой в 1 секунду
                remoteDatabase.collection(STATISTICS).document(ANXIETY.toString()).update(
                    "result", FieldValue.increment(resultAnxiety)//добавляем новую запись
                ).addOnSuccessListener {
                    Timber.tag("TAG").d("StatisticsClientImpl addOnSuccessListener update")
                }.addOnFailureListener {
                    Timber.tag("TAG")
                        .d("StatisticsClientImpl addOnFailureListener = ${it.message}")
                }
                remoteDatabase.collection(STATISTICS).document(ANXIETY.toString()).get()
                    .addOnSuccessListener {
                        val statistics = it.toObject(GlobalStatistics::class.java)
                        if (statistics != null) {
                            Timber.tag("TAG")
                                .d("StatisticsClientImpl addOnSuccessListener statistics != null")
                            coroutineScope.launch {
                                localStorage.statisticsDao().insert(
                                    StatisticEntity(
                                        id = ANXIETY,
                                        result = resultAnxiety,
                                        oldResult = oldResult.result,
                                        globalResults = statistics.result,
                                        users = statistics.members
                                    )
                                )
                                updateRemoteStatistics(idStatistic = ANXIETY)
                            }
                        }
                    }.addOnFailureListener {
                        Timber.tag("TAG")
                            .d("StatisticsClientImpl addOnFailureListener = ${it.message}")
                    }
            } else {
                remoteDatabase.collection(STATISTICS).document(ANXIETY.toString()).get()
                    .addOnSuccessListener {
                        val statistics = it.toObject(GlobalStatistics::class.java)
                        if (statistics != null) {
                            Timber.tag("TAG")
                                .d("StatisticsClientImpl addOnSuccessListener else statistics != null")
                            coroutineScope.launch {
                                localStorage.statisticsDao().insert(
                                    StatisticEntity(
                                        id = ANXIETY,
                                        result = resultAnxiety,
                                        oldResult = resultAnxiety,
                                        globalResults = statistics.result,
                                        users = statistics.members
                                    )
                                )
                                updateRemoteStatistics(idStatistic = ANXIETY)
                            }
                        }
                    }.addOnFailureListener {
                        Timber.tag("TAG")
                            .d("StatisticsClientImpl addOnFailureListener = ${it.message}")
                    }
            }
        } else {//если null, то надо записать новый результат в общую бд
            remoteDatabase.collection(STATISTICS).document(ANXIETY.toString()).update(
                "result", FieldValue.increment(resultAnxiety),//добавляем новую запись
                "members", FieldValue.increment(1)
            ).addOnSuccessListener {
                Timber.tag("TAG").d("StatisticsClientImpl addOnSuccessListener")
            }.addOnFailureListener {
                Timber.tag("TAG")
                    .d("StatisticsClientImpl addOnFailureListener = ${it.message}")
            }

            remoteDatabase.collection(STATISTICS).document(ANXIETY.toString()).get()
                .addOnSuccessListener {
                    val statistics = it.toObject(GlobalStatistics::class.java)
                    if (statistics != null) {
                        Timber.tag("TAG")
                            .d("StatisticsClientImpl addOnSuccessListener else statistics != null")
                        coroutineScope.launch {
                            localStorage.statisticsDao().insert(
                                StatisticEntity(
                                    id = ANXIETY,
                                    result = resultAnxiety,
                                    oldResult = resultAnxiety,
                                    globalResults = statistics.result,
                                    users = statistics.members
                                )
                            )
                            updateRemoteStatistics(idStatistic = ANXIETY)
                        }
                    }
                }.addOnFailureListener {
                    Timber.tag("TAG")
                        .d("StatisticsClientImpl addOnFailureListener = ${it.message}")
                }
        }
    }


    override suspend fun getRemoteStatistic(idUser: String) {

        try {
            remoteDatabase.collection(USERS).document(idUser).collection(STATISTIC).get()
                .addOnSuccessListener { results ->
                    results.documents.forEach {
                        val statistics = it.toObject(StatisticDTO::class.java)
                        if (statistics != null) {
                            Timber.tag("TAG").d("StatisticsClientImpl getRemoteStatistic get")
                            coroutineScope.launch {
                                localStorage.statisticsDao().insert(
                                    StatisticEntity(
                                        id = statistics.id,
                                        result = statistics.result,
                                        oldResult = statistics.oldResult,
                                        globalResults = mapOf("result" to statistics.globalResults),
                                        users = statistics.users
                                    )
                                )
                                updateRemoteStatistics(idStatistic = ANXIETY)
                            }
                        }
                    }

                }.addOnFailureListener {

                }//при авторизации брать статистику
        } catch (e: Exception) {
            Timber.tag("FIREBASE").d("StatisticsClientImpl getRemoteStatistic = ${e.message}")
        }

    }

    private suspend fun updateRemoteStatistics(idStatistic: Int) {//Обновляются удаленные данные при каждом подсчете статистики
        try {
            val userId = dataStore.data.map { it[KEY_USER_ID] }.first()
            if (userId != null) {
                val userStatistic = localStorage.statisticsDao().getStatistic(idStatistic)
                remoteDatabase.collection(USERS).document(userId).collection(STATISTIC)
                    .document(idStatistic.toString()).set(userStatistic).addOnSuccessListener {
                        Timber.tag("FIREBASE")
                            .d("StatisticsClientImpl updateStatistics Success")
                    }.addOnFailureListener {
                        Timber.tag("FIREBASE")
                            .d("StatisticsClientImpl updateStatistics = ${it.message}")
                    }
            } else {
                Timber.tag("FIREBASE").d("StatisticsClientImpl updateStatistics userId=null")
            }

        } catch (e: Exception) {
            Timber.tag("FIREBASE").d("StatisticsClientImpl updateAccount = ${e.message}")
        }
    }

    private companion object {
        const val STATISTIC = "STATISTIC"
    }
}