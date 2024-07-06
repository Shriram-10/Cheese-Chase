package com.example.thecheesechaseapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Build
import android.view.OrientationEventListener
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameMillis
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.roundToInt
import kotlin.math.roundToLong
import kotlin.math.sin
import kotlin.random.Random

@Composable
fun AudioLoader() {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        fun loadAudioAsync() {
            coroutineScope.launch(Dispatchers.IO) {
                try {
                    val mediaPlayer = MediaPlayer()
                    mediaPlayer.setDataSource(
                        context,
                        Uri.parse("android.resource://" + context.packageName + "/" + R.raw.jump)
                    )
                    mediaPlayer.prepare()
                    mp = mediaPlayer
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
        loadAudioAsync()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Game(modifier: Modifier, navController: NavController, highScore: HighScoreManager, context: Context, dataViewModel: MainViewModel){
    val viewStateOfObstacles by dataViewModel.stateOfObstacleCourse
    AudioLoader()
    
    BackNavigator(navController = navController)

    if (collisionCount.value < collisionCountLimit.value) {
        LaunchedEffect(Unit) {
            delay(1000)
            val startTime = withFrameMillis { it }
            while (!(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value)) {
                time.value = (withFrameMillis { it } - startTime) * 0.001f
            }
        }
    }

    if (!makeDelay.value) {
        LaunchedEffect(Unit) {
            while (!makeDelay.value) {
                delay(4)
                powerUpCollection()
            }
        }
    }

    LaunchedEffect(Unit){
        while(true) {
            delay(500)
            velocity.value += acceleration.value
        }
    }

    if (!(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value) && !jerryJump.value && collisionCount.value < collisionCountLimit.value){
        LaunchedEffect(Unit){
            while(!(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value) && !jerryJump.value && collisionCount.value < collisionCountLimit.value){
                delay(4)
                checkCollision()
            }
        }
    }

    LaunchedEffect(Unit){
        while(true){
            delay(4)
            if (collisionCount.value == 1){
                while((movingTom.value.centerY >= height.value * 4 / 5 && !reverseTom.value && !chooseRewardSource.value) || (movingTom.value.centerY >= height.value)){
                    delay(8)
                    movingTom.value.centerY -= jerryVelocity.value / 4
                    if (!sidewaysCollision.value) {
                        if (jerryLocate.value == 0) {
                            for (i in 0..8) {
                                if (movingBoxes[i].centerX == movingTom.value.centerX) {
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomLeft.value = true
                                    }
                                }
                            }
                        } else if (tomLocate.value == 1) {
                            for (i in 0..8) {
                                if (movingBoxes[i].centerX == movingTom.value.centerX) {
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomLeft.value = true
                                    }
                                }
                            }
                        } else if (tomLocate.value == -1) {
                            for (i in 0..8) {
                                if (movingBoxes[i].centerX == movingTom.value.centerX) {
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomRight.value = true
                                    }
                                }
                            }
                        }
                    } else if (sidewaysCollision.value){
                        if (tomLocate.value == jerryLocate.value){
                            for (i in 0..8) {
                                if (movingTom.value.centerX == movingBoxes[i].centerX){
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomRight.value = true
                                    }
                                }
                            }
                        } else if (tomLocate.value == jerryLocate.value + 1 || tomLocate.value == jerryLocate.value + 2){
                            for (i in 0..8) {
                                if (movingTom.value.centerX == movingBoxes[i].centerX){
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomLeft.value = true
                                    }
                                }
                            }
                        } else if (tomLocate.value == jerryLocate.value - 1 || tomLocate.value == jerryLocate.value - 2){
                            for (i in 0..8) {
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

    var Vibrated = false
    LaunchedEffect(Unit){
        while(true) {
            delay(4)
            if (collisionCount.value in 1..collisionCountLimit.value - 1 && (collided1.value || collided2.value || collided3.value || collided4.value || collided5.value) && !Vibrated) {
                HapticFeedback().triggerHapticFeedback(context, 250)
                delay(250)
                Vibrated = true
            } else if (Vibrated){
                delay(1000)
                Vibrated = false
            }
        }
    }

    if (collisionCount.value in 1..collisionCountLimit.value - 1) {
        LaunchedEffect(Unit) {
            while (true) {
                delay(4)
                if ((movingTom.value.centerY < height.value * 4 / 5 && !chooseRewardSource.value) || (chooseRewardSource.value && movingTom.value.centerY <= height.value)) {
                    if (jerryJump.value) {
                        if (tomLocate.value == jerryLocate.value) {
                            for (i in 0..8) {
                                if (movingTom.value.centerX == movingBoxes[i].centerX && tomLocate.value != 1) {
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomRight.value = true
                                    }
                                } else if (movingTom.value.centerX == movingBoxes[i].centerX && tomLocate.value == 1) {
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomLeft.value = true
                                    }
                                }
                            }
                        } else if (tomLocate.value == jerryLocate.value + 1 || tomLocate.value == jerryLocate.value + 2) {
                            for (i in 0..8) {
                                if (movingTom.value.centerX == movingBoxes[i].centerX) {
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomLeft.value = true
                                    }
                                }
                            }
                        } else if (tomLocate.value == jerryLocate.value - 1 || tomLocate.value == jerryLocate.value - 2) {
                            for (i in 0..8) {
                                if (movingTom.value.centerX == movingBoxes[i].centerX) {
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomRight.value = true
                                    }
                                }
                            }
                        }
                    } else if (!sidewaysCollision.value) {
                        if (tomLocate.value == jerryLocate.value) {
                            if (moveLeft.value) {
                                delay((height.value * 30 / (12 * (velocity.value))).roundToLong())
                                moveTomLeft.value = true
                            } else if (moveRight.value) {
                                delay((height.value * 30 / (12 * (velocity.value))).roundToLong())
                                moveTomRight.value = true
                            }
                        } else if (tomLocate.value == jerryLocate.value - 1 || tomLocate.value == jerryLocate.value - 2) {
                            for (i in 0..8) {
                                if (movingBoxes[i].centerX == movingTom.value.centerX) {
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomRight.value = true
                                    }
                                }
                            }
                            if (moveRight.value) {
                                delay((height.value * 30 / (12 * (velocity.value))).roundToLong())
                                moveTomRight.value = true
                            }
                        } else if (tomLocate.value == jerryLocate.value + 1 || tomLocate.value == jerryLocate.value + 2) {
                            for (i in 0..8) {
                                if (movingBoxes[i].centerX == movingTom.value.centerX) {
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomLeft.value = true
                                    }
                                }
                            }
                            if (moveLeft.value) {
                                delay((height.value * 30 / (12 * (velocity.value))).roundToLong())
                                moveTomLeft.value = true
                            }
                        }
                    } else if (sidewaysCollision.value && movingTom.value.centerY <= height.value * 4 / 5) {
                        if (tomLocate.value == jerryLocate.value) {
                            if (moveLeft.value) {
                                delay((height.value * 30 / (12 * (velocity.value))).roundToLong())
                                moveTomLeft.value = true
                            } else if (moveRight.value) {
                                delay((height.value * 30 / (12 * (velocity.value))).roundToLong())
                                moveTomRight.value = true
                            }
                        } else if (tomLocate.value == jerryLocate.value - 1 || tomLocate.value == jerryLocate.value - 2) {
                            for (i in 0..8) {
                                if (movingBoxes[i].centerX == movingTom.value.centerX) {
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomRight.value = true
                                    }
                                }
                            }
                            if (moveRight.value) {
                                delay((height.value * 30 / (12 * (velocity.value))).roundToLong())
                                moveTomRight.value = true
                            }
                        } else if (tomLocate.value == jerryLocate.value + 1 || tomLocate.value == jerryLocate.value + 2) {
                            for (i in 0..8) {
                                if (movingBoxes[i].centerX == movingTom.value.centerX) {
                                    if (movingBoxes[i].centerY < movingTom.value.centerY && movingTom.value.centerY - movingBoxes[i].centerY < movingTom.value.height * 3 / 2 + movingBoxes[i].height / 2) {
                                        moveTomLeft.value = true
                                    }
                                }
                            }
                            if (moveLeft.value) {
                                delay((height.value * 30 / (12 * (velocity.value))).roundToLong())
                                moveTomLeft.value = true
                            }
                        }
                    }
                }
            }
        }
    } else if (collisionCount.value == collisionCountLimit.value){
        LaunchedEffect(Unit){
            var hasVibrated = false
            var hasVibrated2 = false
            var hasVibrated3 =  false
            while(movingTom.value.centerY >= height.value * 2 / 3 + movingJerry.value.height * 2 / 3) {
                if (mode.value == 2 || mode.value == 3) {
                    if (!hasVibrated) {
                        HapticFeedback().triggerHapticFeedback(context, 150)
                        delay(150)
                        hasVibrated = true
                    }
                    if (!hasVibrated2) {
                        delay(200)
                        HapticFeedback().triggerHapticFeedback(context, 100)
                        delay(100)
                        hasVibrated2 = true
                    }
                    if (!hasVibrated3) {
                        delay(350)
                        HapticFeedback().triggerHapticFeedback(context, 200)
                        delay(200)
                        hasVibrated3 = true
                    }
                } else if (mode.value == 1){
                    HapticFeedback().triggerHapticFeedback(context, 200)
                }
                delay(8)
                if (jerryLocate.value == tomLocate.value) {
                    movingTom.value.centerY -= jerryVelocity.value
                } else if (jerryLocate.value == tomLocate.value - 1 || jerryLocate.value == tomLocate.value - 2){
                    moveTomLeft.value = true
                } else if (jerryLocate.value == tomLocate.value + 1 || jerryLocate.value == tomLocate.value + 2){
                    moveTomRight.value = true
                }
            }
            delay(400)
            showWinnerPage.value = true
        }
    }

    LaunchedEffect(key1 = reset.value){
        while(collisionCount.value < collisionCountLimit.value){
            delay(8)
            if (collided1.value){
                delay((width.value * 4.5f * 30 / (7.5 * (velocity.value))).roundToLong())
                collided1.value = false
            } else if (collided2.value){
                delay((width.value * 4.2f * 30 / (7.5 * (velocity.value))).roundToLong())
                collided2.value = false
            } else if (collided3.value){
                delay((width.value * 4.2f * 30 / (7.5 * (velocity.value))).roundToLong())
                collided3.value = false
            } else if (collided4.value){
                delay((width.value * 4.2f * 30 / (7.5 * (velocity.value))).roundToLong())
                collided4.value = false
            } else if (collided5.value){
                delay((width.value * 4.2f * 30 / (7.5 * (velocity.value))).roundToLong())
                collided5.value = false
            }
            reset.value = false
        }
    }

    Box(
        modifier = modifier.fillMaxSize(),
    ){
        GameCanvas(modifier, context, dataViewModel)
        Column{
            Text(collisionCountLimit.value.toString())
            Text(powerUp1Value.value.toString())
            Text(powerUp1Amount.value.toString())
            Text(powerUp2Value.value.toString())
            Text(powerUp2Amount.value.toString())
            Text(usePowerUp.value.toString())
            Text(powerUpsCollected.value.toString())
            Text(viewStateOfObstacles.value.toString())
            Text(powerUpDisplay[0].toString())
            Text(powerUp[0].centerY.toString())
            Text(powerUpDisplay[1].toString())
            Text(powerUp[1].centerY.toString())
            Text(powerUpDisplay[2].toString())
            Text(powerUp[2].centerY.toString())
        }
        if (collisionCount.value < collisionCountLimit.value) {
            Column {
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    Button(
                        modifier = Modifier.height(36.dp),
                        onClick = {},
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(96, 149, 160)
                        ),
                        shape = RoundedCornerShape(25),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 8.dp,
                            pressedElevation = 8.dp
                        )
                    ) {
                        Text(
                            text = "Score : ${(score.value / 20).roundToInt()}✨",
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.width((width.value / 40).dp))
                }
            }
        }

        if (showWinnerPage.value){
            WinnerPage(highScore, navController, dataViewModel)
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
    if (jerryJump.value){
        mp?.start()
        LaunchedEffect(Unit){
            if (!autoJump.value) {
                delay((width.value * 2.2f * 30 / (7.5 * (velocity.value))).roundToLong())
                jerryJump.value = false
            } else {
                if (usePowerUp.value == 1){
                    delay(1000 * powerUp1Amount.value.toLong())
                    powerUp1Amount.value = 0
                    usePowerUp.value = 0
                    jerryJump.value = false
                    autoJump.value = false
                } else if (usePowerUp.value == 2){
                    delay(1000 * powerUp2Amount.value.toLong())
                    powerUp2Amount.value = 0
                    usePowerUp.value = 0
                    jerryJump.value = false
                    autoJump.value = false
                }
            }
        }
        if (!chooseImageSource.value) {
            LaunchedEffect(Unit) {
                while (sizeDuringJump.value <= 1.3f) {
                    delay(4)
                    sizeDuringJump.value += 0.3f * 18 / (width.value * 30 / (7.5f * 2 * (velocity.value)))
                }
                delay((width.value * 1.8 * 30 / (7.5 * 2 * ((width.value + height.value) / 200))).roundToLong())
                while (sizeDuringJump.value > 1f) {
                    delay(4)
                    sizeDuringJump.value -= 0.3f * 18 / (width.value * 30 / (7.5f * 2 * (velocity.value)))
                }
            }
        } else {
            if (!autoJump.value) {
                LaunchedEffect(Unit) {
                    while (sizeDuringJump.value <= 0.24f) {
                        delay(4)
                        sizeDuringJump.value += 0.05f * 18 / (width.value * 30 / (7.5f * 2 * (velocity.value)))
                    }
                    delay((width.value * 2.2 * 30 / (7.5 * 2 * ((width.value + height.value) / 200))).roundToLong())
                    while (sizeDuringJump.value > 0.2f) {
                        delay(4)
                        sizeDuringJump.value -= 0.08f * 18 / (width.value * 30 / (7.5f * 2 * (velocity.value)))
                    }
                }
            } else {
                LaunchedEffect(Unit) {
                    while (sizeDuringJump.value <= 0.24f) {
                        delay(4)
                        sizeDuringJump.value += 0.05f * 18 / (width.value * 30 / (7.5f * 2 * (velocity.value)))
                    }
                    if (usePowerUp.value == 1) {
                        delay((powerUp1Amount.value * 3 / 4 * 1000).toLong())
                    } else if (usePowerUp.value == 2) {
                        delay((powerUp2Amount.value * 3 / 4 * 1000).toLong())
                    }
                    while (sizeDuringJump.value > 0.2f) {
                        delay(4)
                        sizeDuringJump.value -= 0.08f * 18 / (width.value * 30 / (7.5f * 2 * (velocity.value)))
                    }
                    if (usePowerUp.value == 1){
                        powerUp1Amount.value = 0
                        usePowerUp.value = 0
                    } else if (usePowerUp.value == 2){
                        powerUp2Amount.value = 0
                        usePowerUp.value = 0
                    }
                }
            }
        }
    }
    if (collided1.value || collided2.value || collided3.value || collided4.value || collided5.value){
        sizeDuringJump.value = if (!chooseImageSource.value) 1f else 0.2f
    }

    if (makeDelay.value){
        LaunchedEffect(Unit){
            delay(100)
            makeDelay.value = false
        }
    }

    if (powerUpsCollected.value == 1 && !activatePowerUp1.value){
        startTimer1.value = true
    }
    if (powerUpsCollected.value == 2 && !activatePowerUp2.value){
        startTimer2.value = true
    }

    if (reverseTom.value){
        LaunchedEffect(Unit) {
            while (movingTom.value.centerY <= height.value + width.value && fadeTom.value >= 0.01f) {
                delay(4)
                movingTom.value.centerY += velocity.value / 10
                fadeTom.value -= 0.005f
                if (collided1.value || collided2.value || collided3.value || collided4.value || collided5.value){
                    reverseTom.value = false
                }
            }
        }
    }

    if (movingTom.value.centerY >= height.value + width.value || fadeTom.value < 0.01f){
        collisionCount.value -= 1
        fadeTom.value = 1f
        reverseTom.value = false
    }

    if (shatterBlocks.value){
        LaunchedEffect(Unit){
            delay((2.5f * height.value / velocity.value * 15).toLong())
            resetBoxes.value = 0
            shatterBlocks.value = false
        }
    }

    if (scoreSpeeding.value == 1){
        LaunchedEffect(Unit){
            delay((2.5 * height.value / velocity.value * 15).toLong())
            scoreSpeeding.value -= 1
            if (powerUp1Amount.value != 0){
                powerUp1Amount.value = 0
            } else if (powerUp2Amount.value != 0){
                powerUp2Amount.value = 0
            }
        }
    }
    if (scoreSpeeding.value == 2) {
        LaunchedEffect(Unit) {
            delay((2.5 * height.value / velocity.value * 15).toLong())
            scoreSpeeding.value -= 2
            powerUp1Amount.value = 0
            powerUp2Amount.value = 0
        }
    }
    if (startTimer1.value){
        UpdateTimer()
    }
    if (startTimer2.value) {
        UpdateTimer()
    }

    if (autoJump.value){
        LaunchedEffect(Unit) {
            jerryJump.value = true
        }
    }

    if ((powerUp1Value.value == 3 || powerUp2Value.value == 3) && chooseRewardSource.value) {
        LaunchedEffect(Unit) {
            tomClosingIn.value = true
        }
    }

    if (tomClosingIn.value){
        LaunchedEffect(Unit) {
            viewTom.value = true
            if (collisionCount.value < collisionCountLimit.value) {
                currentTomLocation.value = movingTom.value.centerY
                while(movingTom.value.centerY >= currentTomLocation.value - height.value / 20 * (powerUp1Amount.value + powerUp2Amount.value)){
                    delay(4)
                    movingTom.value.centerY -= jerryVelocity.value / 4
                    if (movingTom.value.centerY < height.value * 2 / 3 + movingJerry.value.height * 2 / 3){
                        if (jerryLocate.value == tomLocate.value - 1 || jerryLocate.value == tomLocate.value - 2){
                            moveTomLeft.value = true
                        } else if (jerryLocate.value == tomLocate.value + 1 || jerryLocate.value == tomLocate.value + 2){
                            moveTomRight.value = true
                        }
                        HapticFeedback().triggerHapticFeedback(context, 250)
                        delay(500)
                        collisionCount.value = collisionCountLimit.value
                    }
                    if (movingTom.value.centerY < currentTomLocation.value - height.value / 20 * (powerUp1Amount.value + powerUp2Amount.value)){
                        powerUp1Amount.value = 0
                        powerUp2Amount.value = 0
                        tomClosingIn.value = false
                    }
                }
            }
        }
    }

    if (powerUp1Value.value == 3 && activatePowerUp1.value && chooseRewardSource.value){
        LaunchedEffect(Unit){
            delay(2500)
            circularTimer1.value = 0f
            powerUpInit1.value = 0
            usePowerUp.value = 0
            activatePowerUp1.value = false
        }
    } else if (powerUp2Value.value == 3 && activatePowerUp2.value && chooseRewardSource.value){
        LaunchedEffect(Unit){
            delay(2500)
            circularTimer2.value = 0f
            powerUpInit1.value = 0
            usePowerUp.value = 0
            activatePowerUp2.value = false
        }
    } else if (powerUp1Value.value == 3 && !activatePowerUp1.value && chooseRewardSource.value) {
        LaunchedEffect(Unit) {
            if (powerUpsCollected.value == 1) {
                powerUpsCollected.value -= 1
            } else if (powerUpsCollected.value == 2) {
                powerUpsCollected.value -= 2
            }
            startTimer1.value = false
            powerUp1Value.value = 0
        }
    } else if (powerUp2Value.value == 3 && !activatePowerUp2.value && chooseRewardSource.value) {
        LaunchedEffect(Unit) {
            if (powerUpsCollected.value == 2 || powerUpsCollected.value == 1) {
                powerUpsCollected.value -= 1
            }
            startTimer2.value = false
            powerUp2Value.value = 0
        }
    }
}

