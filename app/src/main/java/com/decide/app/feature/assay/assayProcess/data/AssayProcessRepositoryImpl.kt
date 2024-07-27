package com.decide.app.feature.assay.assayProcess.data

import android.util.Log
import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.dto.ResultCompletedAssayEntity
import com.decide.app.database.local.dto.toAssay
import com.decide.app.database.remote.assay.RemoteAssayStorage
import com.decide.app.database.remote.assay.dto.KeyDto
import com.decide.app.feature.assay.assayProcess.ui.Answers
import com.decide.app.feature.assay.mainAssay.modals.Assay
import com.decide.app.utils.Resource
import java.util.Date
import javax.inject.Inject

class AssayProcessRepositoryImpl @Inject constructor(
    private val localAssayStorage: AppDatabase,
    private val remoteAssayStorage: RemoteAssayStorage
) : AssayProcessRepository {

    override suspend fun getAssay(id: Int): Resource<Assay> {
        val result = localAssayStorage.assayDao().getAssay(id)
        Log.d("TAG", "result - " + result.toString())
        return if (result != null) {
            Resource.Success(result.toAssay())
        } else {
            Resource.Error(Exception("Ничего не найдено"))
        }
    }

    override suspend fun saveResult(id: Int, answers: List<Answers>): Resource<Boolean> {
        return when (id) {
            1 -> {
                var points = 0
                answers.forEach {
                    points += it.idAnswer
                }
                val result: Float = points.toFloat() / 20

                remoteAssayStorage.getKey(id.toString()) {
                    when (it) {
                        is Resource.Success -> {
                            when {
                                (result in 0f..1f) -> {
                                    saveResult(id = id, key = "1", it.data)
                                }

                                (result in 1f..2f) -> {
                                    saveResult(id = id, key = "2", it.data)
                                }

                                (result in 2f..3f) -> {
                                    saveResult(id = id, key = "3", it.data)
                                }

                                (result in 3f..3.4f) -> {
                                    saveResult(id = id, key = "4", it.data)
                                }

                                else -> {
                                    saveResult(id = id, key = "5", it.data)
                                }
                            }
                        }

                        is Resource.Error -> {
                            Log.d("TAG", "saveResult 43 string")
                        }

                    }
                }


                Resource.Error(Exception("Не подсчитано"))
            }

            else -> {
                Resource.Error(Exception("Не подсчитано"))
            }
        }
    }

    private fun saveResult(id: Int, key: String, keyDto: KeyDto){

        val results = localAssayStorage.assayDao()
            .getAssay(id).results.toMutableList()

        if (results.size > 5) {
            results.removeFirst()
            results.add(
                ResultCompletedAssayEntity(
                    date = Date().time.toString(),
                    result = keyDto.result[key] ?: "-1"
                )
            )
        } else {
            results.add(
                ResultCompletedAssayEntity(
                    date = Date().time.toString(),
                    result = keyDto.result[key] ?: "-1"
                )
            )
        }
        localAssayStorage.assayDao().addNewResult(id, results)
    }

}