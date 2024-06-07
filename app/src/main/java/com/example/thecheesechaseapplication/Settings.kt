package com.example.thecheesechaseapplication

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
    androidx.compose.foundation.Canvas(modifier = Modifier.fillMaxWidth()) {
        x.value = size.width/2
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
                movingJerry.value.centerX = width.value / 2
                movingJerry.value.centerY = height.value
                movingJerry.value.width = width.value / 15
                movingJerry.value.height = width.value / 15
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