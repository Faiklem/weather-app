package com.example.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.weather.ui.components.ForecastCard
import com.example.weather.viewmodel.WeatherViewModel
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeatherAppScreen(viewModel: WeatherViewModel) {
    var city by remember { mutableStateOf("") }
    var dateInput by remember { mutableStateOf("") }
    var localError by remember { mutableStateOf<String?>(null) }

    val dailyResp by viewModel.dailyResponse.observeAsState()
    val isLoading by viewModel.isLoading.observeAsState(false)
    val vmError by viewModel.error.observeAsState()

    Scaffold(
        topBar = { TopAppBar(title = { Text("Прогноз погоды") }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                TextField(
                    value = city,
                    onValueChange = { city = it },
                    label = { Text("Город") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(8.dp))
                TextField(
                    value = dateInput,
                    onValueChange = { dateInput = it },
                    label = { Text("Дата (YYYY-MM-DD)") },
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Button(
                    onClick = {
                        localError = null
                        if (city.isBlank()) {
                            localError = "Введите название города"
                        } else {
                            runCatching { LocalDate.parse(dateInput) }
                                .fold(
                                    onSuccess = { date -> viewModel.loadDaily(city, date) },
                                    onFailure = { localError = "Неверный формат даты" }
                                )
                        }
                    }
                ) {
                    Text("Получить на дату")
                }
                Spacer(modifier = Modifier.width(16.dp))
                Button(
                    onClick = {
                        localError = null
                        if (city.isBlank()) {
                            localError = "Введите название города"
                        } else {
                            viewModel.loadWeekly(city)
                        }
                    }
                ) {
                    Text("Получить на неделю")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            val errorMessage = localError ?: vmError
            errorMessage?.let { msg ->
                Text(
                    text = msg,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            if (isLoading) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(text = "Загрузка...")
                    }
                }
            }
            else if (errorMessage == null && dailyResp != null) {
                val resp = dailyResp!!
                if (resp.daily.time.size == 1) {
                    val date = LocalDate.parse(resp.daily.time[0])
                    ForecastCard(
                        date = date,
                        tempMax = resp.daily.temperature_2m_max[0],
                        tempMin = resp.daily.temperature_2m_min[0],
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    Text(
                        text = "Прогноз на неделю:",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(resp.daily.time.size) { i ->
                            val date = LocalDate.parse(resp.daily.time[i])
                            ForecastCard(
                                date = date,
                                tempMax = resp.daily.temperature_2m_max[i],
                                tempMin = resp.daily.temperature_2m_min[i]
                            )
                        }
                    }
                }
            }
        }
    }
}
