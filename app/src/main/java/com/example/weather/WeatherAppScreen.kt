package com.example.weather

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.viewmodel.WeatherViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppScreen(viewModel: WeatherViewModel) {
    var cityInput by remember { mutableStateOf("") }
    val weatherResponse = viewModel.weatherResponse.observeAsState()
    val errorMsg = viewModel.error.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Прогноз погоды") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = cityInput,
                onValueChange = { cityInput = it },
                label = { Text("Введите название города") },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    if (cityInput.isNotBlank()) {
                        viewModel.loadWeather(cityInput)
                    }
                }
            ) {
                Text("Получить прогноз")
            }
            Spacer(modifier = Modifier.height(32.dp))
            when {
                errorMsg.value != null -> {
                    Text(
                        text = "Ошибка: ${errorMsg.value}",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                weatherResponse.value != null -> {
                    val forecast = weatherResponse.value!!
                    val temperature = forecast.hourly.temperature_2m.firstOrNull() ?: "N/A"
                    Text(text = "Температура: $temperature°C", fontSize = 20.sp)
                }
            }
        }
    }
}
