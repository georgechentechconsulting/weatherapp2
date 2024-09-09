package com.example.weatherapp.repository

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {

    private val apiService: WeatherApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        apiService = retrofit.create(WeatherApiService::class.java)
    }

    suspend fun getWeatherByCity(city: String): WeatherResponse {
        return apiService.getWeatherByCity(city, "0b998d0c1a9300cf8c898322a89cd4de")
    }
}
