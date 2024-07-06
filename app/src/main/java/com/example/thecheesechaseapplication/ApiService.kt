package com.example.thecheesechaseapplication

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

private val retrofit = Retrofit.Builder().baseUrl("https://chasedeux.vercel.app/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val dataService = retrofit.create(ApiService::class.java)

interface ApiService {
    @GET("obstacleLimit")
    suspend fun getObstacleLimit() : CollisionCount

    @GET("hitHindrance")
    suspend fun getHitHindrance() : hitHindrance

    @POST("obstacleCourse")
    suspend fun getObstacleCourse(@Body request : obstacleCourseRequest) : obstacleCourseResponse

    @POST("randomWord")
    suspend fun getRandomWord(@Body request : randomWordRequest) : randomWord

    @POST("theme")
    suspend fun getTheme(@Body request : themeRequest) : theme
}

