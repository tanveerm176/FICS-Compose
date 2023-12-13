package com.example.fics_compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fics_compose.R
import com.example.fics_compose.WelcomeNav
import com.example.fics_compose.ui.theme.lightGray

@Composable
fun LetsInvestScreen(navController: NavController){
    LetsInvestCard(navController)
}

@Composable
fun LetsInvestCard(navController: NavController):Int{
    Column(
        modifier = Modifier
            .background(color = lightGray)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Let's Invest",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(top = 150.dp, bottom = 40.dp),
        )
        Text(
            text = "You got this!",
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.padding(bottom = 70.dp),
            fontWeight = FontWeight.Bold
        )
        Button(
            onClick = { startTutorialScreen(navController)},
            modifier = Modifier
                .border(2.dp, Color(0xFFDEB841), RoundedCornerShape(30.dp)),
            elevation = ButtonDefaults.buttonElevation(
                pressedElevation = 6.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent,
            )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Play", color = Color(0xFFDEB841), fontWeight = FontWeight.Bold)
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color(0xFFDEB841),
                )
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 90.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.letsinvest),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(Color.Transparent) // Adjust the background color as needed
                    .align(Alignment.BottomCenter)
            )
        }
    }
//    Column {
//        Text(text = "Let's Invest")
//        Text(text = "You got this!")
//
//        Button(onClick = { startTutorialScreen(navController)}) {
//            Text(text = "PLAY")
////            Icon(painter = Icons.Filled.ArrowForward, contentDescription = )
//        }
//    }
    return 1

}

fun startTutorialScreen(navController: NavController){
    navController.navigate(WelcomeNav.Tutorial.route)
}