package com.example.weather.network

import retrofit2.http.GET
import retrofit2.http.Query

interface OpenMeteoService {
    @GET("v1/forecast")
    suspend fun getWeatherForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("hourly") hourly: String = "temperature_2m"
    ): WeatherResponse
}