@Composable
fun UpdateTimer(){
    if (powerUpsCollected.value == 2 && collisionCount.value < collisionCountLimit.value || startTimer2.value){
        LaunchedEffect(Unit) {
            while (circularTimer2.value < 360) {
                delay(8)
                circularTimer2.value += (360f/375f)
            }
            activatePowerUp2.value = true
            startTimer2.value = false
        }
    }
    if (powerUpsCollected.value >= 1 && collisionCount.value < collisionCountLimit.value || startTimer1.value){
        LaunchedEffect(Unit) {
            while (circularTimer1.value < 360) {
                delay(8)
                circularTimer1.value += (360f/375f)
            }
            activatePowerUp1.value = true
            startTimer1.value = false
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun GameCanvas(modifier:Modifier, context: Context, dataViewModel: MainViewModel) {
    val bitmapJerry = LoadImageAsBitmap(url = "https://chasedeux.vercel.app/image?character=jerry")
    val bitmapTom = LoadImageAsBitmap(url = "https://chasedeux.vercel.app/image?character=tom")
    val bitmapObstacle = LoadImageAsBitmap(url = "https://chasedeux.vercel.app/image?character=obstacle")

    val viewState by dataViewModel.stateOfHitHindrance
    val viewStateOfObstacles by dataViewModel.stateOfObstacleCourse
    val infiniteTransition = rememberInfiniteTransition()
    
    val animatedColors = infiniteTransition.animateColor(
        Color(180,60,40).copy(0.5f),
        Color(234,51,128).copy(alpha = 0.5f),
        animationSpec = infiniteRepeatable(tween(200),
            repeatMode = RepeatMode.Reverse
        )
    ).value

    if (chooseGyro.value && mode.value == 3) {
        DisposableEffect(context) {
            val orientationListener =
                object : OrientationEventListener(context, SensorManager.SENSOR_DELAY_GAME) {
                    override fun onOrientationChanged(orientationDegrees: Int) {
                        orientation.value = orientationDegrees
                        if (!fixPosition.value && !(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value) && collisionCount.value < collisionCountLimit.value) {
                            when (orientationDegrees) {
                                in 15..30 -> {
                                    moveRight.value = true;
                                    fixPosition.value = true
                                }

                                in 330..345 -> {
                                    moveLeft.value = true
                                    fixPosition.value = true
                                }
                            }
                        } else {
                            if (orientation.value < 15 || orientation.value > 345) {
                                fixPosition.value = false
                            }
                        }
                    }
                }

            if (orientationListener.canDetectOrientation()) {
                orientationListener.enable()
            }

            onDispose {
                orientationListener.disable()
            }
        }
    }

    var y by remember { mutableStateOf(0f) }
    if (collisionCount.value == 0 && resetObstacles.value){
        y = 0f
        resetObstacles.value = false
    }
    xRight.value = x.value + width.value/15f
    xLeft.value = x.value - width.value/15f

    val colors = infiniteTransition.animateColor(
        Color(64,64,64),
        Color(190,53,50),
        animationSpec = infiniteRepeatable(tween(300),
            repeatMode = RepeatMode.Reverse
        )
    ).value

    val powerUpColors = listOf(
        Color.White.copy(0.3f),
        Color(234,51,128).copy(alpha = 0.5f)
    )

    val animatedPowerUpColors = listOf(
        Color.White.copy(0.3f),
        animatedColors
    )

    val shadowColors = listOf(
        Color.Black.copy(0.3f),
        Color.DarkGray.copy(alpha = 0.2f)
    )

    val yellowColors = listOf(
        Color(253,198,37).copy(0.5f),
        Color(252,242,152).copy(0.25f)
    )

    var obstaclesList : MutableList<String> = mutableListOf()

    if (collisionCount.value < collisionCountLimit.value){
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
            while(true){
                delay(4)
                if (fetchCourse.value){
                    dataViewModel.fetchObstacleCourse(9)
                    delay(20)
                    fetchCourse.value = false
                }
            }
        }

        LaunchedEffect(Unit){
            while(true){
                delay(4)
                if (obstaclesList.count { it == "L" || it == "R" || it == "M" } == 0){
                    obstaclesList = viewStateOfObstacles.value!!
                    fetchCourse.value = true
                    if (initiatePowerUpUpdate.value && obstaclesList.count { it == "B" } <= 3) {
                        for (i in 0..obstaclesList.count { it == "B"}) {
                            if (obstaclesList.count { it == "B" } != 0) {
                                val x = Random.nextInt(0, obstaclesList.count { it == "B" })
                                if (!powerUpDisplay[x]) {
                                    powerUpDisplay[x] = true
                                    powerUp[x].centerY = -Random.nextFloat() * (height.value + width.value)
                                    for (j in 0..8) {
                                        if (powerUp[x].centerX == movingBoxes[j].centerX) {
                                            if (powerUp[x].centerY >= movingBoxes[j].centerY - movingBoxes[j].height / 2 - powerUp[x].height / 2 && powerUp[x].centerY <= movingBoxes[j].centerY + movingBoxes[j].height / 2 + powerUp[x].height / 2) {
                                                powerUp[x].centerY += powerUp[x].height / 2 + movingBoxes[j].height / 2
                                            }
                                        }
                                    }

                                    for (j in 0..2) {
                                        if (powerUp[x].centerX == powerUp[j].centerX && j != x) {
                                            if (powerUp[x].centerY >= powerUp[j].centerY - powerUp[j].height / 2 - powerUp[x].height / 2 && powerUp[x].centerY <= powerUp[j].centerY + powerUp[j].height / 2 + powerUp[x].height / 2) {
                                                powerUpDisplay[x] = false
                                            }
                                        }
                                    }

                                    initiatePowerUpUpdate.value = false
                                }
                            }
                        }
                    }
                }
            }
        }

        LaunchedEffect(Unit) {
            yBox = mutableStateListOf<Float>(
                height.value / 2,
                height.value / 2,
                height.value / 4,
                0f,
                0f,
                -height.value / 4,
                -height.value / 2,
                -height.value / 2,
                -height.value * 3 / 4
            )
            velocity.value = (height.value + width.value) / 200
            delay(1250)

            while (true) {
                delay(16)
                if (!shatterBlocks.value) {
                    for (i in 0..8) {
                        if (yBox[i] < height.value + width.value) {
                            yBox[i] += if (!(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value)) velocity.value else (velocity.value) / 3
                            movingBoxes[i].centerY += if (!(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value)) velocity.value else (velocity.value) / 3
                        } else {
                            if (chooseRandomSource.value && !chooseObstaclesSource.value) {
                                yBoxLocate[i] = Random.nextInt(-1, 2)
                                if (yBoxLocate[i] == -1) {
                                    movingBoxes[i].centerX = width.value / 6
                                    yBoxLocate[i] = -1
                                } else if (yBoxLocate[i] == 0) {
                                    movingBoxes[i].centerX = width.value / 2
                                    yBoxLocate[i] = 0
                                } else if (yBoxLocate[i] == 1) {
                                    movingBoxes[i].centerX = width.value * 5 / 6
                                    yBoxLocate[i] = 1
                                }
                                yBoxOffset[i] = Random.nextFloat() * width.value / 5f
                                yBox[i] -= height.value + width.value + yBoxOffset[i]
                                movingBoxes[i].centerY -= height.value + width.value + yBoxOffset[i]
                            } else if (!chooseRandomSource.value && !chooseObstaclesSource.value){
                                movingBoxes[i].centerY -= height.value + width.value
                                yBox[i] -= height.value + width.value
                            } else if (chooseObstaclesSource.value){
                                if (obstaclesList[i] == "L"){
                                    yBoxLocate[i] = -1
                                    movingBoxes[i].centerX = width.value / 6
                                    obstaclesList[i] = "U"
                                } else if (obstaclesList[i] == "M") {
                                    yBoxLocate[i] = 0
                                    movingBoxes[i].centerX = width.value / 2
                                    obstaclesList[i] = "U"
                                } else if (obstaclesList[i] == "R"){
                                    yBoxLocate[i] = 1
                                    movingBoxes[i].centerX = width.value * 5 / 6
                                    obstaclesList[i] = "U"
                                }
                                yBoxOffset[i] = Random.nextFloat() * width.value / 5f
                                movingBoxes[i].centerY -= height.value + width.value + yBoxOffset[i]
                                yBox[i] -= height.value + width.value + yBoxOffset[i]
                            }
                        }
                    }
                } else if (shatterBlocks.value && resetBoxes.value == 0){
                    yBox = mutableStateListOf<Float>(
                        height.value / 2 - height.value,
                        height.value / 2 - height.value,
                        height.value / 4 - height.value,
                        0f - height.value,
                        0f - height.value,
                        -height.value / 4 - height.value,
                        -height.value / 2 - height.value,
                        -height.value / 2 - height.value,
                        -height.value * 3 / 4 - height.value
                    )
                    for (i in 0..8){
                        movingBoxes[i].centerY = yBox[i] + movingBoxes[i].height/2
                    }
                    resetBoxes.value = 1
                }

                for (i in 0..2) {
                    if (powerUp[i].centerY <= 2.5f * (height.value + width.value)) {
                        powerUp[i].centerY += if (!(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value)) velocity.value else (velocity.value) / 3
                    }
                    if (!chooseObstaclesSource.value) {
                         if (powerUp[i].centerY > 2.5f * (height.value + width.value)) {
                            powerUpDisplay[i] = Random.nextBoolean()
                            powerUp[i].centerY = -Random.nextFloat() * (height.value + width.value)
                        }
                    } else {
                        if (powerUp[i].centerY > (height.value + width.value)) {
                            powerUpDisplay[i] = false
                            initiatePowerUpUpdate.value = true
                        }
                    }
                    if (!shatterBlocks.value) {
                        for (j in 0..8) {
                            if (powerUp[i].centerX == movingBoxes[j].centerX) {
                                if (powerUp[i].centerY >= movingBoxes[j].centerY - movingBoxes[j].height / 2 - powerUp[i].height / 2 && powerUp[i].centerY <= movingBoxes[j].centerY + movingBoxes[j].height / 2 + powerUp[i].height / 2) {
                                    powerUpDisplay[i] = false
                                }
                            }
                        }
                    }
                }
                if (!chooseRewardSource.value) {
                    if (scoreSpeeding.value == 1) {
                        score.value += if (!(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value)) 3 * ((height.value + width.value) / 2000) else 3 * ((height.value + width.value) / 6000)
                    } else if (scoreSpeeding.value == 2) {
                        score.value += if (!(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value)) 6 * ((height.value + width.value) / 2000) else 6 * ((height.value + width.value) / 6000)
                    } else {
                        score.value += if (!(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value)) ((height.value + width.value) / 2000) else ((height.value + width.value) / 6000)
                    }
                } else {
                    if (scoreSpeeding.value == 1 && powerUp1Amount.value != 0) {
                        score.value += if (!(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value)) powerUp1Amount.value * ((height.value + width.value) / 2000) else 3 * ((height.value + width.value) / 6000)
                    } else if (scoreSpeeding.value == 1 && powerUp1Amount.value != 0){
                        score.value += if (!(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value)) powerUp2Amount.value * ((height.value + width.value) / 2000) else 3 * ((height.value + width.value) / 6000)
                    }else if (scoreSpeeding.value == 2) {
                        score.value += if (!(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value)) powerUp1Amount.value * powerUp2Amount.value * ((height.value + width.value) / 2000) else 6 * ((height.value + width.value) / 6000)
                    } else {
                        score.value += if (!(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value)) ((height.value + width.value) / 2000) else ((height.value + width.value) / 6000)
                    }
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(210, 163, 118))
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = {
                            if (collisionCount.value < collisionCountLimit.value && !(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value)) {
                                if (mode.value == 2 || mode.value == 3) {
                                    HapticFeedback().triggerHapticFeedback(context, 50)
                                }
                                if (it.x < xLeft.value) {
                                    moveLeft.value = true
                                } else if (it.x > xRight.value) {
                                    moveRight.value = true
                                }
                            }
                        }
                    )
                }
                .pointerInput(Unit) {
                    detectDragGestures(
                        onDrag = { change, dragAmount ->
                            change.consume()
                            if (dragAmount.y < 0 && !jerryJump.value) {
                                if (collisionCount.value < collisionCountLimit.value && !(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value)) {
                                    if (mode.value == 2 || mode.value == 3) {
                                        jerryJump.value = true
                                    }
                                }
                            }
                        }
                    )
                }
        ) {
            height.value = size.height
            width.value = size.width

            drawLine(
                color = Color(71, 61, 52),
                start = Offset(size.width / 3, 0f),
                end = Offset(size.width / 3, size.height),
                strokeWidth = 4f
            )

            drawLine(
                color = Color(71, 61, 52),
                start = Offset(size.width / 1.5f, 0f),
                end = Offset(size.width / 1.5f, size.height),
                strokeWidth = 4f
            )

            if (!shatterBlocks.value) {
                drawRect(
                    topLeft = Offset(movingBoxes[0].centerX - size.width / 10, yBox[0]),
                    color = Color(128, 56, 42),
                    size = Size(size.width / 5, size.width / 5)
                )

                drawRect(
                    topLeft = Offset(movingBoxes[1].centerX - size.width / 10, yBox[1]),
                    color = Color(128, 56, 42),
                    size = Size(size.width / 5, size.width / 5)
                )

                drawRect(
                    topLeft = Offset(movingBoxes[2].centerX - size.width / 10, yBox[2]),
                    color = Color(128, 56, 42),
                    size = Size(size.width / 5, size.width / 5)
                )

                drawRect(
                    topLeft = Offset(movingBoxes[3].centerX - size.width / 10, yBox[3]),
                    color = Color(128, 56, 42),
                    size = Size(size.width / 5, size.width / 5)
                )

                drawRect(
                    topLeft = Offset(movingBoxes[4].centerX - size.width / 10, yBox[4]),
                    color = Color(128, 56, 42),
                    size = Size(size.width / 5, size.width / 5)
                )

                drawRect(
                    topLeft = Offset(movingBoxes[5].centerX - size.width / 10, yBox[5]),
                    color = Color(128, 56, 42),
                    size = Size(size.width / 5, size.width / 5)
                )

                drawRect(
                    topLeft = Offset(movingBoxes[6].centerX - size.width / 10, yBox[6]),
                    color = Color(128, 56, 42),
                    size = Size(size.width / 5, size.width / 5)
                )

                drawRect(
                    topLeft = Offset(movingBoxes[7].centerX - size.width / 10, yBox[7]),
                    color = Color(128, 56, 42),
                    size = Size(size.width / 5, size.width / 5)
                )

                drawRect(
                    topLeft = Offset(movingBoxes[8].centerX - size.width / 10, yBox[8]),
                    color = Color(128, 56, 42),
                    size = Size(size.width / 5, size.width / 5)
                )

                if (chooseImageSource.value) {
                    drawCircle(
                        color = Color(253, 247, 82).copy(alpha = 0.5f),
                        radius = size.width / 18f,
                        center = Offset(movingBoxes[0].centerX, yBox[0])
                    )

                    withTransform({
                        scale(
                            0.4f,
                            0.4f,
                            pivot = Offset(size.width / 2, size.height / 2)
                        )
                    }) {
                        if (bitmapObstacle != null) {
                            drawImage(
                                image = bitmapObstacle.asImageBitmap(),
                                topLeft = Offset(
                                    (movingBoxes[0].centerX - size.width / 10 - width.value / 3) * 10 / 4,
                                    (yBox[0] - height.value / 3) * 10 / 4
                                )
                            )
                        }
                    }

                    drawCircle(
                        color = Color(253, 247, 82).copy(alpha = 0.5f),
                        radius = size.width / 18f,
                        center = Offset(movingBoxes[1].centerX, yBox[1])
                    )

                    withTransform({
                        scale(
                            0.4f,
                            0.4f,
                            pivot = Offset(size.width / 2, size.height / 2)
                        )
                    }) {
                        if (bitmapObstacle != null) {
                            drawImage(
                                image = bitmapObstacle.asImageBitmap(),
                                topLeft = Offset(
                                    (movingBoxes[1].centerX - size.width / 10 - width.value / 3) * 10 / 4,
                                    (yBox[1] - height.value / 3) * 10 / 4
                                )
                            )
                        }
                    }

                    drawCircle(
                        color = Color(253, 247, 82).copy(alpha = 0.5f),
                        radius = size.width / 18f,
                        center = Offset(movingBoxes[2].centerX, yBox[2])
                    )

                    withTransform({
                        scale(
                            0.4f,
                            0.4f,
                            pivot = Offset(size.width / 2, size.height / 2)
                        )
                    }) {
                        if (bitmapObstacle != null) {
                            drawImage(
                                image = bitmapObstacle.asImageBitmap(),
                                topLeft = Offset(
                                    (movingBoxes[2].centerX - size.width / 10 - width.value / 3) * 10 / 4,
                                    (yBox[2] - height.value / 3) * 10 / 4
                                )
                            )
                        }
                    }

                    drawCircle(
                        color = Color(253, 247, 82).copy(alpha = 0.5f),
                        radius = size.width / 18f,
                        center = Offset(movingBoxes[3].centerX, yBox[3])
                    )

                    withTransform({
                        scale(
                            0.4f,
                            0.4f,
                            pivot = Offset(size.width / 2, size.height / 2)
                        )
                    }) {
                        if (bitmapObstacle != null) {
                            drawImage(
                                image = bitmapObstacle.asImageBitmap(),
                                topLeft = Offset(
                                    (movingBoxes[3].centerX - size.width / 10 - width.value / 3) * 10 / 4,
                                    (yBox[3] - height.value / 3) * 10 / 4
                                )
                            )
                        }
                    }

                    drawCircle(
                        color = Color(253, 247, 82).copy(alpha = 0.5f),
                        radius = size.width / 18f,
                        center = Offset(movingBoxes[4].centerX, yBox[4])
                    )

                    withTransform({
                        scale(
                            0.4f,
                            0.4f,
                            pivot = Offset(size.width / 2, size.height / 2)
                        )
                    }) {
                        if (bitmapObstacle != null) {
                            drawImage(
                                image = bitmapObstacle.asImageBitmap(),
                                topLeft = Offset(
                                    (movingBoxes[4].centerX - size.width / 10 - width.value / 3) * 10 / 4,
                                    (yBox[4] - height.value / 3) * 10 / 4
                                )
                            )
                        }
                    }

                    drawCircle(
                        color = Color(253, 247, 82).copy(alpha = 0.5f),
                        radius = size.width / 18f,
                        center = Offset(movingBoxes[5].centerX, yBox[5])
                    )

                    withTransform({
                        scale(
                            0.4f,
                            0.4f,
                            pivot = Offset(size.width / 2, size.height / 2)
                        )
                    }) {
                        if (bitmapObstacle != null) {
                            drawImage(
                                image = bitmapObstacle.asImageBitmap(),
                                topLeft = Offset(
                                    (movingBoxes[5].centerX - size.width / 10 - width.value / 3) * 10 / 4,
                                    (yBox[5] - height.value / 3) * 10 / 4
                                )
                            )
                        }
                    }

                    drawCircle(
                        color = Color(253, 247, 82).copy(alpha = 0.5f),
                        radius = size.width / 18f,
                        center = Offset(movingBoxes[6].centerX, yBox[6])
                    )

                    withTransform({
                        scale(
                            0.4f,
                            0.4f,
                            pivot = Offset(size.width / 2, size.height / 2)
                        )
                    }) {
                        if (bitmapObstacle != null) {
                            drawImage(
                                image = bitmapObstacle.asImageBitmap(),
                                topLeft = Offset(
                                    (movingBoxes[6].centerX - size.width / 10 - width.value / 3) * 10 / 4,
                                    (yBox[6] - height.value / 3) * 10 / 4
                                )
                            )
                        }
                    }

                    drawCircle(
                        color = Color(253, 247, 82).copy(alpha = 0.5f),
                        radius = size.width / 18f,
                        center = Offset(movingBoxes[7].centerX, yBox[7])
                    )

                    withTransform({
                        scale(
                            0.4f,
                            0.4f,
                            pivot = Offset(size.width / 2, size.height / 2)
                        )
                    }) {
                        if (bitmapObstacle != null) {
                            drawImage(
                                image = bitmapObstacle.asImageBitmap(),
                                topLeft = Offset(
                                    (movingBoxes[7].centerX - size.width / 10 - width.value / 3) * 10 / 4,
                                    (yBox[7] - height.value / 3) * 10 / 4
                                )
                            )
                        }
                    }

                    drawCircle(
                        color = Color(253, 247, 82).copy(alpha = 0.5f),
                        radius = size.width / 18f,
                        center = Offset(movingBoxes[8].centerX, yBox[8])
                    )

                    withTransform({
                        scale(
                            0.4f,
                            0.4f,
                            pivot = Offset(size.width / 2, size.height / 2)
                        )
                    }) {
                        if (bitmapObstacle != null) {
                            drawImage(
                                image = bitmapObstacle.asImageBitmap(),
                                topLeft = Offset(
                                    (movingBoxes[8].centerX - size.width / 10 - width.value / 3) * 10 / 4,
                                    (yBox[8] - height.value / 3) * 10 / 4
                                )
                            )
                        }
                    }
                }
            }

            if (!chooseImageSource.value) {
                drawCircle(
                    Brush.radialGradient(
                        colors = shadowColors,
                        center = Offset(size.width / 2, size.height / 2),
                        radius = size.width / 15f * 1.1f * sizeDuringJump.value.pow(1.25f)
                    ),
                    radius = size.width / 15f * 1.1f * sizeDuringJump.value.pow(1.25f),
                    center = Offset(movingJerry.value.centerX + 2f, size.height - y + 2f)
                )

                drawCircle(
                    color = if (collisionCount.value == 1) colors else Color.Black,
                    radius = size.width / 15f * sizeDuringJump.value,
                    center = Offset(movingJerry.value.centerX, size.height - y)
                )
            } else {
                withTransform({
                    scale(
                        if (!(jerryJump.value || autoJump.value)) 0.2f else sizeDuringJump.value,
                        if (!(jerryJump.value || autoJump.value)) 0.2f else sizeDuringJump.value,
                        pivot = Offset(size.width / 2, size.height / 2)
                    )
                }) {
                    if (bitmapJerry != null) {
                        drawImage(
                            image = bitmapJerry.asImageBitmap(),
                            topLeft = Offset(
                                (movingJerry.value.centerX - width.value * 0.99f / 2) * 10 / 2,
                                (size.height - y + height.value / 3)
                            ),
                        )
                    }
                }
            }

            if (collisionCount.value >= 1 || viewTom.value) {
                if (!chooseImageSource.value) {
                    drawCircle(
                        color = if (!reverseTom.value) Color.Gray else Color.Gray.copy(alpha = fadeTom.value),
                        radius = size.width / 15f,
                        center = Offset(movingTom.value.centerX, movingTom.value.centerY)
                    )
                    drawCircle(
                        color = if (!reverseTom.value) Color.DarkGray else Color.DarkGray.copy(alpha = fadeTom.value),
                        radius = size.width / 12f,
                        center = Offset(movingTom.value.centerX, movingTom.value.centerY),
                        style = Stroke(width = 8f)
                    )
                } else {
                    withTransform({
                        scale(
                            0.6f,
                            0.6f,
                            pivot = Offset(size.width / 2, size.height / 2)
                        )
                    }) {
                        if (bitmapTom != null) {
                            drawImage(
                                image = bitmapTom.asImageBitmap(),
                                topLeft = Offset(
                                    (movingTom.value.centerX - width.value * 0.985f / 3) * 10 / 6,
                                    movingTom.value.centerY
                                )
                            )
                        }
                    }
                }
            }


            if (powerUpDisplay[0]) {

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = powerUpColors,
                        center = Offset(powerUp[0].centerX, powerUp[0].centerY),
                        radius = size.width / 18f
                    ),
                    radius = size.width / 12f,
                    center = Offset(powerUp[0].centerX, powerUp[0].centerY)
                )

                val path1 = Path()

                path1.moveTo(
                    x = powerUp[0].centerX + size.width / 24 * cos(0f),
                    y = powerUp[0].centerY + size.width / 48 * sin(0f)
                )

                for (i in 1 until 5 * 2) {
                    val radius = if (i % 2 == 0) size.width / 24 else size.width / 48
                    val pointAngle = i * 72 * Math.PI / 180f

                    path1.lineTo(
                        x = (powerUp[0].centerX + radius * cos(pointAngle)).toFloat(),
                        y = (powerUp[0].centerY + radius * sin(pointAngle)).toFloat()
                    )
                }

                path1.close()

                drawPath(
                    path = path1,
                    color = Color(253, 247, 82).copy(alpha = 0.5f),
                    style = Fill
                )

            }

            if (powerUpDisplay[1]) {

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = powerUpColors,
                        center = Offset(powerUp[1].centerX, powerUp[1].centerY),
                        radius = size.width / 18f
                    ),
                    radius = size.width / 12f,
                    center = Offset(powerUp[1].centerX, powerUp[1].centerY)
                )

                val path2 = Path()

                path2.moveTo(
                    x = powerUp[1].centerX + size.width / 24 * cos(0f),
                    y = powerUp[1].centerY + size.width / 48 * sin(0f)
                )

                for (i in 1 until 5 * 2) {
                    val radius = if (i % 2 == 0) size.width / 24 else size.width / 48
                    val pointAngle = i * 72 * Math.PI / 180f

                    path2.lineTo(
                        x = (powerUp[1].centerX + radius * cos(pointAngle)).toFloat(),
                        y = (powerUp[1].centerY + radius * sin(pointAngle)).toFloat()
                    )
                }

                path2.close()

                drawPath(
                    path = path2,
                    color = Color(253, 247, 82).copy(alpha = 0.5f),
                    style = Fill
                )

            }

            if (powerUpDisplay[2]) {

                drawCircle(
                    brush = Brush.radialGradient(
                        colors = powerUpColors,
                        center = Offset(powerUp[2].centerX, powerUp[2].centerY),
                        radius = size.width / 18f
                    ),
                    radius = size.width / 12f,
                    center = Offset(powerUp[2].centerX, powerUp[2].centerY)
                )

                val path3 = Path()

                path3.moveTo(
                    x = powerUp[2].centerX + size.width / 24 * cos(0f),
                    y = powerUp[2].centerY + size.width / 48 * sin(0f)
                )

                for (i in 1 until 5 * 2) {
                    val radius = if (i % 2 == 0) size.width / 24 else size.width / 48
                    val pointAngle = i * 72 * Math.PI / 180f

                    path3.lineTo(
                        x = (powerUp[2].centerX + radius * cos(pointAngle)).toFloat(),
                        y = (powerUp[2].centerY + radius * sin(pointAngle)).toFloat()
                    )
                }

                path3.close()

                drawPath(
                    path = path3,
                    color = Color(253, 247, 82).copy(alpha = 0.5f),
                    style = Fill
                )
            }

            drawLine(
                color = Color.White,
                start = Offset(size.width / 2, 0f),
                end = Offset(size.width / 2, size.height),
                strokeWidth = 1f
            )

            drawLine(
                color = Color.White,
                start = Offset(size.width / 6, 0f),
                end = Offset(size.width / 6, size.height),
                strokeWidth = 1f
            )

            drawLine(
                color = Color.White,
                start = Offset(size.width / 1.2f, 0f),
                end = Offset(size.width / 1.2f, size.height),
                strokeWidth = 1f
            )
        }
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Column {
                    Canvas(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.White.copy(alpha = 0.4f))
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        if (collisionCount.value < collisionCountLimit.value) {
                                            activatePowerUp2.value = false
                                            usePowerUp.value = 2
                                            powerUpInit2.value = 0
                                            if (!chooseRewardSource.value) {
                                                if (powerUp2Value.value == 1 && collisionCount.value >= 1) {
                                                    reverseTom.value = true
                                                } else if (powerUp2Value.value == 2) {
                                                    shatterBlocks.value = true
                                                } else if (powerUp2Value.value == 3) {
                                                    scoreSpeeding.value += 1
                                                }
                                            } else {
                                                if (powerUp2Value.value == 1) {
                                                    scoreSpeeding.value += 1
                                                } else if (powerUp2Value.value == 2) {
                                                    autoJump.value = true
                                                }
                                            }
                                            powerUp2Value.value = 0
                                            if (powerUpsCollected.value == 2 || powerUpsCollected.value == 1) {
                                                powerUpsCollected.value -= 1
                                            }
                                            circularTimer2.value = 0f
                                        }
                                    }
                                )
                            }
                    ) {
                        if (!activatePowerUp2.value) {
                            drawCircle(
                                color = Color.Black,
                                radius = size.width / 2,
                                center = Offset(size.width / 2, size.height / 2),
                                style = Stroke(width = 16f)
                            )

                            drawArc(
                                color = Color.Gray,
                                startAngle = -90f,
                                sweepAngle = circularTimer2.value,
                                useCenter = false,
                                style = Stroke(width = 16f)
                            )
                        } else {
                            drawCircle(
                                brush = Brush.radialGradient(
                                    colors = if ((powerUp1Value.value != 3 && chooseRewardSource.value) || !chooseRewardSource.value) powerUpColors else animatedPowerUpColors,
                                    radius = size.width / 2,
                                    center = Offset(size.width / 2, size.height / 2),
                                )
                            )

                            if (powerUp2Value.value == 1){
                                if (!chooseRewardSource.value) {
                                    drawCircle(
                                        color = Color.Gray,
                                        radius = size.width / 4,
                                        center = Offset(size.width / 2, size.height / 2),
                                        style = Fill
                                    )

                                    drawCircle(
                                        color = Color.DarkGray,
                                        radius = size.width / 3.6f,
                                        center = Offset(size.width / 2, size.height / 2),
                                        style = Stroke(width = 3f)
                                    )

                                    drawLine(
                                        color = Color(202, 60, 70),
                                        start = Offset(size.width / 5, size.height / 5),
                                        end = Offset(size.width * 4 / 5f, size.height * 4 / 5f),
                                        strokeWidth = 10f
                                    )

                                    drawLine(
                                        color = Color(202, 60, 70),
                                        start = Offset(size.width * 4 / 5, size.height / 5),
                                        end = Offset(size.width / 5f, size.height * 4 / 5f),
                                        strokeWidth = 10f
                                    )
                                } else {
                                    drawCircle(
                                        brush = Brush.radialGradient(
                                            colors = yellowColors,
                                            radius = size.width / 3,
                                            center = Offset(size.width / 2, size.height / 2)
                                        ),
                                        radius = size.width / 3,
                                        center = Offset(size.width / 2, size.height / 2)
                                    )

                                    drawLine(
                                        start = Offset(size.width / 2, size.height / 4),
                                        end = Offset(size.width / 2, size.height * 3 / 4),
                                        color = Color(202,60,70),
                                        strokeWidth = 10f
                                    )

                                    drawLine(
                                        start = Offset(size.width / 4, size.height / 2),
                                        end = Offset(size.width * 3 / 4, size.height / 2),
                                        color = Color(202,60,70),
                                        strokeWidth = 10f
                                    )
                                }
                            }
                            if (powerUp2Value.value == 2){
                                if (!chooseRewardSource.value) {
                                    drawRect(
                                        color = Color(128, 56, 42),
                                        topLeft = Offset(size.width / 4, size.height / 4),
                                        size = Size(size.width / 2, size.height / 2)
                                    )

                                    drawLine(
                                        color = Color(202, 60, 70),
                                        start = Offset(size.width / 5, size.height / 5),
                                        end = Offset(size.width * 4 / 5f, size.height * 4 / 5f),
                                        strokeWidth = 10f
                                    )

                                    drawLine(
                                        color = Color(202, 60, 70),
                                        start = Offset(size.width * 4 / 5, size.height / 5),
                                        end = Offset(size.width / 5f, size.height * 4 / 5f),
                                        strokeWidth = 10f
                                    )
                                } else {
                                    drawRect(
                                        color = Color(128, 56, 42),
                                        topLeft = Offset(size.width / 3, size.height / 2),
                                        size = Size(size.width / 3, size.height / 3)
                                    )

                                    drawLine(
                                        start = Offset(size.width / 2, size.height / 5),
                                        end = Offset(size.width / 2, size.height * 4 / 5f),
                                        color = Color(202,60,70),
                                        strokeWidth = 10f
                                    )

                                    drawLine(
                                        start = Offset(size.width / 2 + 5f, size.height / 5),
                                        end = Offset(size.width / 5, size.height / 3f),
                                        color = Color(202,60,70),
                                        strokeWidth = 10f
                                    )

                                    drawLine(
                                        start = Offset(size.width / 2, size.height / 5),
                                        end = Offset(size.width * 4 / 5, size.height / 3f),
                                        color = Color(202,60,70),
                                        strokeWidth = 10f
                                    )
                                }
                            }
                            if (powerUp2Value.value == 3){
                                if (!chooseRewardSource.value) {
                                    drawCircle(
                                        brush = Brush.radialGradient(
                                            colors = yellowColors,
                                            radius = size.width / 3,
                                            center = Offset(size.width / 2, size.height / 2)
                                        ),
                                        radius = size.width / 3,
                                        center = Offset(size.width / 2, size.height / 2)
                                    )

                                    drawLine(
                                        start = Offset(size.width / 2, size.height / 4),
                                        end = Offset(size.width / 2, size.height * 3 / 4),
                                        color = Color(202, 60, 70),
                                        strokeWidth = 10f
                                    )

                                    drawLine(
                                        start = Offset(size.width / 4, size.height / 2),
                                        end = Offset(size.width * 3 / 4, size.height / 2),
                                        color = Color(202, 60, 70),
                                        strokeWidth = 10f
                                    )
                                } else {
                                    drawCircle(
                                        color = Color.Gray,
                                        radius = size.width / 4,
                                        center = Offset(size.width / 2, size.height / 2),
                                        style = Fill
                                    )

                                    drawCircle(
                                        color = Color.DarkGray,
                                        radius = size.width / 3.6f,
                                        center = Offset(size.width / 2, size.height / 2),
                                        style = Stroke(width = 3f)
                                    )

                                    drawLine(
                                        start = Offset(size.width / 2, size.height / 5),
                                        end = Offset(size.width / 2, size.height * 4 / 5f),
                                        color = Color(202,60,70),
                                        strokeWidth = 10f
                                    )

                                    drawLine(
                                        start = Offset(size.width / 2 + 5f, size.height / 5),
                                        end = Offset(size.width / 5, size.height / 3f),
                                        color = Color(202,60,70),
                                        strokeWidth = 10f
                                    )

                                    drawLine(
                                        start = Offset(size.width / 2, size.height / 5),
                                        end = Offset(size.width * 4 / 5, size.height / 3f),
                                        color = Color(202,60,70),
                                        strokeWidth = 10f
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Canvas(
                        modifier = Modifier
                            .size(80.dp)
                            .clip(RoundedCornerShape(50))
                            .background(Color.White.copy(alpha = 0.4f))
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onTap = {
                                        if (collisionCount.value < collisionCountLimit.value) {
                                            activatePowerUp1.value = false
                                            usePowerUp.value = 1
                                            powerUpInit1.value = 0
                                            if (!chooseRewardSource.value) {
                                                if (powerUp1Value.value == 1 && collisionCount.value >= 1) {
                                                    reverseTom.value = true
                                                } else if (powerUp1Value.value == 2) {
                                                    shatterBlocks.value = true
                                                } else if (powerUp1Value.value == 3) {
                                                    scoreSpeeding.value += 1
                                                }
                                            } else {
                                                if (powerUp1Value.value == 1) {
                                                    scoreSpeeding.value += 1
                                                } else if (powerUp1Value.value == 2) {
                                                    autoJump.value = true
                                                }
                                            }
                                            powerUp1Value.value = 0
                                            if (powerUpsCollected.value == 1) {
                                                powerUpsCollected.value -= 1
                                            } else if (powerUpsCollected.value == 2) {
                                                powerUpsCollected.value -= 2
                                            }
                                            circularTimer1.value = 0f
                                        }
                                    }
                                )
                            }
                    ) {
                        if (!activatePowerUp1.value) {
                            drawCircle(
                                color = Color.Black,
                                radius = size.width / 2,
                                center = Offset(size.width / 2, size.height / 2),
                                style = Stroke(width = 16f)
                            )

                            drawArc(
                                color = Color.Gray,
                                startAngle = -90f,
                                sweepAngle = circularTimer1.value,
                                useCenter = false,
                                style = Stroke(width = 16f)
                            )
                        } else {
                            drawCircle(
                                brush = Brush.radialGradient(
                                    colors = if ((powerUp1Value.value != 3 && chooseRewardSource.value) || !chooseRewardSource.value) powerUpColors else animatedPowerUpColors,
                                    radius = size.width / 2,
                                    center = Offset(size.width / 2, size.height / 2),
                                )
                            )

                            if (powerUp1Value.value == 1) {
                                if (!chooseRewardSource.value) {
                                    drawCircle(
                                        color = Color.Gray,
                                        radius = size.width / 4,
                                        center = Offset(size.width / 2, size.height / 2),
                                        style = Fill
                                    )

                                    drawCircle(
                                        color = Color.DarkGray,
                                        radius = size.width / 3.6f,
                                        center = Offset(size.width / 2, size.height / 2),
                                        style = Stroke(width = 3f)
                                    )

                                    drawLine(
                                        color = Color(202, 60, 70),
                                        start = Offset(size.width / 5, size.height / 5),
                                        end = Offset(size.width * 4 / 5f, size.height * 4 / 5f),
                                        strokeWidth = 10f
                                    )

                                    drawLine(
                                        color = Color(202, 60, 70),
                                        start = Offset(size.width * 4 / 5, size.height / 5),
                                        end = Offset(size.width / 5f, size.height * 4 / 5f),
                                        strokeWidth = 10f
                                    )
                                } else {
                                    drawCircle(
                                        brush = Brush.radialGradient(
                                            colors = yellowColors,
                                            radius = size.width / 3,
                                            center = Offset(size.width / 2, size.height / 2)
                                        ),
                                        radius = size.width / 3,
                                        center = Offset(size.width / 2, size.height / 2)
                                    )

                                    drawLine(
                                        start = Offset(size.width / 2, size.height / 4),
                                        end = Offset(size.width / 2, size.height * 3 / 4),
                                        color = Color(202,60,70),
                                        strokeWidth = 10f
                                    )

                                    drawLine(
                                        start = Offset(size.width / 4, size.height / 2),
                                        end = Offset(size.width * 3 / 4, size.height / 2),
                                        color = Color(202,60,70),
                                        strokeWidth = 10f
                                    )
                                }
                            }
                            if (powerUp1Value.value == 2) {
                                if (!chooseRewardSource.value) {
                                    drawRect(
                                        color = Color(128, 56, 42),
                                        topLeft = Offset(size.width / 4, size.height / 4),
                                        size = Size(size.width / 2, size.height / 2)
                                    )

                                    drawLine(
                                        color = Color(202, 60, 70),
                                        start = Offset(size.width / 5, size.height / 5),
                                        end = Offset(size.width * 4 / 5f, size.height * 4 / 5f),
                                        strokeWidth = 10f
                                    )

                                    drawLine(
                                        color = Color(202, 60, 70),
                                        start = Offset(size.width * 4 / 5, size.height / 5),
                                        end = Offset(size.width / 5f, size.height * 4 / 5f),
                                        strokeWidth = 10f
                                    )
                                } else {
                                    drawRect(
                                        color = Color(128, 56, 42),
                                        topLeft = Offset(size.width / 3, size.height / 2),
                                        size = Size(size.width / 3, size.height / 3)
                                    )

                                    drawLine(
                                        start = Offset(size.width / 2, size.height / 5),
                                        end = Offset(size.width / 2, size.height * 4 / 5f),
                                        color = Color(202,60,70),
                                        strokeWidth = 10f
                                    )

                                    drawLine(
                                        start = Offset(size.width / 2 + 5f, size.height / 5),
                                        end = Offset(size.width / 5, size.height / 3f),
                                        color = Color(202,60,70),
                                        strokeWidth = 10f
                                    )

                                    drawLine(
                                        start = Offset(size.width / 2, size.height / 5),
                                        end = Offset(size.width * 4 / 5, size.height / 3f),
                                        color = Color(202,60,70),
                                        strokeWidth = 10f
                                    )
                                }
                            }
                            if (powerUp1Value.value == 3){
                                if (!chooseRewardSource.value) {
                                    drawCircle(
                                        brush = Brush.radialGradient(
                                            colors = yellowColors,
                                            radius = size.width / 3,
                                            center = Offset(size.width / 2, size.height / 2)
                                        ),
                                        radius = size.width / 3,
                                        center = Offset(size.width / 2, size.height / 2)
                                    )

                                    drawLine(
                                        start = Offset(size.width / 2, size.height / 4),
                                        end = Offset(size.width / 2, size.height * 3 / 4),
                                        color = Color(202, 60, 70),
                                        strokeWidth = 10f
                                    )

                                    drawLine(
                                        start = Offset(size.width / 4, size.height / 2),
                                        end = Offset(size.width * 3 / 4, size.height / 2),
                                        color = Color(202, 60, 70),
                                        strokeWidth = 10f
                                    )
                                } else {
                                    drawCircle(
                                        color = Color.Gray,
                                        radius = size.width / 4,
                                        center = Offset(size.width / 2, size.height / 2),
                                        style = Fill
                                    )

                                    drawCircle(
                                        color = Color.DarkGray,
                                        radius = size.width / 3.6f,
                                        center = Offset(size.width / 2, size.height / 2),
                                        style = Stroke(width = 3f)
                                    )

                                    drawLine(
                                        start = Offset(size.width / 2, size.height / 5),
                                        end = Offset(size.width / 2, size.height * 4 / 5f),
                                        color = Color(202,60,70),
                                        strokeWidth = 10f
                                    )

                                    drawLine(
                                        start = Offset(size.width / 2 + 5f, size.height / 5),
                                        end = Offset(size.width / 5, size.height / 3f),
                                        color = Color(202,60,70),
                                        strokeWidth = 10f
                                    )

                                    drawLine(
                                        start = Offset(size.width / 2, size.height / 5),
                                        end = Offset(size.width * 4 / 5, size.height / 3f),
                                        color = Color(202,60,70),
                                        strokeWidth = 10f
                                    )
                                }
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.width(12.dp))
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
    if (!chooseRewardSource.value) {
        if (activatePowerUp1.value && powerUpInit1.value == 0) {
            powerUp1Value.value = Random.nextInt(1, 4)
            powerUpInit1.value = 1
        }
        if (activatePowerUp2.value && powerUpInit2.value == 0) {
            powerUp2Value.value = Random.nextInt(1, 4)
            powerUpInit2.value = 1
        }
    } else {
        if (powerUpInit1.value == 0 && startTimer1.value && !setReward1.value){
            dataViewModel.fetchHitHindrance()
            setReward1.value = true
        }
        if (activatePowerUp1.value && powerUpInit1.value == 0) {
            powerUp1Value.value = viewState.value?.type!!
            powerUp1Amount.value = viewState.value?.amount!!
            powerUpInit1.value = 1
            setReward1.value = false
        }
        if (powerUpInit2.value == 0 && startTimer2.value && !setReward2.value){
            dataViewModel.fetchHitHindrance()
            setReward2.value = true
        }
        if (activatePowerUp2.value && powerUpInit2.value == 0) {
            powerUp2Value.value = viewState.value?.type!!
            powerUp2Amount.value = viewState.value?.amount!!
            powerUpInit2.value = 1
            setReward2.value = false
        }
    }
}

@Composable
fun LoadImageAsBitmap(url: String): Bitmap? {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    LaunchedEffect(url) {
        val loader = ImageLoader(context)
        val request = ImageRequest.Builder(context)
            .data(url)
            .allowHardware(false) // Disable hardware bitmaps
            .build()

        val result = loader.execute(request)
        if (result is SuccessResult) {
            bitmap = (result.drawable as BitmapDrawable).bitmap
        }
    }

    return bitmap
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
    if (!(collided1.value || collided2.value || collided3.value || collided4.value || collided5.value) && !shatterBlocks.value) {
        for (i in 0..8) {
            if (movingJerry.value.centerX < movingBoxes[i].centerX) {
                if (movingJerry.value.centerY <= movingBoxes[i].centerY + movingBoxes[i].height / 2 && movingJerry.value.centerY >= movingBoxes[i].centerY - movingBoxes[i].height / 2) {
                    if (-movingJerry.value.centerX + movingBoxes[i].centerX <= movingBoxes[i].width / 2 + movingJerry.value.width / 2) {
                        if (movingBoxes[i].centerX == width.value / 2) {
                            collisionCount.value += 1
                            sidewaysCollision.value = true
                            collided4.value = true
                        } else if (movingBoxes[i].centerX == width.value * 5 / 6) {
                            collisionCount.value += 1
                            sidewaysCollision.value = true
                            collided5.value = true
                        }
                    }
                }
            } else if (movingBoxes[i].centerX - movingJerry.value.centerX <= movingBoxes[i].width / 2 + movingJerry.value.width / 2 && movingBoxes[i].centerX - movingJerry.value.centerX >= 0) {
                if ((movingBoxes[i].centerY - movingBoxes[i].height / 2 <= movingJerry.value.centerY + movingJerry.value.height / 2) && (movingBoxes[i].centerY + movingBoxes[i].height / 2 >= movingJerry.value.centerY - movingJerry.value.height / 2)) {
                    collisionCount.value += 1
                    collided1.value = true
                }
            } else if (movingBoxes[i].centerX == width.value / 6 && movingJerry.value.centerX - movingJerry.value.width / 2 <= movingBoxes[i].centerX + movingBoxes[i].width / 2) {
                if (movingBoxes[i].centerY - movingBoxes[i].height / 2 <= movingJerry.value.centerY + movingJerry.value.height / 2 && movingBoxes[i].centerY + movingBoxes[i].height / 2 >= movingJerry.value.centerY - movingJerry.value.height / 2) {
                    collisionCount.value += 1
                    sidewaysCollision.value = true
                    collided2.value = true
                }
            } else if (movingBoxes[i].centerX == width.value / 2 && movingJerry.value.centerX - movingJerry.value.width / 2 <= movingBoxes[i].centerX + movingBoxes[i].width / 2 && movingJerry.value.centerX + movingJerry.value.width / 2 >= movingBoxes[i].centerX - movingBoxes[i].width / 2) {
                if (movingBoxes[i].centerY - movingBoxes[i].height / 2 <= movingJerry.value.centerY + movingJerry.value.height / 2 && movingBoxes[i].centerY + movingBoxes[i].height / 2 >= movingJerry.value.centerY - movingJerry.value.height / 2) {
                    collisionCount.value += 1
                    sidewaysCollision.value = true
                    collided3.value = true
                }
            }
        }
    }
}

fun powerUpCollection(){
    if (powerUpsCollected.value < 2) {
        for (i in 0..2) {
            if (movingJerry.value.centerX < powerUp[i].centerX) {
                if (movingJerry.value.centerY <= powerUp[i].centerY + powerUp[i].height / 2 && movingJerry.value.centerY >= powerUp[i].centerY - powerUp[i].height / 2) {
                    if (-movingJerry.value.centerX + powerUp[i].centerX <= powerUp[i].width / 2 + movingJerry.value.width / 2) {
                        if (powerUp[i].centerX == width.value / 2 && powerUpDisplay[i]) {
                            if (powerUpsCollected.value == 0 && powerUpInit1.value == 1){
                                powerUpsCollected.value += 2
                            } else {
                                powerUpsCollected.value += 1
                            }
                            powerUpDisplay[i] = false
                            makeDelay.value = true
                        } else if (powerUp[i].centerX == width.value * 5 / 6 && powerUpDisplay[i]) {
                            if (powerUpsCollected.value == 0 && powerUpInit1.value == 1){
                                powerUpsCollected.value += 2
                            } else {
                                powerUpsCollected.value += 1
                            }
                            powerUpDisplay[i] = false
                            makeDelay.value = true
                        }
                    }
                }
            } else if (powerUp[i].centerX - movingJerry.value.centerX <= powerUp[i].width / 2 + movingJerry.value.width / 2 && powerUp[i].centerX - movingJerry.value.centerX >= 0) {
                if ((powerUp[i].centerY - powerUp[i].height / 2 <= movingJerry.value.centerY + movingJerry.value.height / 2) && (powerUp[i].centerY + powerUp[i].height / 2 >= movingJerry.value.centerY - movingJerry.value.height / 2)) {
                    if (powerUpDisplay[i]) {
                        if (powerUpsCollected.value == 0 && powerUpInit1.value == 1){
                            powerUpsCollected.value += 2
                        } else {
                            powerUpsCollected.value += 1
                        }
                        powerUpDisplay[i] = false
                        makeDelay.value = true
                    }
                }
            } else if (powerUp[i].centerX == width.value / 6 && movingJerry.value.centerX - movingJerry.value.width / 2 <= powerUp[i].centerX + powerUp[i].width / 2) {
                if (powerUp[i].centerY - powerUp[i].height / 2 <= movingJerry.value.centerY + movingJerry.value.height / 2 && powerUp[i].centerY + powerUp[i].height / 2 >= movingJerry.value.centerY - movingJerry.value.height / 2) {
                    if (powerUpDisplay[i]) {
                        if (powerUpsCollected.value == 0 && powerUpInit1.value == 1){
                            powerUpsCollected.value += 2
                        } else {
                            powerUpsCollected.value += 1
                        }
                        powerUpDisplay[i] = false
                        makeDelay.value = true
                    }
                }
            } else if (powerUp[i].centerX == width.value / 2 && movingJerry.value.centerX - movingJerry.value.width / 2 <= powerUp[i].centerX + powerUp[i].width / 2 && movingJerry.value.centerX + movingJerry.value.width / 2 >= powerUp[i].centerX - powerUp[i].width / 2) {
                if (powerUp[i].centerY - powerUp[i].height / 2 <= movingJerry.value.centerY + movingJerry.value.height / 2 && powerUp[i].centerY + powerUp[i].height / 2 >= movingJerry.value.centerY - movingJerry.value.height / 2) {
                    if (powerUpDisplay[i]) {
                        if (powerUpsCollected.value == 0 && powerUpInit1.value == 1){
                            powerUpsCollected.value += 2
                        } else {
                            powerUpsCollected.value += 1
                        }
                        powerUpDisplay[i] = false
                        makeDelay.value = true
                    }
                }
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