package com.example.thecheesechaseapplication

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animate
import androidx.compose.animation.core.animateDecay
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
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
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlin.math.roundToInt
import kotlin.math.roundToLong

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
                    if (!sidewaysCollision.value) {
                        if (jerryLocate.value == 0) {
                            for (i in 0..9) {
                                if (movingBoxes[i].centerX == movingTom.value.centerX) {
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomLeft.value = true
                                    }
                                }
                            }
                        } else if (tomLocate.value == 1) {
                            for (i in 0..9) {
                                if (movingBoxes[i].centerX == movingTom.value.centerX) {
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomLeft.value = true
                                    }
                                }
                            }
                        } else if (tomLocate.value == -1) {
                            for (i in 0..9) {
                                if (movingBoxes[i].centerX == movingTom.value.centerX) {
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomRight.value = true
                                    }
                                }
                            }
                        }
                    } else if (sidewaysCollision.value){
                        if (tomLocate.value == jerryLocate.value){
                            for (i in 0..9) {
                                if (movingTom.value.centerX == movingBoxes[i].centerX){
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomRight.value = true
                                    }
                                }
                            }
                        } else if (tomLocate.value == jerryLocate.value + 1){
                            for (i in 0..9) {
                                if (movingTom.value.centerX == movingBoxes[i].centerX){
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomLeft.value = true
                                    }
                                }
                            }
                        } else if (tomLocate.value == jerryLocate.value - 1){
                            for (i in 0..9) {
                                if (movingTom.value.centerX == movingBoxes[i].centerX){
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomRight.value = true
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (collisionCount.value == 1) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(4)
                if (!sidewaysCollision.value) {
                    if (tomLocate.value == jerryLocate.value) {
                        if (moveLeft.value) {
                            delay((height.value * 200 * 30 / (12 * (height.value + width.value))).roundToLong())
                            moveTomLeft.value = true
                        } else if (moveRight.value) {
                            delay((height.value * 200 * 30 / (12 * (height.value + width.value))).roundToLong())
                            moveTomRight.value = true
                        }
                    } else if (tomLocate.value == jerryLocate.value - 1) {
                        for (i in 0..9) {
                            if (movingBoxes[i].centerX == movingTom.value.centerX) {
                                if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                    moveTomRight.value = true
                                }
                            }
                        }
                        if (moveRight.value) {
                            delay((height.value * 200 * 30 / (12 * (height.value + width.value))).roundToLong())
                            moveTomRight.value = true
                        }
                    } else if (tomLocate.value == jerryLocate.value + 1) {
                        for (i in 0..9) {
                            if (movingBoxes[i].centerX == movingTom.value.centerX) {
                                if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                    moveTomLeft.value = true
                                }
                            }
                        }
                        if (moveLeft.value) {
                            delay((height.value * 200 * 30 / (12 * (height.value + width.value))).roundToLong())
                            moveTomLeft.value = true
                        }
                    }
                } else if (sidewaysCollision.value && movingTom.value.centerY <= height.value * 4 / 5){
                    if (tomLocate.value == jerryLocate.value) {
                        if (moveLeft.value) {
                            delay((height.value * 200 * 30 / (12 * (height.value + width.value))).roundToLong())
                            moveTomLeft.value = true
                        } else if (moveRight.value) {
                            delay((height.value * 200 * 30 / (12 * (height.value + width.value))).roundToLong())
                            moveTomRight.value = true
                        }
                    } else if (tomLocate.value == jerryLocate.value - 1) {
                        for (i in 0..9) {
                            if (movingBoxes[i].centerX == movingTom.value.centerX) {
                                if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                    moveTomRight.value = true
                                }
                            }
                        }
                        if (moveRight.value) {
                            delay((height.value * 200 * 30 / (12 * (height.value + width.value))).roundToLong())
                            moveTomRight.value = true
                        }
                    } else if (tomLocate.value == jerryLocate.value + 1) {
                        for (i in 0..9) {
                            if (movingBoxes[i].centerX == movingTom.value.centerX) {
                                if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                    moveTomLeft.value = true
                                }
                            }
                        }
                        if (moveLeft.value) {
                            delay((height.value * 200 * 30 / (12 * (height.value + width.value))).roundToLong())
                            moveTomLeft.value = true
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(key1 = reset.value){
        while(collisionCount.value == 0){
            delay(8)
            if (collided1.value){
                delay(400)
                collided1.value = false
            } else if (collided2.value){
                delay(400)
                collided2.value = false
            } else if (collided3.value){
                delay(400)
                collided3.value = false
            } else if (collided4.value){
                delay(400)
                collided4.value = false
            } else if (collided5.value){
                delay(400)
                collided5.value = false
            }
            reset.value = false
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
    ){
        GameCanvas(modifier)

        if (collisionCount.value == 2){
            WinnerPage(modifier)
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
    if (moveTomLeft.value){
        MoveTomLeft()
    }
    if (moveTomRight.value){
        MoveTomRight()
    }
    if (moveTomLeft.value && moveTomRight.value){
        moveTomLeft.value = !moveTomLeft.value
        moveTomRight.value = !moveTomRight.value
    }
}

@Composable
fun GameCanvas(modifier:Modifier) {
    var y by remember { mutableStateOf(0f) }
    var velocity by remember { mutableStateOf((height.value + width.value)/200) }
    xRight.value = x.value + width.value/15f
    xLeft.value = x.value - width.value/15f
    val infiniteTransition = rememberInfiniteTransition()
    val colors = infiniteTransition.animateColor(
        Color(64,64,64),
        Color(190,53,50),
        animationSpec = infiniteRepeatable(tween(300),
            repeatMode = RepeatMode.Reverse
        )
    ).value


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
            .background(Color(210,163,118))
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        if (collisionCount.value < 2) {
                            if (it.x < xLeft.value) {
                                moveLeft.value = true
                            } else if (it.x > xRight.value) {
                                moveRight.value = true
                            }
                        }
                    }
                )
            }
    ){
        height.value = size.height
        width.value = size.width

        drawLine(
            color = Color(71,61,52),
            start = Offset(size.width/3, 0f),
            end = Offset(size.width/3, size.height),
            strokeWidth = 4f
        )

        drawLine(
            color = Color(71,61,52),
            start = Offset(size.width/1.5f, 0f),
            end = Offset(size.width/1.5f, size.height),
            strokeWidth = 4f
        )

        drawRect(
            topLeft = Offset(size.width/3f + size.width/15, yBox[8]),
            color = Color(128,56,42),
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/2.5f - size.width/3, yBox[6]),
            color = Color(128,56,42),
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/1.5f + size.width/15, yBox[7]),
            color = Color(128,56,42),
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/3f + size.width/15, yBox[5]),
            color = Color(128,56,42),
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/2.5f - size.width/3, yBox[0]),
            color = Color(128,56,42),
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/1.5f + size.width/15, yBox[1]),
            color = Color(128,56,42),
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/3f + size.width/15, yBox[2]),
            color = Color(128,56,42),
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/2.5f - size.width/3, yBox[3]),
            color = Color(128,56,42),
            size = Size(size.width/5, size.width/5)
        )

        drawRect(
            topLeft = Offset(size.width/1.5f + size.width/15, yBox[4]),
            color = Color(128,56,42),
            size = Size(size.width/5, size.width/5)
        )

        drawCircle(
            color = if (collisionCount.value == 1) colors else Color.Black,
            radius = size.width/15f,
            center = Offset(movingJerry.value.centerX, size.height - y)
        )

        if (collisionCount.value >= 1){
            drawCircle(
                color = Color.Gray,
                radius = size.width/15f,
                center = Offset(movingTom.value.centerX, movingTom.value.centerY)
            )
            drawCircle(
                color = Color.DarkGray,
                radius = size.width/12f,
                center = Offset(movingTom.value.centerX, movingTom.value.centerY),
                style = Stroke(width = 8f)
            )
        }
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
                         sidewaysCollision.value = true
                         collided4.value = true
                     } else if (movingBoxes[i].centerX == width.value * 5 / 6){
                         collisionCount.value += 1
                         sidewaysCollision.value = true
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
                 sidewaysCollision.value = true
                 collided2.value = true
             }
         } else if (movingBoxes[i].centerX == width.value / 2 && movingJerry.value.centerX - movingJerry.value.width / 2 <= movingBoxes[i].centerX + movingBoxes[i].width / 2 && movingJerry.value.centerX + movingJerry.value.width / 2 >= movingBoxes[i].centerX - movingBoxes[i].width / 2){
             if (movingBoxes[i].centerY - movingBoxes[i].height / 2 <= movingJerry.value.centerY + movingJerry.value.height / 2 && movingBoxes[i].centerY + movingBoxes[i].height / 2 >= movingJerry.value.centerY - movingJerry.value.height / 2){
                 collisionCount.value += 1
                 sidewaysCollision.value = true
                 collided3.value = true
             }
         }
     }
}

