package com.ayoolamasha.gopaddi.featureTrips.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class GetCountriesResponse(
    @Json(name = "error")
    val error: Boolean?,
    @Json(name = "msg")
    val message: String?,
    @Json(name = "data")
    val data: List<GetCountriesDataResponse>?,


)

@JsonClass(generateAdapter = true)
data class GetCountriesDataResponse(
    @Json(name = "name")
    val name: String?,
    @Json(name = "flag")
    val flag: String?,
    @Json(name = "iso2")
    val iso2: String?,
    @Json(name = "iso3")
    val iso3: String?,
)
