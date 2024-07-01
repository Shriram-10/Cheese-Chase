package com.example.thecheesechaseapplication

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlin.math.roundToInt

@Composable
fun WinnerPage(highScore: HighScoreManager, navController: NavController, dataViewModel: MainViewModel){

    dataViewModel.fetchObstacleLimit()
    val viewState by dataViewModel.state


    val infiniteTransition = rememberInfiniteTransition()

    val colors2 = infiniteTransition.animateColor(
        Color(255, 66, 66, 255),
        Color(255, 221, 50, 255),
        animationSpec = infiniteRepeatable(
            tween(200),
            repeatMode = RepeatMode.Reverse
        )
    ).value

    val colors3 = infiniteTransition.animateColor(
        Color(47, 136, 58, 255),
        Color(228, 134, 206, 255),
        animationSpec = infiniteRepeatable(
            tween(500),
            repeatMode = RepeatMode.Reverse
        )
    ).value

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.5f)),
        contentAlignment = Alignment.Center
    ){
        Button(
            onClick = { },
            modifier = Modifier
                .height(IntrinsicSize.Min)
                .width((width.value / 3).dp),
            shape = RoundedCornerShape(15),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(97,85,83),
                contentColor = Color.Black
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 10.dp
            )
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Spacer(modifier = Modifier.height(4.dp))

                Button(
                    onClick = {},
                    modifier = Modifier
                        .width((width.value / 3).dp)
                        .height(if ((score.value / 20).roundToInt() < 50 && level.value == 1 || (score.value / 20).roundToInt() < 100 && level.value == 2 || (score.value / 20).roundToInt() < 150 && level.value == 3) 70.dp else if ((score.value / 20).roundToInt() >= 50 && HighScore.value < 50 && level.value == 1 || (score.value / 20).roundToInt() in 100..149 && HighScore.value < 100 && level.value == 2) 70.dp else 50.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(25),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 10.dp
                    )
                ){
                    Text(
                        textAlign = TextAlign.Center,
                        lineHeight = 25.sp,
                        text = if ((score.value / 20).roundToInt() >= 50 && HighScore.value < 50 && level.value == 1 || (score.value / 20).roundToInt() in 100..149 && HighScore.value < 100 && level.value == 2) "YOU LEVELLED UP!"
                                else if ((score.value / 20).roundToInt() < 50 && level.value == 1 || (score.value / 20).roundToInt() < 100 && level.value == 2 || (score.value / 20).roundToInt() < 150 && level.value == 3) "BETTER LUCK NEXT TIME"
                                else "YOU WON!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if ((score.value / 20).roundToInt() >= 50 && HighScore.value < 50 && level.value == 1 || (score.value / 20).roundToInt() in 100..149 && HighScore.value < 100 && level.value == 2){
                    Spacer(modifier = Modifier.height(20.dp))

                    Text(
                        text = "Level ${level.value + 1} Target : " + if (level.value == 1) 100.toString() else 150.toString(),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(84, 224, 129, 255)
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "YOUR SCORE: ${(score.value / 20).roundToInt()}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(252, 187, 73, 255)
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "HIGH SCORE: " + if (HighScore.value > (score.value / 20).roundToInt()) HighScore.value.toString() else (score.value / 20).roundToInt().toString(),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(95,125,199)
                )

                if (HighScore.value < (score.value / 20).roundToInt()) {
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = "NEW HIGH SCORE!",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors3
                    )
                }

                if ((score.value / 20).roundToInt() >= 150){
                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
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

                            yBox[0] = height.value / 2
                            yBox[1] = height.value / 2
                            yBox[2] = height.value / 4
                            yBox[3] = 0f
                            yBox[4] = 0f
                            yBox[5] = - height.value / 4
                            yBox[6] = - height.value / 2
                            yBox[7] = - height.value / 2
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
                            if ((score.value / 20).roundToInt() >= 50 && HighScore.value < 50 && level.value == 1 || (score.value / 20).roundToInt() > 100 && HighScore.value < 100 && level.value == 2){
                                if ((score.value / 20).roundToInt() in 50..99){
                                    level.value = 2
                                } else if ((score.value / 20).roundToInt() >= 100){
                                    level.value = 3
                                }
                            }
                            if ((score.value / 20).roundToInt() >= HighScore.value) {
                                HighScore.value = (score.value / 20).roundToInt()
                                highScore.saveData("HS", HighScore.value.toString())
                            }
                            score.value = 0f
                            showWinnerPage.value = false

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
                            collisionCountLimit.value = viewState.value
                            navController.popBackStack(Screen.Settings.route, false)
                        },
                        modifier = Modifier.height(40.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = colors2
                        ),
                        shape = RoundedCornerShape(15)
                    ){
                        Text(
                            text = "CHANGE MODE",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.ExtraBold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
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

                        yBox[0] = height.value / 2
                        yBox[1] = height.value / 2
                        yBox[2] = height.value / 4
                        yBox[3] = 0f
                        yBox[4] = 0f
                        yBox[5] = - height.value / 4
                        yBox[6] = - height.value / 2
                        yBox[7] = - height.value / 2
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
                        if ((score.value / 20).roundToInt() >= 50 && HighScore.value < 50 && level.value == 1 || (score.value / 20).roundToInt() > 100 && HighScore.value < 100 && level.value == 2){
                            if ((score.value / 20).roundToInt() in 50..99){
                                level.value = 2
                            } else if ((score.value / 20).roundToInt() >= 100){
                                level.value = 3
                            }
                        }
                        if ((score.value / 20).roundToInt() >= HighScore.value) {
                            HighScore.value = (score.value / 20).roundToInt()
                            highScore.saveData("HS", HighScore.value.toString())
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
                        score.value = 0f
                        shatterBlocks.value = false
                        resetBoxes.value = 0
                        scoreSpeeding.value = 0
                        reverseTom.value = false
                        fadeTom.value = 1f
                        showWinnerPage.value = false
                        collisionCountLimit.value = viewState.value
                    },
                    modifier = Modifier
                        .height(50.dp)
                        .width((width.value / 3).dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(21, 121, 10, 255),
                        contentColor = Color.Black
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 10.dp
                    )
                ){
                    Text(
                        textAlign = TextAlign.Center,
                        text = "PLAY AGAIN",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }
        }
    }
}