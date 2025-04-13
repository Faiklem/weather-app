package com.example.weather.viewmodel

import androidx.lifecycle.*
import com.example.weather.network.WeatherResponse
import com.example.weather.repository.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _weatherResponse = MutableLiveData<WeatherResponse?>()
    val weatherResponse: LiveData<WeatherResponse?> get() = _weatherResponse

    private val _error = MutableLiveData<String?>()
    val error: LiveData<String?> get() = _error

    fun loadWeather(cityName: String) {
        viewModelScope.launch {
            try {
                val coordinates = repository.getCityCoordinates(cityName)
                if (coordinates != null) {
                    val forecast = repository.getWeatherForecast(coordinates.first, coordinates.second)
                    _weatherResponse.value = forecast
                } else {
                    _error.value = "Город не найден"
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class WeatherViewModelFactory(private val repository: WeatherRepository) : ViewModelProvider.Factory {
    override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WeatherViewModel::class.java)) {
            return WeatherViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
