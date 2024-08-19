package com.decide.app.feature.assay.assayProcess.data

import com.decide.app.database.local.AppDatabase
import com.decide.app.database.local.dto.ResultCompletedAssayEntity
import com.decide.app.database.local.dto.toAssay
import com.decide.app.database.remote.assay.RemoteAssayStorage
import com.decide.app.database.remote.assay.dto.KeyDto
import com.decide.app.feature.assay.assayProcess.domain.AssayProcessRepository
import com.decide.app.feature.assay.mainAssay.modals.Assay
import com.decide.app.feature.passed.models.ResultCompletedAssay
import com.decide.app.utils.Resource
import java.util.Date
import javax.inject.Inject

class AssayProcessRepositoryImpl @Inject constructor(
    private val localAssayStorage: AppDatabase,
    private val remoteAssayStorage: RemoteAssayStorage
) : AssayProcessRepository {

    override suspend fun getAssay(id: Int): Resource<Assay> {
        val result = localAssayStorage.assayDao().getAssay(id)
        return if (result != null) {
            Resource.Success(result.toAssay())
        } else {
            Resource.Error(Exception("Ничего не найдено"))
        }
    }

    override suspend fun saveResult(id: Int, result: ResultCompletedAssay) {
        val results = localAssayStorage.assayDao()
            .getAssay(id).results.sortedBy { it.date }.toMutableList()

        if (results.size > 5) {
            results.removeLast()
            results.add(
                ResultCompletedAssayEntity(
                    date = result.date,
                    shortResult = result.shortResult,
                    result = result.result,
                    keyResult = result.keyResult
                )
            )
        } else {
            results.add(
                ResultCompletedAssayEntity(
                    date = result.date,
                    shortResult = result.shortResult,
                    result = result.result,
                    keyResult = result.keyResult
                )
            )
        }
        localAssayStorage.assayDao().addNewResult(id, results)
    }

    override suspend fun getKeys(id: Int, onResult: (Resource<KeyDto>) -> Unit) {
        remoteAssayStorage.getKey(id = id.toString(), onResult = onResult)
    }

//    private suspend fun controlCountResultSave(id: Int, key: String, keyDto: KeyDto) {
//
//        val results = localAssayStorage.assayDao()
//            .getAssay(id).results.toMutableList()
//
//        if (results.size > 5) {
//            results.removeFirst()
//            results.add(
//                ResultCompletedAssayEntity(
//                    date = Date().time,
//                    shortResult = keyDto.resultShort[key] ?: "-1",
//                    result = keyDto.result[key] ?: "-1"
//                )
//            )
//        } else {
//            results.add(
//                ResultCompletedAssayEntity(
//                    date = Date().time,
//                    shortResult = keyDto.resultShort[key] ?: "-1",
//                    result = keyDto.result[key] ?: "-1"
//                )
//            )
//        }
//        localAssayStorage.assayDao().addNewResult(id, results)
//    }

}