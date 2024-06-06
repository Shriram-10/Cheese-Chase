package com.example.thecheesechaseapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay

@Composable
fun Game(modifier: Modifier, navController: NavController){
    Box(
        modifier = modifier.fillMaxSize(),
    ){
        GameCanvas()
    }
}

@Composable
fun TimeCalc() {

}

@Composable
fun GameCanvas(){
    val jerryHappy = ImageBitmap.imageResource(id = R.drawable.jerryhappy)
    val angryTom = ImageBitmap.imageResource(id = R.drawable.angrytom)
    var x by remember { mutableStateOf(0f) }
    var y by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit){
        delay(1000)
        y = 300f
        val startTime = withFrameMillis { it }
        while(y < 800){
            delay(16)
            val elapsedTime = withFrameMillis { it } - startTime
            y += elapsedTime / 100f
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(78, 92, 170))
    ){
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

        drawRect(
            topLeft = Offset(size.width/2.5f - size.width/3, 100f),
            color = Color.Red,
            size = Size(size.width/5, size.width/5)
        )

        scale(scale = 0.4f){
            drawImage(
                image = jerryHappy,
                topLeft = Offset(size.width/3 - 70f, size.height + 500 - y),
            )
        }

        scale(scale = 0.4f){
            drawImage(
                image = angryTom,
                topLeft = Offset(size.width/3f - 60, size.height + 1000f),
            )
        }
    }
}