@Composable
fun MoveTomLeft(){
    if (tomLocate.value == 1){
        LaunchedEffect(Unit){
            while(movingTom.value.centerX > width.value/2){
                delay(8)
                movingTom.value.centerX -= jerryVelocity.value
            }
            if (movingTom.value.centerX <= width.value/2){
                tomLocate.value = 0
                moveTomLeft.value = false
            }
        }
    } else if (tomLocate.value == 0){
        LaunchedEffect(Unit){
            while(movingTom.value.centerX > width.value/6){
                delay(8)
                movingTom.value.centerX -= jerryVelocity.value
            }
            if (movingTom.value.centerX <= width.value/6){
                tomLocate.value = -1
                moveTomLeft.value = false
            }
        }
    }
}

@Composable
fun MoveTomRight(){
    if (tomLocate.value == -1){
        LaunchedEffect(Unit){
            while(movingTom.value.centerX < width.value/2){
                delay(8)
                movingTom.value.centerX += jerryVelocity.value
            }
            if (movingTom.value.centerX >= width.value/2){
                tomLocate.value = 0
                moveTomRight.value = false
            }
        }
    } else if (tomLocate.value == 0){
        LaunchedEffect(Unit){
            while(movingTom.value.centerX < width.value * 5/6){
                delay(8)
                movingTom.value.centerX += jerryVelocity.value
            }
            if (movingTom.value.centerX >= width.value * 5/6){
                tomLocate.value = 1
                moveTomRight.value = false
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GamePreview(){
    Game(modifier = Modifier, navController = rememberNavController())
}