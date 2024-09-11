package com.decide.app.account.statisticsClient

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.decide.app.account.data.AccountRepositoryImpl.Companion.KEY_USER_ID
import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.entities.StatisticEntity
import com.decide.app.database.remote.RemoteAssayStorageImpl.Companion.ANXIETY
import com.decide.app.database.remote.RemoteAssayStorageImpl.Companion.STATISTICS
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
        var countTestAnxiety = 0
        val getAllResults = localStorage.assayDao().getAllAssays()
        val statisticsAll = localStorage.statisticsDao().getAll()
        coroutineScope.launch {
            var resultAnxiety = 0.0//Счетчик баллов

            getAllResults.forEach { result ->

                if (result.id in forStatisticAnxiety && result.results.isNotEmpty()) {
                    countTestAnxiety++//Считаем сколько тестов завершено, нужно чтобы было в итоге 3
                    Timber.tag("TAG")
                        .d("resultForStatistic = ${result.results.last().resultForStatistic}")
                    resultAnxiety += result.results.last().resultForStatistic
                    Timber.tag("TAG").d("resultAnxiety = $resultAnxiety")
                }
            }
            if (countTestAnxiety == 3) {

                val oldResult = statisticsAll.find { it.id == ANXIETY }

                if (oldResult != null) {//если не null значит запись в общую базу была, а если null, то надо записать новый результат в общую бд

                    if (oldResult.result != resultAnxiety) {
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
                            Timber.tag("TAG").d("StatisticsClientImpl addOnFailureListener = ${it.message}")
                        }
                        remoteDatabase.collection(STATISTICS).document(ANXIETY.toString()).get()
                            .addOnSuccessListener {
                                val statistics = it.toObject(GlobalStatistics::class.java)
                                if (statistics != null) {
                                    Timber.tag("TAG").d("StatisticsClientImpl addOnSuccessListener get")
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
                                Timber.tag("TAG").d("StatisticsClientImpl addOnFailureListener = ${it.message}")
                            }
                    }
                } else {//если null, то надо записать новый результат в общую бд
                    remoteDatabase.collection(STATISTICS).document(ANXIETY.toString()).update(
                        "result", FieldValue.increment(resultAnxiety),//добавляем новую запись
                        "members", FieldValue.increment(1)
                    ).addOnSuccessListener {
                        Timber.tag("TAG").d("StatisticsClientImpl addOnSuccessListener")
                    }.addOnFailureListener {
                        Timber.tag("TAG").d("StatisticsClientImpl addOnFailureListener = ${it.message}")
                    }

                    remoteDatabase.collection(STATISTICS).document(ANXIETY.toString()).get()
                        .addOnSuccessListener {
                            val statistics = it.toObject(GlobalStatistics::class.java)
                            if (statistics != null) {
                                Timber.tag("TAG").d("StatisticsClientImpl addOnSuccessListener get")
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
                            Timber.tag("TAG").d("StatisticsClientImpl addOnFailureListener = ${it.message}")
                        }
                }

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
                                        globalResults = statistics.globalResults,
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
                        Timber.tag("FIREBASE").d("StatisticsClientImpl updateStatistics Success")
                    }.addOnFailureListener {
                        Timber.tag("FIREBASE").d("StatisticsClientImpl updateStatistics = ${it.message}")
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