package com.example.thecheesechaseapplication

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@Composable
fun Game(modifier: Modifier, navController: NavController){
    var elapsedTime by remember { mutableStateOf(0f) }

    LaunchedEffect(Unit){
        delay(1000)
        val startTime = withFrameMillis { it }
        while(!(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value)) {
            elapsedTime = (withFrameMillis { it } - startTime) * 0.001f
        }
    }

    if (!(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value)){
        LaunchedEffect(Unit){
            delay(400)
            while(!(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value)){
                delay(4)
                checkCollision()
            }
        }
    }

    LaunchedEffect(Unit){
        while(true){
            delay(4)
            if (collisionCount.value == 1){
                while(movingTom.value.centerY >= height.value * 4 / 5){
                    delay(8)
                    movingTom.value.centerY -= jerryVelocity.value / 4
                }
            }
        }
    }

    LaunchedEffect(Unit){
        while(true){
            delay(8)
            if (collided1.value){
                delay(2000)
                collided1.value = false
            } else if (collided2.value){
                delay(2000)
                collided2.value = false
            } else if (collided3.value){
                delay(2000)
                collided3.value = false
            } else if (collided4.value){
                delay(2000)
                collided4.value = false
            } else if (collided5.value){
                delay(2000)
                collided5.value = false
            }
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
    ){
        GameCanvas(modifier)

        if (collisionCount.value == 2){
            WinnerPage(modifier)
        }

        Column {
            Text(
                text = collided1.value.toString(),
                color = Color.White
            )
            Text(
                text = collided2.value.toString(),
                color = Color.White
            )
            Text(
                text = collided3.value.toString(),
                color = Color.White
            )
            Text(
                text = collided4.value.toString(),
                color = Color.White
            )
            Text(
                text = collided5.value.toString(),
                color = Color.White
            )
            Text(
                text = collisionCount.value.toString(),
                color = Color.White
            )
            /*Text(
                text = jerryLocate.value.toString(),
                color = Color.White
            )
            Text(
                text = movingJerry.value.centerX.toString(),
                color = Color.White
            )
            Text(
                text = movingBoxes[0].centerX.toString(),
                color = Color.White
            )
            Text(
                text = movingBoxes[3].centerX.toString(),
                color = Color.White
            )*/
            /*Text(
                text = elapsedTime.roundToInt().toString(),
                color = Color.White
            )
            Text(
                text = height.value.toString() + "\n" + width.value.toString(),
                color = Color.White
            )
            Text(
                text = x.value.toString(),
                color = Color.White
            )
            Text(
                text = jerryLocate.value.toString(),
                color = Color.White
            )
            Text(
                text = xLeft.value.toString(),
                color = Color.White
            )
            Text(
                text = xRight.value.toString(),
                color = Color.White
            )
            Text(
                text = moveLeft.value.toString(),
                color = Color.White
            )
            Text(
                text = moveRight.value.toString(),
                color = Color.White
            )
            Text(
                text = movingBoxes[0].toString(),
                color = Color.White
            )
            Text(
                text = movingBoxes[1].toString(),
                color = Color.White
            )
            Text(
                text = movingBoxes[2].toString(),
                color = Color.White
            )
            Text(
                text = movingBoxes[3].toString(),
                color = Color.White
            )
            Text(
                text = movingBoxes[4].toString(),
                color = Color.White
            )
            Text(
                text = movingBoxes[5].toString(),
                color = Color.White
            )
            Text(
                text = movingBoxes[6].toString(),
                color = Color.White
            )
            Text(
                text = movingBoxes[7].toString(),
                color = Color.White
            )
            Text(
                text = movingBoxes[8].toString(),
                color = Color.White
            )
            Text(
                text = movingJerry.value.toString(),
                color = Color.White
            )*/
        }
    }
    if (moveLeft.value){
        MoveJerryLeft()
        xRight.value = x.value + width.value/15f
        xLeft.value = x.value - width.value/15f
    }
    if (moveRight.value){
        MoveJerryRight()
        xRight.value = x.value + width.value/15f
        xLeft.value = x.value - width.value/15f
    }
    if (moveLeft.value && moveRight.value){
        moveLeft.value = !moveLeft.value
        moveRight.value = !moveRight.value
    }
}

@Composable
fun GameCanvas(modifier:Modifier) {
    val jerryHappy = ImageBitmap.imageResource(id = R.drawable.jerryhappy)
    val angryTom = ImageBitmap.imageResource(id = R.drawable.angrytom)
    var y by remember { mutableStateOf(0f) }
    var velocity by remember { mutableStateOf((height.value + width.value)/200) }
    xRight.value = x.value + width.value/15f
    xLeft.value = x.value - width.value/15f


    if (collisionCount.value < 2){
        LaunchedEffect(Unit){
            delay(500)
            val startTime = withFrameMillis { it }
            while(y < height.value/3){
                delay(8)
                val elapsedTime = withFrameMillis { it } - startTime
                y += elapsedTime / 50f
                movingJerry.value.centerY -= elapsedTime / 50f
            }
        }

        LaunchedEffect(Unit){
            yBox = mutableStateListOf<Float>(0f,0f, height.value/4,height.value/2,height.value/2,-height.value/4,-height.value/2,-height.value/2,- 3 * height.value / 4,0f,0f)
            velocity = (height.value + width.value)/200
            delay(1250)
            while(true){
                delay(16)
                for(i in 0..9){
                    if (yBox[i] < height.value + width.value){
                        yBox[i] += velocity
                        movingBoxes[i].centerY += velocity
                    } else {
                        yBox[i] -= height.value + width.value
                        movingBoxes[i].centerY -= height.value + width.value
                    }
                }
            }
        }
    } else if (collisionCount.value == 3){
        y = 0f
        yBox[0] = 0f
        yBox[1] = 0f
        yBox[2] = height.value / 4
        yBox[3] = height.value / 2
        yBox[4] = height.value / 2
        yBox[5] = - height.value / 4
        yBox[6] = - height.value / 2
        yBox[7] = - height.value / 2
        yBox[8] = - height.value * 3 / 4
        yBox[9] = 0f
        collisionCount.value = 0
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(78, 92, 170))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (it.x < xLeft.value) {
                            moveLeft.value = true
                        } else if (it.x > xRight.value) {
                            moveRight.value = true
                        }
                    }
                )
            }
    ){
        height.value = size.height
        width.value = size.width

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
            radius = size.width/15f,
            center = Offset(movingTom.value.centerX, movingTom.value.centerY)
        )

        /*scale(scale = 0.4f){
            drawImage(
                image = jerryHappy,
                topLeft = Offset(x.value - width.value / 4, size.height + size.width - y),
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
            color = Color.Green,
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/2.5f - size.width/3, yBox[6]),
            color = Color.Blue,
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/1.5f + size.width/15, yBox[7]),
            color = Color.LightGray,
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/3f + size.width/15, yBox[5]),
            color = Color.Red,
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/2.5f - size.width/3, yBox[0]),
            color = Color.Yellow,
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/1.5f + size.width/15, yBox[1]),
            color = Color.DarkGray,
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/3f + size.width/15, yBox[2]),
            color = Color.White,
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/2.5f - size.width/3, yBox[3]),
            color = Color.Magenta,
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/1.5f + size.width/15, yBox[4]),
            color = Color.Cyan,
            size = Size(size.width/5, size.width/5)
        )
    }
}

@Composable
fun MoveJerryLeft(){
    if (jerryLocate.value == 1){
        LaunchedEffect(Unit){
            while(x.value > width.value/2){
                delay(8)
                x.value -= jerryVelocity.value
                movingJerry.value.centerX -= jerryVelocity.value
            }
            if (x.value <= width.value/2){
                jerryLocate.value = 0
                moveLeft.value = false
            }
        }
    } else if (jerryLocate.value == 0){
        LaunchedEffect(Unit){
            while(x.value > width.value/6){
                delay(8)
                x.value -= jerryVelocity.value
                movingJerry.value.centerX -= jerryVelocity.value
            }
            if (x.value <= width.value/6){
                jerryLocate.value = -1
                moveLeft.value = false
            }
        }
    }
}

@Composable
fun MoveJerryRight(){
    if (jerryLocate.value == -1){
        LaunchedEffect(Unit){
            while(x.value < width.value/2){
                delay(8)
                x.value += jerryVelocity.value
                movingJerry.value.centerX += jerryVelocity.value
            }
            if (x.value >= width.value/2){
                jerryLocate.value = 0
                moveRight.value = false
            }
        }
    } else if (jerryLocate.value == 0){
        LaunchedEffect(Unit){
            while(x.value < width.value * 5/6){
                delay(8)
                x.value += jerryVelocity.value
                movingJerry.value.centerX += jerryVelocity.value
            }
            if (x.value >= width.value * 5/6){
                jerryLocate.value = 1
                moveRight.value = false
            }
        }
    }
}

fun checkCollision(){
     for (i in 0..9){
         if (movingJerry.value.centerX < movingBoxes[i].centerX){
             if (movingJerry.value.centerY <= movingBoxes[i].centerY + movingBoxes[i].height / 2 && movingJerry.value.centerY >= movingBoxes[i].centerY - movingBoxes[i].height / 2){
                 if (- movingJerry.value.centerX + movingBoxes[i].centerX <= movingBoxes[i].width / 2 + movingJerry.value.width / 2){
                     if (movingBoxes[i].centerX == width.value / 2){
                         collisionCount.value += 1
                         collided4.value = true
                     } else if (movingBoxes[i].centerX == width.value * 5 / 6){
                         collisionCount.value += 1
                         collided5.value = true
                     }
                 }
             }
         } else if (movingBoxes[i].centerX - movingJerry.value.centerX <= movingBoxes[i].width / 2 + movingJerry.value.width / 2 && movingBoxes[i].centerX - movingJerry.value.centerX >= 0){
             if ((movingBoxes[i].centerY - movingBoxes[i].height / 2 <= movingJerry.value.centerY + movingJerry.value.height / 2) && (movingBoxes[i].centerY + movingBoxes[i].height / 2 >= movingJerry.value.centerY - movingJerry.value.height / 2)){
                 collisionCount.value += 1
                 collided1.value = true
             }
         } else if (movingBoxes[i].centerX == width.value / 6 && movingJerry.value.centerX - movingJerry.value.width / 2 <= movingBoxes[i].centerX + movingBoxes[i].width / 2){
             if (movingBoxes[i].centerY - movingBoxes[i].height / 2 <= movingJerry.value.centerY + movingJerry.value.height / 2 && movingBoxes[i].centerY + movingBoxes[i].height / 2 >= movingJerry.value.centerY - movingJerry.value.height / 2){
                 collisionCount.value += 1
                 collided2.value = true
             }
         } else if (movingBoxes[i].centerX == width.value / 2 && movingJerry.value.centerX - movingJerry.value.width / 2 <= movingBoxes[i].centerX + movingBoxes[i].width / 2 && movingJerry.value.centerX + movingJerry.value.width / 2 >= movingBoxes[i].centerX - movingBoxes[i].width / 2){
             if (movingBoxes[i].centerY - movingBoxes[i].height / 2 <= movingJerry.value.centerY + movingJerry.value.height / 2 && movingBoxes[i].centerY + movingBoxes[i].height / 2 >= movingJerry.value.centerY - movingJerry.value.height / 2){
                 collisionCount.value += 1
                 collided3.value = true
             }
         }
     }
}

@Composable
fun moveTom(){

}