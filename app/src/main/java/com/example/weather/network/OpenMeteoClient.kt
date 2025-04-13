package com.example.weather.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OpenMeteoClient {
    private const val BASE_URL = "https://api.open-meteo.com/"

    val service: OpenMeteoService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(OpenMeteoService::class.java)
    }
}
