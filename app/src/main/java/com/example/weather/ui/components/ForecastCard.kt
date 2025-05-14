package com.example.weather.ui.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ForecastCard(
    date: LocalDate,
    tempMax: Double,
    tempMin: Double,
    modifier: Modifier = Modifier
) {
    val dateStr = date.format(DateTimeFormatter.ISO_DATE)
    val dayOfWeek = date.dayOfWeek
        .getDisplayName(TextStyle.FULL, Locale("ru"))
        .replaceFirstChar { it.uppercaseChar() }

    Card(
        modifier = modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Text(
                text = "$dateStr, $dayOfWeek",
                style = MaterialTheme.typography.bodyLarge
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text = "Максимальная температура: $tempMax°C",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Минимальная температура: $tempMin°C",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
