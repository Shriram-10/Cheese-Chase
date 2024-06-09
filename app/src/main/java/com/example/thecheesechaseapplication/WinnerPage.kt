package com.example.thecheesechaseapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun WinnerPage(modifier: Modifier){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White.copy(alpha = 0.5f)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Box(
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .background(color = Color.Black),
            contentAlignment = Alignment.Center
        ){
            Button(
                onClick = {
                    movingJerry.value.centerX = width.value / 2
                    movingJerry.value.centerY = height.value
                    movingJerry.value.width = width.value / 15
                    movingJerry.value.height = width.value / 15

                    movingTom.value.centerX = width.value / 2
                    movingTom.value.centerY = height.value
                    movingTom.value.width = width.value / 15
                    movingTom.value.height = width.value / 15

                    x.value = width.value/2
                    movingJerry.value.centerY = height.value
                    movingBoxes[0] = Dimensions(
                        width.value / 5,
                        width.value / 5,
                        width.value/10,
                        width.value/10
                    )

                    movingBoxes[1] = Dimensions(
                        width.value / 5,
                        width.value / 5,
                        width.value / 1.2f,
                        width.value / 10
                    )

                    movingBoxes[2] = Dimensions(
                        width.value / 5,
                        width.value / 5,
                        width.value / 2,
                        height.value / 4 + width.value / 10
                    )

                    movingBoxes[3] = Dimensions(
                        width.value / 5,
                        width.value / 5,
                        width.value/10,
                        height.value/ 2 + width.value / 10
                    )

                    movingBoxes[4] = Dimensions(
                        width.value / 5,
                        width.value / 5,
                        width.value / 1.2f,
                        height.value/ 2 + width.value / 10
                    )

                    movingBoxes[5] = Dimensions(
                        width.value / 5,
                        width.value / 5,
                        width.value / 2,
                        - height.value / 4 + width.value / 10
                    )

                    movingBoxes[6] = Dimensions(
                        width.value / 5,
                        width.value / 5,
                        width.value/10,
                        - height.value / 2 + width.value/10
                    )

                    movingBoxes[7] = Dimensions(
                        width.value / 5,
                        width.value / 5,
                        width.value / 1.2f,
                        - height.value / 2 + width.value/10
                    )

                    movingBoxes[8] = Dimensions(
                        width.value / 5,
                        width.value / 5,
                        width.value / 2,
                        - 3 * height.value / 4 + width.value / 10
                    )
                    collisionCount.value = 0
                    collided1.value = false
                    collided2.value = false
                    collided3.value = false
                    tomStarts.value = false
                    collisionCount.value = 3
                },
                modifier = Modifier
                    .height(60.dp)
            ){
                Text(
                    text = "Play Again",
                    color = Color.White
                )
            }
        }
    }
}