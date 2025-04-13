package com.example.weather.network

data class WeatherResponse(
    val latitude: Double,
    val longitude: Double,
    val generationtime_ms: Double,
    val hourly: HourlyWeather
)

data class HourlyWeather(
    val time: List<String>,
    val temperature_2m: List<Double>
)
