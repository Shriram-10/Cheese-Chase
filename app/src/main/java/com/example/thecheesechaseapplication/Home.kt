package com.example.thecheesechaseapplication

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.thecheesechaseapplication.ui.theme.fontFamily
import kotlinx.coroutines.delay

@Composable
fun Home(modifier: Modifier, navController: NavController){
    var animatedWidth by remember { mutableStateOf(120) }

    Column(
        modifier = modifier
            .background(Color.Gray),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "THE\nCHEESE\nCHASE",
            letterSpacing = 4.sp,
            fontFamily = fontFamily,
            lineHeight = 60.sp,
            textAlign = TextAlign.Center,
            color = Color(248, 225, 75),
            fontSize = 60.sp,
            fontWeight = FontWeight.ExtraBold
        )

        Image(
            painter = painterResource(id = R.drawable.tomandjerry),
            contentDescription = "BG",
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.1f),
        )

        Button(
            onClick = {
                navController.navigate(Screen.Settings.route)
            },
            modifier = Modifier
                .height(60.dp)
                .width(animatedWidth.dp),
            shape = RoundedCornerShape(20),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(248, 225, 75)
            ),
            border = BorderStroke(1.dp, Color(248, 225, 75)),
        ) {
            Text(
                text = "PLAY",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(40.dp))
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview(){
    Home(modifier = Modifier, navController = NavController(LocalContext.current))
}
