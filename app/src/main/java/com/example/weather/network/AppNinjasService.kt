package com.example.weather.network

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiNinjasService {
    @GET("v1/city")
    suspend fun getCity(
        @Header("X-Api-Key") apiKey: String,
        @Query("name") cityName: String
    ): List<CityResponse>
}
