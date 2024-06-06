package com.example.thecheesechaseapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun Game(modifier: Modifier, navController: NavController){
    Box(
        modifier = modifier.fillMaxSize(),
    ){
        GameCanvas()
    }
}

@Composable
fun GameCanvas(){
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(78, 92, 170))
    ){
        val jerryHappy = ImageBitmap.imageResource(id = R.drawable.file)
        drawLine(
            color = Color.White,
            start = Offset(size.width/3, 0f),
            end = Offset(size.width/3, size.height),
            strokeWidth = 2f
        )

        drawLine(
            color = Color.White,
            start = Offset(size.width/1.5f, 0f),
            end = Offset(size.width/1.5f, size.height),
            strokeWidth = 2f
        )

        drawImage(
            topLeft = Offset(size.width/3, 0f),
            image =
        )
    }
}