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

data class obstacleCourseRequest(
    val extent : Int
)

data class obstacleCourseResponse(
    val obstacleCourse : List<String>
)

interface ApiService {
    @GET("obstacleLimit")
    suspend fun getObstacleLimit() : CollisionCount

    @GET("hitHindrance")
    suspend fun getHitHindrance() : hitHindrance

    @POST("obstacleCourse")
    suspend fun getObstacleCourse(@Body request : obstacleCourseRequest) : obstacleCourseResponse

    /*@GET("/image?character=tom")
    suspend fun getImageTom() : tomImage

    @GET("/image?character=jerry")
    suspend fun getImageJerry() : jerryImage

    @GET("/image?character=obstacle")
    suspend fun getImageObstacle() : obstacleImage*/
}

