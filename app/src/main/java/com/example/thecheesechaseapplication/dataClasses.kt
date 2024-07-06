package com.example.thecheesechaseapplication

import android.health.connect.datatypes.units.Length

data class CollisionCount(
    val obstacleLimit : Int
)

data class obstacleCourseRequest(
    val extent : Int
)

data class obstacleCourseResponse(
    val obstacleCourse : List<String>
)

data class randomWordRequest(
    val length : Int
)

data class randomWord(
    val word : String
)

data class themeRequest(
    val date : String,
    val time : String
)

data class theme(
    val theme : String
)