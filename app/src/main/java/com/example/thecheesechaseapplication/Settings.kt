package com.example.thecheesechaseapplication

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlin.math.roundToInt

@Composable
fun Settings(modifier: Modifier, navController: NavController, highScore: HighScoreManager){
    androidx.compose.foundation.Canvas(modifier = modifier.fillMaxSize()) {
        width.value = size.width
        height.value = size.height
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(230, 145, 58)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
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

                collisionCount.value = 3
                collided1.value = false
                collided2.value = false
                collided3.value = false
                collided4.value = false
                collided5.value = false
                sidewaysCollision.value = false
                tomCatches.value = false
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
                navController.navigate(Screen.Game.route)
            },
            modifier = modifier.height(64.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(80,80,80)
            ),
            shape = RoundedCornerShape(15)
        ){
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

                collisionCount.value = 3
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
                navController.navigate(Screen.Game.route)
            },
            modifier = modifier.height(64.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(80,80,80)
            ),
            shape = RoundedCornerShape(15)
        ){
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

                collisionCount.value = 3
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
                navController.navigate(Screen.Game.route)
            },
            modifier = modifier.height(64.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(80,80,80)
            ),
            shape = RoundedCornerShape(15)
        ){
            Text(
                text = "Hacker Mode++",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}