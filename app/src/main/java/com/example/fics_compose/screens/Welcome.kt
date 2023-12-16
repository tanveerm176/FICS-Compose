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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.contentColorFor
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fics_compose.R
import com.example.fics_compose.WelcomeNav
import com.example.fics_compose.ui.theme.lightGray

@Composable
fun WelcomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .background(color = lightGray)
            .fillMaxHeight()
            .fillMaxWidth(),
//        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
//                .fillMaxHeight()
//                .fillMaxWidth()
//                .background(Color.LightGray),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painterResource(id = R.drawable.welcomelogo),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 40.dp)
                    .size(280.dp))
            Text(
                text = "Welcome!",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                modifier = Modifier
                    .padding(start=30.dp, top = 15.dp, bottom = 60.dp),
                text = "Let’s work on your fixed income investing future, together!",
                style = MaterialTheme.typography.bodyLarge
            )

            Button(
                onClick = { startIntroductionScreen(navController) },
                shape = RoundedCornerShape(7.dp),
                modifier = Modifier.height(60.dp).width(150.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 6.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDEB841),
                    contentColor = Color.White,
                )
            ) {
                Text(
                    text = "Get Started",
                    style = MaterialTheme.typography.bodyLarge
                )
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