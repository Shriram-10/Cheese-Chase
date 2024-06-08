package com.example.thecheesechaseapplication

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Settings(modifier: Modifier, navController: NavController){
    androidx.compose.foundation.Canvas(modifier = modifier.fillMaxWidth()) {
        movingJerry.value.centerX = size.width / 2
        movingJerry.value.centerY = size.height
        movingJerry.value.width = size.width / 15
        movingJerry.value.height = size.width / 15
        x.value = size.width/2
        width.value = size.width
        height.value = size.height
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
    }

    androidx.compose.foundation.Canvas(modifier = modifier.fillMaxHeight()) {
        movingJerry.value.centerY = size.height
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
            onClick = {},
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
            onClick = {},
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