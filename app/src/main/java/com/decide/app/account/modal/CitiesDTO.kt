package com.decide.app.account.modal

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResult(
    @SerialName("result") val result: List<CitiesDTO>
)

@Serializable
data class CitiesDTO(
    @SerialName("id") val id: String,
    @SerialName("name") val city: String
)
