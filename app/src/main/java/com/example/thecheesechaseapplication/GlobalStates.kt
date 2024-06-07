package com.example.thecheesechaseapplication

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf

var height = mutableStateOf(0f)
var width = mutableStateOf(0f)
var yBox = mutableStateListOf<Float>(0f,0f, height.value/4,height.value/2,height.value/2,0f,0f,0f,0f,0f)
var x = mutableStateOf(0f)
var jerryLocate = mutableStateOf(0)
var xRight = mutableStateOf(0f)
var xLeft = mutableStateOf(0f)
var moveRight = mutableStateOf(false)
var moveLeft = mutableStateOf(false)
var jerryVelocity = mutableStateOf(15f)
var movingJerry = mutableStateOf<Dimensions>(
    Dimensions(
        0f,
        0f,
        0f,
        0f
    )
)
var movingBoxes = mutableStateListOf<Dimensions>(
    Dimensions(
        width.value / 5,
        width.value / 5,
        width.value/10,
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
        height.value / 4 + width.value / 10
    ),
    Dimensions(
        width.value / 5,
        width.value / 5,
        width.value/10,
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
        - height.value / 4 + width.value / 10
    ),
    Dimensions(
        width.value / 5,
        width.value / 5,
        width.value/10,
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
    ),
    Dimensions(
        width.value / 5,
        width.value / 5,
        width.value/10,
        width.value/10
    ),
    Dimensions(
        width.value / 5,
        width.value / 5,
        width.value / 1.2f,
        width.value / 10
    )
)