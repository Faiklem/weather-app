package com.example.weather.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.weather.network.ApiNinjasClient
import com.example.weather.network.DailyWeatherResponse
import com.example.weather.network.OpenMeteoClient
import java.net.URLEncoder
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class WeatherRepository(private val apiNinjasKey: String) {
    suspend fun getCityCoordinates(cityName: String): Pair<Double, Double>? {
        val encodedCity = URLEncoder.encode(cityName, "UTF-8")
        val cities = ApiNinjasClient.service.getCity(apiNinjasKey, encodedCity)
        return if (cities.isNotEmpty()) {
            Pair(cities[0].latitude, cities[0].longitude)
        } else {
            null
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getDailyForecast(
        latitude: Double,
        longitude: Double,
        date: LocalDate
    ): DailyWeatherResponse {
        val today = LocalDate.now()
        require(!date.isBefore(today) && !date.isAfter(today.plusDays(7))) {
            "Дата должна быть в диапазоне от сегодня до сегодня + 7 дней"
        }
        val formatter = DateTimeFormatter.ISO_DATE
        return OpenMeteoClient.service.getDailyWeather(
            latitude = latitude,
            longitude = longitude,
            startDate = date.format(formatter),
            endDate   = date.format(formatter)
        )
    }

    @RequiresApi(Build.VERSION_CODES.O)
    suspend fun getWeeklyForecast(
        latitude: Double,
        longitude: Double
    ): DailyWeatherResponse {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ISO_DATE
        return OpenMeteoClient.service.getDailyWeather(
            latitude = latitude,
            longitude = longitude,
            startDate = today.format(formatter),
            endDate   = today.plusDays(6).format(formatter)
        )
    }
}
