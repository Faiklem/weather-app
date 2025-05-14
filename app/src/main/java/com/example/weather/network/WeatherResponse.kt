package com.example.weather.network

data class DailyWeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val generationtime_ms: Double,
    val daily: DailyWeather
)

data class DailyWeather(
    val time: List<String>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>
)
