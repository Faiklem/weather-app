package com.example.weather.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.example.weather.network.DailyWeatherResponse
import com.example.weather.repository.WeatherRepository
import kotlinx.coroutines.launch
import java.time.LocalDate

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {
    private val _dailyResponse = MutableLiveData<DailyWeatherResponse?>()
    val dailyResponse: LiveData<DailyWeatherResponse?> = _dailyResponse

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> = _error

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadDaily(city: String, date: LocalDate) {
        viewModelScope.launch {
            _dailyResponse.value = null
            _error.value = null

            try {
                val coords = repository.getCityCoordinates(city)
                if (coords != null) {
                    _dailyResponse.value = repository.getDailyForecast(coords.first, coords.second, date)
                } else {
                    _error.value = "Город не найден"
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun loadWeekly(city: String) {
        viewModelScope.launch {
            _dailyResponse.value = null
            _error.value = null

            try {
                val coords = repository.getCityCoordinates(city)
                if (coords != null) {
                    _dailyResponse.value = repository.getWeeklyForecast(coords.first, coords.second)
                } else {
                    _error.value = "Город не найден"
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}
