package com.example.weather.repository

import com.example.weather.network.ApiNinjasClient
import com.example.weather.network.WeatherResponse
import com.example.weather.network.OpenMeteoClient

class WeatherRepository(private val apiNinjasKey: String) {
    suspend fun getCityCoordinates(cityName: String): Pair<Double, Double>? {
        val cities = ApiNinjasClient.service.getCity(apiNinjasKey, cityName)
        return if (cities.isNotEmpty()) {
            Pair(cities[0].latitude, cities[0].longitude)
        } else {
            null
        }
    }

    suspend fun getWeatherForecast(latitude: Double, longitude: Double): WeatherResponse {
        return OpenMeteoClient.service.getWeatherForecast(latitude, longitude)
    }
}
