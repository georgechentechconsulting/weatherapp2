package com.example.weatherapp
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.repository.WeatherResponse

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                val viewModel: WeatherViewModel = viewModel()
                WeatherScreen(viewModel)
            }
        }
    }


}

@Composable
fun WeatherScreen(viewModel: WeatherViewModel) {
    val weatherData by viewModel.weatherData.collectAsState()
    val city = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = city.value,
            onValueChange = { city.value = it },
            label = { Text("Enter US City") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Button(
            onClick = { viewModel.getWeather(city.value) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Get Weather")
        }

        Spacer(modifier = Modifier.height(16.dp))

        weatherData?.let {
            WeatherCard(it)
        }
    }
}

@Composable
fun WeatherCard(weather: WeatherResponse) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "City: ${weather.name}")
        Text(text = "Temperature: ${ (weather.main.temp * 9 / 5) + 32}°F")
        Text(text = "Weather: ${weather.weather[0].description.capitalize()}")
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    WeatherAppTheme {
        WeatherScreen(viewModel = WeatherViewModel(WeatherRepository()))
    }
}


@Composable
fun WeatherAppTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(),
        content = content
    )
}
