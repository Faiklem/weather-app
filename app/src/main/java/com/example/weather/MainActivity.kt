package com.example.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.weather.repository.WeatherRepository
import com.example.weather.viewmodel.WeatherViewModel
import com.example.weather.viewmodel.WeatherViewModelFactory

class MainActivity : ComponentActivity() {
    private val apiNinjasKey = BuildConfig.API_NINJAS_KEY

    private val viewModel: WeatherViewModel by viewModels {
        WeatherViewModelFactory(WeatherRepository(apiNinjasKey))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppScreen(viewModel = viewModel)
        }
    }
}
