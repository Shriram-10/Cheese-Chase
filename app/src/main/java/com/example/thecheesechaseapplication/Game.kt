package com.example.thecheesechaseapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

var height = mutableStateOf(0f)
var width = mutableStateOf(0f)
var yBox = mutableStateListOf<Float>(0f,0f, height.value/4,height.value/2,height.value/2,0f,0f,0f,0f,0f)
var x = mutableStateOf(0f)

@Composable
fun Game(modifier: Modifier, navController: NavController){
    var elapsedTime by remember { mutableStateOf(0f) }
    LaunchedEffect(Unit){
        delay(1000)
        val startTime = withFrameMillis { it }
        while(true) {
            elapsedTime = (withFrameMillis { it } - startTime) * 0.001f
        }
    }
    Box(
        modifier = modifier.fillMaxSize(),
    ){
        GameCanvas()
        Column {
            Text(
                text = elapsedTime.roundToInt().toString(),
                color = Color.White
            )
            Text(
                text = height.value.toString() + "\n" + width.value.toString(),
                color = Color.White
            )
        }
    }
}

@Composable
fun GameCanvas() {
    val jerryHappy = ImageBitmap.imageResource(id = R.drawable.jerryhappy)
    val angryTom = ImageBitmap.imageResource(id = R.drawable.angrytom)
    var xRight by remember { mutableStateOf(0f) }
    var xLeft by remember { mutableStateOf(0f) }
    var jerryLocate by remember { mutableStateOf(1) }

    var y by remember { mutableStateOf(0f) }
    var velocity by remember { mutableStateOf((height.value + width.value)/200) }

    LaunchedEffect(Unit){
        delay(1000)
        val startTime = withFrameMillis { it }
        while(y < height.value/3){
            delay(16)
            val elapsedTime = withFrameMillis { it } - startTime
            y += elapsedTime / 100f
        }
    }

    LaunchedEffect(Unit){
        yBox = mutableStateListOf<Float>(0f,0f, height.value/4,height.value/2,height.value/2,-height.value/4,-height.value/2,-height.value/2,- 3 * height.value / 4,0f,0f)
        velocity = (height.value + width.value)/200
        delay(3000)
        /*var startTime = withFrameMillis { it }*/
        while(true){
            delay(16)
            /*var elapsedTime = withFrameMillis { it } - startTime*/
            for(i in 0..9){
                if (yBox[i] < height.value + 6 * width.value / 5){
                    yBox[i] += velocity
                } else {
                    yBox[i] -= height.value + 6 * width.value / 5
                }
            }
        }
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(78, 92, 170))
            .pointerInput(Unit){
                detectTapGestures (
                    onTap = {offset ->
                        if (offset.x > xRight && (offset.x > 2 * size.width / 3 || offset.x > size.width / 3)){
                            moveJerryRight(jerryLocate)
                        } else if (offset.x < xLeft && (offset.x < size.width / 3 || offset.x < 2 * size.width / 3)){
                            moveJerryLeft(jerryLocate)
                        }
                    }
                )
            }
    ){
        height.value = size.height
        width.value = size.width
        x.value = size.width/2
        xRight = x.value + size.width/15f
        xLeft = x.value - size.width/15f
        jerryLocate = 1

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

        drawCircle(
            color = Color.Black,
            radius = size.width/15f,
            center = Offset(x.value, size.height - y)
        )

        drawCircle(
            color = Color.Gray,
            radius = size.width/10f,
            center = Offset(size.width/2, size.height)
        )

        /*scale(scale = 0.4f){
            drawImage(
                image = jerryHappy,
                topLeft = Offset(size.width/3, size.height + size.width/2 - y),
            )
        }

        scale(scale = 0.4f){
            drawImage(
                image = angryTom,
                topLeft = Offset(size.width/3,  size.height + size.width),
            )
        }*/

        drawRect(
            topLeft = Offset(size.width/3f + size.width/15, yBox[8]),
            color = Color.Red,
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/2.5f - size.width/3, yBox[6]),
            color = Color.Red,
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/1.5f + size.width/15, yBox[7]),
            color = Color.Red,
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/3f + size.width/15, yBox[5]),
            color = Color.Red,
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/2.5f - size.width/3, yBox[0]),
            color = Color.Red,
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/1.5f + size.width/15, yBox[1]),
            color = Color.Red,
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/3f + size.width/15, yBox[2]),
            color = Color.Red,
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/2.5f - size.width/3, yBox[3]),
            color = Color.Red,
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/1.5f + size.width/15, yBox[4]),
            color = Color.Red,
            size = Size(size.width/5, size.width/5)
        )
    }
}

fun moveJerryRight(locate : Int){

}

fun moveJerryLeft(locate : Int){

}