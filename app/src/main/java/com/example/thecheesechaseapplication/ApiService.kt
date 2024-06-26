package com.example.thecheesechaseapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

private val retrofit = Retrofit.Builder().baseUrl("https://chasedeux.vercel.app/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val dataService = retrofit.create(ApiService::class.java)

interface ApiService {
    @GET("obstacleLimit")
    suspend fun getObstacleLimit() : CollisionCount
}