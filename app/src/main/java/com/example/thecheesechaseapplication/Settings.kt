package com.example.thecheesechaseapplication

import android.graphics.Paint.Align
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun Settings(modifier: Modifier, navController: NavController){
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color(230,145,58)),
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