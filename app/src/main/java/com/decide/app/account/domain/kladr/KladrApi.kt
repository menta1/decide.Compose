package com.decide.app.account.domain.kladr

import com.decide.app.account.modal.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface KladrApi {

    @GET("/api.php?")
    suspend fun getCities(
        @Query("query") search: String,
        @Query("contentType") contentType: String = "city",
        @Query("typeCode") typeCode: Int = 1,

        ): SearchResult
}