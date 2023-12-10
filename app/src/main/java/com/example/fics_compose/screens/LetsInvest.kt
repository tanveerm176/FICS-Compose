package com.example.fics_compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.fics_compose.WelcomeNav

@Composable
fun LetsInvestScreen(navController: NavController){
    Column {
        Text(text = "Let's Invest")
        Text(text = "You got this!")

        Button(onClick = { startTutorialScreen(navController)}) {
            Text(text = "PLAY")
//            Icon(painter = Icons.Filled.ArrowForward, contentDescription = )
        }
    }

}

fun startTutorialScreen(navController: NavController){
    navController.navigate(WelcomeNav.Tutorial.route)
}