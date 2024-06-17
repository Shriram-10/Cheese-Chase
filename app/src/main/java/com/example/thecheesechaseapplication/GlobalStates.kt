package com.example.thecheesechaseapplication

import android.media.MediaPlayer
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

var height = mutableStateOf(0f)
var width = mutableStateOf(0f)
var yBox = mutableStateListOf<Float>(0f,0f,0f,0f,0f,0f,0f,0f,0f)
var yBoxLocate = mutableStateListOf(-1,1,0,-1,1,0,-1,1,0)
var yBoxOffset = mutableStateListOf(0f,0f,0f,0f,0f,0f,0f,0f,0f)
var x = mutableStateOf(0f)
var jerryLocate = mutableStateOf(0)
var tomLocate = mutableStateOf(0)
var xRight = mutableStateOf(0f)
var xLeft = mutableStateOf(0f)
var moveRight = mutableStateOf(false)
var moveLeft = mutableStateOf(false)
var jerryVelocity = mutableStateOf(15f)
var collided1 = mutableStateOf(false)
var collided2 = mutableStateOf(false)
var collided3 = mutableStateOf(false)
var collided4 = mutableStateOf(false)
var collided5 = mutableStateOf(false)
var collisionCount = mutableStateOf(0)
var reset = mutableStateOf(true)
var moveTomLeft = mutableStateOf(false)
var moveTomRight = mutableStateOf(false)
var sidewaysCollision = mutableStateOf(false)
var tomCatches = mutableStateOf(false)
var showWinnerPage = mutableStateOf(false)
var score = mutableStateOf(0f)
var HighScore = mutableStateOf(0)
var mode = mutableStateOf(0)
var level = mutableStateOf(1)
var jerryJump = mutableStateOf(false)
var tomJump = mutableStateOf(false)
var sizeDuringJump = mutableStateOf(1f)
var mp: MediaPlayer? = null
var time = mutableStateOf(0f)
var velocity = mutableStateOf((height.value + width.value)/200)
var acceleration = mutableStateOf(0.02f)

var movingJerry = mutableStateOf<Dimensions>(
    Dimensions(
        0f,
        0f,
        0f,
        0f
    )
)

var movingTom = mutableStateOf<Dimensions>(
    Dimensions(
        0f,
        0f,
        0f,
        0f
    )
)
var tomStarts = mutableStateOf(false)

var powerUp = mutableStateListOf(
    Dimensions(
        0f,
        0f,
        0f,
        0f
    ),
    Dimensions(
        0f,
        0f,
        0f,
        0f
    ),
    Dimensions(
        0f,
        0f,
        0f,
        0f
    )
)

var powerUpDisplay = mutableStateListOf(true, true, true)
var powerUpsCollected = mutableStateOf(0)
var makeDelay = mutableStateOf(false)

var movingBoxes = mutableStateListOf<Dimensions>(
    Dimensions(
        width.value / 5,
        width.value / 5,
        width.value / 6,
        height.value/ 2 + width.value / 10
    ),

    Dimensions(
        width.value / 5,
        width.value / 5,
        width.value / 1.2f,
        height.value/ 2 + width.value / 10
    ),

    Dimensions(
        width.value / 5,
        width.value / 5,
        width.value / 2,
        height.value / 4 + width.value / 10
    ),

    Dimensions(
        width.value / 5,
        width.value / 5,
        width.value / 6,
        width.value/10
    ),

    Dimensions(
        width.value / 5,
        width.value / 5,
        width.value / 1.2f,
        width.value / 10
    ),

    Dimensions(
        width.value / 5,
        width.value / 5,
        width.value / 2,
        - height.value / 4 + width.value / 10
    ),

    Dimensions(
        width.value / 5,
        width.value / 5,
        width.value / 6,
        - height.value / 2 + width.value/10
    ),

    Dimensions(
        width.value / 5,
        width.value / 5,
        width.value / 1.2f,
        - height.value / 2 + width.value/10
    ),

    Dimensions(
        width.value / 5,
        width.value / 5,
        width.value / 2,
        - 3 * height.value / 4 + width.value / 10
    )
)