package com.example.fics_compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.navigation.NavController
import com.example.fics_compose.WelcomeNav

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column {
//            Image(painter = , contentDescription = )
            Text(text = "Welcome")
            Text(text = "Let’s work on your fixed income investing future, together")
            
            Button(onClick = {
                startIntroductionScreen(navController)
            }) {
                Text(text = "Get Started")
            }

        }
    }
}


fun startIntroductionScreen(navController: NavController){
    navController.navigate(WelcomeNav.Introduction.route)

}
@Composable
@Preview
fun WelcomePreview() {
//    WelcomeScreen()
}