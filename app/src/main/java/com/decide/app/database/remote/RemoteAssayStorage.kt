package com.decide.app.database.remote

import com.decide.app.account.modal.UserUpdate
import com.decide.app.database.remote.dto.AccountDTO
import com.decide.app.database.remote.dto.AssayDTO
import com.decide.app.utils.DecideException
import com.decide.app.utils.Resource
import kotlinx.coroutines.flow.Flow
import java.io.InputStream

interface RemoteAssayStorage {
    suspend fun getAssay(id: String): Flow<Result<AssayDTO>>
    suspend fun getAssays()
    suspend fun getCategories()
    suspend fun getKey(id: String)

    suspend fun createAccount(
        account: AccountDTO,
        onResult: (result: Resource<Boolean, DecideException>) -> Unit
    )

    suspend fun updateAccount(
        userUpdate: UserUpdate,
        id: String
    )

    suspend fun saveAvatar(
        inputStream: InputStream?,
        id: String
    )

    suspend fun getPassedAssays(id: String)

    suspend fun putPassedAssays(id: Int)

    suspend fun getAccountData(
        id: String?,
        onResult: (result: Resource<Boolean, DecideException>) -> Unit
    )

}