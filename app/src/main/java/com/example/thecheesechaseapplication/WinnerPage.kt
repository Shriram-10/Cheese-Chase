package com.example.thecheesechaseapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun WinnerPage(modifier: Modifier){
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.5f)),
        contentAlignment = Alignment.TopCenter
    ){
        /*Button(
            onClick = { *//*TODO*//* },
            modifier = Modifier
                .height((height.value/5).dp)
                .width((width.value/3).dp),
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Gray,
                contentColor = Color.Black
            )
        ){*/
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

                    x.value = width.value/2
                    jerryLocate.value = 0
                    tomLocate.value = 0
                    moveTomLeft.value = false
                    moveTomRight.value = false

                    movingBoxes[0] = Dimensions(
                        width.value / 5,
                        width.value / 5,
                        width.value / 6,
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
                        width.value / 6,
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
                        width.value / 6,
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

                    collisionCount.value = 3
                    collided1.value = false
                    collided2.value = false
                    collided3.value = false
                    collided4.value = false
                    collided5.value = false
                    sidewaysCollision.value = false
                    reset.value = true
                },
                modifier = Modifier
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color.Black
                )
            ){
                Text(
                    text = "Play Again",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        /*}*/
    }
}