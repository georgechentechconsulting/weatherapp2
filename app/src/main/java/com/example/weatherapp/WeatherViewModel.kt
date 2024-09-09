package com.example.weatherapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.repository.WeatherRepository
import com.example.weatherapp.repository.WeatherResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WeatherViewModel(private val repository: WeatherRepository = WeatherRepository()) : ViewModel() {

    private val _weatherData = MutableStateFlow<WeatherResponse?>(null)
    val weatherData: StateFlow<WeatherResponse?> = _weatherData

    fun getWeather(city: String) {
        viewModelScope.launch {
            val response = repository.getWeatherByCity(city)
            _weatherData.value = response
        }
    }
}
