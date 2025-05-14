package com.example.weather.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiNinjasClient {
    private const val BASE_URL = "https://api.api-ninjas.com/"

    val service: ApiNinjasService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(HttpClientProvider.okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiNinjasService::class.java)
    }
}
