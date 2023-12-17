package com.example.fics_compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fics_compose.BottomNavBar
import com.example.fics_compose.R
import com.example.fics_compose.ui.theme.lightGray

@Composable
fun GoTimeScreen(navController: NavController){
    GoTimeCard(navController)
}

@Composable
fun GoTimeCard(navController: NavController){
    Box(
        modifier = Modifier
            .background(color = lightGray)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(50.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "It's Go Time",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier
                    .padding(bottom = 35.dp)
            )
            Text(text = "You have 24 months to make as much money as you can.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 35.dp)
            )

            Button(
                onClick = { startSimulatorScreen(navController) },
                shape = RoundedCornerShape(7.dp),
                modifier = Modifier
                    .border(1.5.dp, Color(0xFF8A191D), RoundedCornerShape(30.dp))
                    .height(60.dp)
                    .width(150.dp),
                elevation = ButtonDefaults.buttonElevation(
                    pressedElevation = 6.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = Color(0xFF8A191D)
                )
            ) {
                Text(text = "INVEST")
            }

            Image(
                painter = painterResource(id = R.drawable.ssicon),
                contentDescription = "None"
            )
        }
    }
}


fun startSimulatorScreen(navController: NavController){
    navController.navigate(BottomNavBar.Simulator.route)
}