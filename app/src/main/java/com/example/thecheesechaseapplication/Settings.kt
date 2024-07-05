package com.example.thecheesechaseapplication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Info
import androidx.compose.material.icons.twotone.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.compose.rememberAsyncImagePainter
import kotlin.math.roundToInt

@Composable
fun Settings(modifier: Modifier, navController: NavController, highScore: HighScoreManager, dataViewModel : MainViewModel){
    androidx.compose.foundation.Canvas(modifier = modifier.fillMaxSize()) {
        width.value = size.width
        height.value = size.height
    }

    if (!showManeuverOptions.value) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(230, 145, 58)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            val viewState by dataViewModel.state

            if (chooseCollisionSource.value) {
                when {
                    (viewState.loading) -> {
                        displayText.value = "loading"
                    }

                    (viewState.error != null) -> {
                        displayText.value = "Error"
                    }

                    else -> {
                        displayText.value = "loaded"
                        collisionCountLimit.value = viewState.value
                    }
                }
            }

            Button(
                onClick = {
                    mode.value = 1
                    acceleration.value = 0f
                    movingJerry.value.centerX = width.value / 2
                    movingJerry.value.centerY = height.value
                    movingJerry.value.width = width.value / 7.5f
                    movingJerry.value.height = width.value / 7.5f

                    movingTom.value.centerX = width.value / 2
                    movingTom.value.centerY = height.value
                    movingTom.value.width = width.value / 7.5f
                    movingTom.value.height = width.value / 7.5f

                    x.value = width.value / 2
                    jerryLocate.value = 0
                    tomLocate.value = 0
                    moveTomLeft.value = false
                    moveTomRight.value = false

                    movingBoxes = mutableStateListOf(
                        Dimensions(
                            width.value / 5,
                            width.value / 5,
                            width.value / 6,
                            height.value / 2 + width.value / 10
                        ),

                        Dimensions(
                            width.value / 5,
                            width.value / 5,
                            width.value / 1.2f,
                            height.value / 2 + width.value / 10
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
                            width.value / 10
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
                            -height.value / 4 + width.value / 10
                        ),

                        Dimensions(
                            width.value / 5,
                            width.value / 5,
                            width.value / 6,
                            -height.value / 2 + width.value / 10
                        ),

                        Dimensions(
                            width.value / 5,
                            width.value / 5,
                            width.value / 1.2f,
                            -height.value / 2 + width.value / 10
                        ),

                        Dimensions(
                            width.value / 5,
                            width.value / 5,
                            width.value / 2,
                            -3 * height.value / 4 + width.value / 10
                        )
                    )

                    yBox[0] = height.value / 2
                    yBox[1] = height.value / 2
                    yBox[2] = height.value / 4
                    yBox[3] = 0f
                    yBox[4] = 0f
                    yBox[5] = -height.value / 4
                    yBox[6] = -height.value / 2
                    yBox[7] = -height.value / 2
                    yBox[8] = height.value * 3 / 4
                    collisionCount.value = 0
                    resetObstacles.value = true
                    if (!chooseImageSource.value){
                        sizeDuringJump.value = 1f
                    } else {
                        sizeDuringJump.value = 0.2f
                    }
                    collided1.value = false
                    collided2.value = false
                    collided3.value = false
                    collided4.value = false
                    collided5.value = false
                    sidewaysCollision.value = false
                    tomCatches.value = false
                    reset.value = true
                    if ((score.value / 20).roundToInt() >= 50 && HighScore.value < 50 && level.value == 1 || (score.value / 20).roundToInt() > 100 && HighScore.value < 100 && level.value == 2) {
                        if ((score.value / 20).roundToInt() in 50..99) {
                            level.value = 2
                        } else if ((score.value / 20).roundToInt() >= 100) {
                            level.value = 3
                        }
                    }
                    if ((score.value / 20).roundToInt() >= HighScore.value) {
                        HighScore.value = (score.value / 20).roundToInt()
                        highScore.saveData("HS", HighScore.value.toString())
                    }
                    score.value = 0f
                    showWinnerPage.value = false
                    if ((displayText.value == "loaded" && chooseCollisionSource.value) || !chooseCollisionSource.value) {
                        navController.navigate(Screen.Game.route)
                    } else {
                        navController.navigate(Screen.Load.route)
                    }
                },
                modifier = modifier.height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(80, 80, 80)
                ),
                shape = RoundedCornerShape(15)
            ) {
                Text(
                    text = " Normal Mode ",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = {
                    mode.value = 2
                    acceleration.value = 0.02f
                    movingJerry.value.centerX = width.value / 2
                    movingJerry.value.centerY = height.value
                    movingJerry.value.width = width.value / 7.5f
                    movingJerry.value.height = width.value / 7.5f

                    movingTom.value.centerX = width.value / 2
                    movingTom.value.centerY = height.value
                    movingTom.value.width = width.value / 7.5f
                    movingTom.value.height = width.value / 7.5f

                    x.value = width.value / 2
                    jerryLocate.value = 0
                    tomLocate.value = 0
                    moveTomLeft.value = false
                    moveTomRight.value = false

                    movingBoxes = mutableStateListOf(
                        Dimensions(
                            width.value / 5,
                            width.value / 5,
                            width.value / 6,
                            height.value / 2 + width.value / 10
                        ),

                        Dimensions(
                            width.value / 5,
                            width.value / 5,
                            width.value / 1.2f,
                            height.value / 2 + width.value / 10
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
                            width.value / 10
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
                            -height.value / 4 + width.value / 10
                        ),

                        Dimensions(
                            width.value / 5,
                            width.value / 5,
                            width.value / 6,
                            -height.value / 2 + width.value / 10
                        ),

                        Dimensions(
                            width.value / 5,
                            width.value / 5,
                            width.value / 1.2f,
                            -height.value / 2 + width.value / 10
                        ),

                        Dimensions(
                            width.value / 5,
                            width.value / 5,
                            width.value / 2,
                            -3 * height.value / 4 + width.value / 10
                        )
                    )

                    powerUp[0] = Dimensions(
                        width.value / 12,
                        width.value / 12,
                        width.value / 6,
                        height.value + width.value * 6 / 5
                    )

                    powerUp[1] = Dimensions(
                        width.value / 12,
                        width.value / 12,
                        width.value / 2,
                        height.value + width.value * 6 / 5
                    )

                    powerUp[2] = Dimensions(
                        width.value / 12,
                        width.value / 12,
                        width.value * 5 / 6,
                        height.value + width.value * 6 / 5
                    )

                    yBox[0] = height.value / 2
                    yBox[1] = height.value / 2
                    yBox[2] = height.value / 4
                    yBox[3] = 0f
                    yBox[4] = 0f
                    yBox[5] = -height.value / 4
                    yBox[6] = -height.value / 2
                    yBox[7] = -height.value / 2
                    yBox[8] = height.value * 3 / 4
                    collisionCount.value = 0
                    resetObstacles.value = true

                    collided1.value = false
                    collided2.value = false
                    collided3.value = false
                    collided4.value = false
                    collided5.value = false
                    sidewaysCollision.value = false
                    tomCatches.value = false
                    time.value = 0f
                    reset.value = true
                    if ((score.value / 20).roundToInt() >= 50 && HighScore.value < 50 && level.value == 1 || (score.value / 20).roundToInt() > 100 && HighScore.value < 100 && level.value == 2) {
                        if ((score.value / 20).roundToInt() in 50..99) {
                            level.value = 2
                        } else if ((score.value / 20).roundToInt() >= 100) {
                            level.value = 3
                        }
                    }
                    if ((score.value / 20).roundToInt() >= HighScore.value) {
                        HighScore.value = (score.value / 20).roundToInt()
                        highScore.saveData("HS", HighScore.value.toString())
                    }
                    score.value = 0f
                    showWinnerPage.value = false
                    if (!chooseImageSource.value){
                        sizeDuringJump.value = 1f
                    } else {
                        sizeDuringJump.value = 0.2f
                    }
                    powerUpDisplay[0] = true
                    powerUpDisplay[1] = true
                    powerUpDisplay[2] = true
                    makeDelay.value = false
                    powerUpsCollected.value = 0
                    activatePowerUp1.value = false
                    activatePowerUp2.value = false
                    circularTimer1.value = 0f
                    circularTimer2.value = 0f
                    startTimer1.value = false
                    startTimer2.value = false
                    powerUp1Value.value = 0
                    powerUp2Value.value = 0
                    powerUpInit1.value = 0
                    powerUpInit2.value = 0
                    shatterBlocks.value = false
                    resetBoxes.value = 0
                    scoreSpeeding.value = 0
                    reverseTom.value = false
                    fadeTom.value = 1f
                    viewTom.value = false
                    tomClosingIn.value = false
                    currentTomLocation.value = 0f
                    usePowerUp.value = 0
                    autoJump.value = false
                    powerUp1Amount.value = 0
                    powerUp2Amount.value = 0
                    if ((displayText.value == "loaded" && chooseCollisionSource.value) || !chooseCollisionSource.value) {
                        navController.navigate(Screen.Game.route)
                    } else {
                        navController.navigate(Screen.Load.route)
                    }
                },
                modifier = modifier.height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(80, 80, 80)
                ),
                shape = RoundedCornerShape(15)
            ) {
                Text(
                    text = " Hacker Mode ",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = {
                    mode.value = 3
                    acceleration.value = 0.02f
                    movingJerry.value.centerX = width.value / 2
                    movingJerry.value.centerY = height.value
                    movingJerry.value.width = width.value / 7.5f
                    movingJerry.value.height = width.value / 7.5f

                    movingTom.value.centerX = width.value / 2
                    movingTom.value.centerY = height.value
                    movingTom.value.width = width.value / 7.5f
                    movingTom.value.height = width.value / 7.5f

                    x.value = width.value / 2
                    jerryLocate.value = 0
                    tomLocate.value = 0
                    moveTomLeft.value = false
                    moveTomRight.value = false

                    movingBoxes = mutableStateListOf(
                        Dimensions(
                            width.value / 5,
                            width.value / 5,
                            width.value / 6,
                            height.value / 2 + width.value / 10
                        ),

                        Dimensions(
                            width.value / 5,
                            width.value / 5,
                            width.value / 1.2f,
                            height.value / 2 + width.value / 10
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
                            width.value / 10
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
                            -height.value / 4 + width.value / 10
                        ),

                        Dimensions(
                            width.value / 5,
                            width.value / 5,
                            width.value / 6,
                            -height.value / 2 + width.value / 10
                        ),

                        Dimensions(
                            width.value / 5,
                            width.value / 5,
                            width.value / 1.2f,
                            -height.value / 2 + width.value / 10
                        ),

                        Dimensions(
                            width.value / 5,
                            width.value / 5,
                            width.value / 2,
                            -3 * height.value / 4 + width.value / 10
                        )
                    )

                    powerUp[0] = Dimensions(
                        width.value / 12,
                        width.value / 12,
                        width.value / 6,
                        height.value + width.value * 6 / 5
                    )

                    powerUp[1] = Dimensions(
                        width.value / 12,
                        width.value / 12,
                        width.value / 2,
                        height.value + width.value * 6 / 5
                    )

                    powerUp[2] = Dimensions(
                        width.value / 12,
                        width.value / 12,
                        width.value * 5 / 6,
                        height.value + width.value * 6 / 5
                    )

                    yBox[0] = height.value / 2
                    yBox[1] = height.value / 2
                    yBox[2] = height.value / 4
                    yBox[3] = 0f
                    yBox[4] = 0f
                    yBox[5] = -height.value / 4
                    yBox[6] = -height.value / 2
                    yBox[7] = -height.value / 2
                    yBox[8] = height.value * 3 / 4
                    collisionCount.value = 0
                    resetObstacles.value = true

                    collided1.value = false
                    collided2.value = false
                    collided3.value = false
                    collided4.value = false
                    collided5.value = false
                    sidewaysCollision.value = false
                    tomCatches.value = false
                    time.value = 0f
                    reset.value = true
                    if ((score.value / 20).roundToInt() >= 50 && HighScore.value < 50 && level.value == 1 || (score.value / 20).roundToInt() > 100 && HighScore.value < 100 && level.value == 2) {
                        if ((score.value / 20).roundToInt() in 50..99) {
                            level.value = 2
                        } else if ((score.value / 20).roundToInt() >= 100) {
                            level.value = 3
                        }
                    }
                    if ((score.value / 20).roundToInt() >= HighScore.value) {
                        HighScore.value = (score.value / 20).roundToInt()
                        highScore.saveData("HS", HighScore.value.toString())
                    }
                    score.value = 0f
                    showWinnerPage.value = false
                    if (!chooseImageSource.value){
                        sizeDuringJump.value = 1f
                    } else {
                        sizeDuringJump.value = 0.2f
                    }
                    powerUpDisplay[0] = true
                    powerUpDisplay[1] = true
                    powerUpDisplay[2] = true
                    makeDelay.value = false
                    powerUpsCollected.value = 0
                    activatePowerUp1.value = false
                    activatePowerUp2.value = false
                    circularTimer1.value = 0f
                    circularTimer2.value = 0f
                    startTimer1.value = false
                    startTimer2.value = false
                    powerUp1Value.value = 0
                    powerUp2Value.value = 0
                    powerUpInit1.value = 0
                    powerUpInit2.value = 0
                    shatterBlocks.value = false
                    resetBoxes.value = 0
                    scoreSpeeding.value = 0
                    reverseTom.value = false
                    fadeTom.value = 1f
                    viewTom.value = false
                    tomClosingIn.value = false
                    currentTomLocation.value = 0f
                    usePowerUp.value = 0
                    autoJump.value = false
                    powerUp1Amount.value = 0
                    powerUp2Amount.value = 0
                    if ((displayText.value == "loaded" && chooseCollisionSource.value) || !chooseCollisionSource.value) {
                        navController.navigate(Screen.Game.route)
                    } else {
                        navController.navigate(Screen.Load.route)
                    }
                },
                modifier = modifier.height(64.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(80, 80, 80)
                ),
                shape = RoundedCornerShape(15)
            ) {
                Text(
                    text = "Hacker Mode++",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            AnimatedVisibility(
                visible = !showManeuverOptions.value,
                exit = fadeOut()
            ) {
                Button(
                    onClick = {
                        showManeuverOptions.value = true
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    )
                ) {
                    Icon(
                        Icons.TwoTone.Settings,
                        contentDescription = "Settings",
                        tint = Color.Cyan,
                        modifier = Modifier
                            .height(64.dp)
                            .scale(1.8f)
                    )
                }
            }
        }
    }

    AnimatedVisibility(
        visible = showManeuverOptions.value,
        enter = slideInVertically { it } + fadeIn(),
        exit = slideOutVertically { it } + fadeOut()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(230, 145, 58)),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(25))
                    .background(Color(19, 93, 113, 255).copy(0.5f))
            ) {
                Text(
                    text = "Maneuver Options",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "GyroScope Maneuver",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Box {
                        Button(
                            onClick = {
                                showNote.value = !showNote.value
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.Transparent
                            ),
                            shape = RoundedCornerShape(50),
                            modifier = Modifier
                                .height(32.dp)
                                .width(32.dp)
                        ) {

                        }
                        Icon(
                            Icons.TwoTone.Info,
                            contentDescription = null,
                            tint = Color.Cyan,
                            modifier = Modifier
                                .height(32.dp)
                                .width(32.dp)
                                .scale(0.5f)
                        )
                    }

                    Spacer(modifier = Modifier.width((width.value / 15).dp))

                    Switch(
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                            .scale(1.8f),
                        checked = chooseGyro.value,
                        onCheckedChange = {
                            chooseGyro.value = !chooseGyro.value
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(126, 219, 225),
                            checkedTrackColor = Color(62, 83, 86),
                            uncheckedThumbColor = Color(49, 119, 120),
                            uncheckedTrackColor = Color(215, 234, 238),
                            disabledCheckedThumbColor = Color.DarkGray,
                            disabledCheckedTrackColor = Color.Black,
                            disabledUncheckedThumbColor = Color.Yellow,
                            disabledUncheckedTrackColor = Color.LightGray
                        ),
                        thumbContent = {}
                    )
                }

                AnimatedVisibility(
                    visible = showNote.value
                ) {
                    Text(
                        text = "Note : Gyro is available in HackerMode++ only.",
                        fontSize = 16.sp,
                        color = Color.LightGray,
                        fontWeight = FontWeight.Light
                    )
                }

                androidx.compose.foundation.Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                ) {
                    drawLine(
                        start = Offset(size.width / 20, size.height / 2),
                        end = Offset(size.width * 19 / 20, size.height / 2),
                        color = Color.White,
                        strokeWidth = 4f
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Generate Random Obstacle course",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width((width.value / 15).dp))

                    Switch(
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                            .scale(1.8f),
                        checked = chooseRandomSource.value,
                        onCheckedChange = {
                            chooseRandomSource.value = !chooseRandomSource.value
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(126, 219, 225),
                            checkedTrackColor = Color(62, 83, 86),
                            uncheckedThumbColor = Color(49, 119, 120),
                            uncheckedTrackColor = Color(215, 234, 238),
                            disabledCheckedThumbColor = Color.DarkGray,
                            disabledCheckedTrackColor = Color.Black,
                            disabledUncheckedThumbColor = Color.Yellow,
                            disabledUncheckedTrackColor = Color.LightGray
                        ),
                        thumbContent = {}
                    )
                }

                androidx.compose.foundation.Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                ) {
                    drawLine(
                        start = Offset(size.width / 20, size.height / 2),
                        end = Offset(size.width * 19 / 20, size.height / 2),
                        color = Color.White,
                        strokeWidth = 4f
                    )
                }

                Text(
                    text = "Load and Play",
                    fontSize = 24.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Get collision limit from the API",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width((width.value / 15).dp))

                    Switch(
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                            .scale(1.8f),
                        checked = chooseCollisionSource.value,
                        onCheckedChange = {
                            chooseCollisionSource.value = !chooseCollisionSource.value
                            if (!chooseCollisionSource.value) {
                                collisionCountLimit.value = 2
                            }
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(126, 219, 225),
                            checkedTrackColor = Color(62, 83, 86),
                            uncheckedThumbColor = Color(49, 119, 120),
                            uncheckedTrackColor = Color(215, 234, 238),
                            disabledCheckedThumbColor = Color.DarkGray,
                            disabledCheckedTrackColor = Color.Black,
                            disabledUncheckedThumbColor = Color.Yellow,
                            disabledUncheckedTrackColor = Color.LightGray
                        ),
                        thumbContent = {}
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Load Images from the API",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width((width.value / 15).dp))

                    Switch(
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                            .scale(1.8f),
                        checked = chooseImageSource.value,
                        onCheckedChange = {
                            chooseImageSource.value = !chooseImageSource.value
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(126, 219, 225),
                            checkedTrackColor = Color(62, 83, 86),
                            uncheckedThumbColor = Color(49, 119, 120),
                            uncheckedTrackColor = Color(215, 234, 238),
                            disabledCheckedThumbColor = Color.DarkGray,
                            disabledCheckedTrackColor = Color.Black,
                            disabledUncheckedThumbColor = Color.Yellow,
                            disabledUncheckedTrackColor = Color.LightGray
                        ),
                        thumbContent = {}
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Load Rewards from the API",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width((width.value / 15).dp))

                    Switch(
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                            .scale(1.8f),
                        checked = chooseRewardSource.value,
                        onCheckedChange = {
                            chooseRewardSource.value = !chooseRewardSource.value
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(126, 219, 225),
                            checkedTrackColor = Color(62, 83, 86),
                            uncheckedThumbColor = Color(49, 119, 120),
                            uncheckedTrackColor = Color(215, 234, 238),
                            disabledCheckedThumbColor = Color.DarkGray,
                            disabledCheckedTrackColor = Color.Black,
                            disabledUncheckedThumbColor = Color.Yellow,
                            disabledUncheckedTrackColor = Color.LightGray
                        ),
                        thumbContent = {}
                    )
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "Load Obstacle course from the API",
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.width((width.value / 15).dp))

                    Switch(
                        modifier = Modifier
                            .height(80.dp)
                            .width(80.dp)
                            .scale(1.8f),
                        checked = chooseObstaclesSource.value,
                        onCheckedChange = {
                            chooseObstaclesSource.value = !chooseObstaclesSource.value
                        },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color(126, 219, 225),
                            checkedTrackColor = Color(62, 83, 86),
                            uncheckedThumbColor = Color(49, 119, 120),
                            uncheckedTrackColor = Color(215, 234, 238),
                            disabledCheckedThumbColor = Color.DarkGray,
                            disabledCheckedTrackColor = Color.Black,
                            disabledUncheckedThumbColor = Color.Yellow,
                            disabledUncheckedTrackColor = Color.LightGray
                        ),
                        thumbContent = {}
                    )
                }

                androidx.compose.foundation.Canvas(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                ) {
                    drawLine(
                        start = Offset(size.width / 20, size.height / 2),
                        end = Offset(size.width * 19 / 20, size.height / 2),
                        color = Color.White,
                        strokeWidth = 4f
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(
                    onClick = {
                        showNote.value = false
                        showManeuverOptions.value = false
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White
                    ),
                    modifier = Modifier.height(50.dp),
                    shape = RoundedCornerShape(20)
                ) {
                    Text(
                        text = "OK",
                        fontWeight = FontWeight.ExtraBold,
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}