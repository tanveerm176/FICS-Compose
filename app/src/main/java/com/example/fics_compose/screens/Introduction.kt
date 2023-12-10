package com.example.fics_compose.screens

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
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fics_compose.WelcomeNav

@Composable
fun IntroductionScreen(navController: NavController) {
    IntroCard(navController = navController, displayText = introText.introTextList)

}

@Composable
fun IntroCard(
    navController: NavController,
    displayText:List<introInfo>,
){
    var i by remember {
        mutableIntStateOf(0)
    }
    var currentText by remember {
        mutableStateOf(displayText[i])
    }

    Column {
        Text(text = currentText.title)
        Text(text = currentText.description)

        Row {
            Button(onClick = { startLetsInvestScreen(navController) }) {
                Text(text = "SKIP")
            }

            Button(onClick = {
                if (i==1)
                {
                    startLetsInvestScreen(navController)

                }
                else{
                    i += 1
                    currentText = displayText[i]
                }

            })
            { Text(text = "NEXT") }


        }


    }

}



data class introInfo(
    val title:String,
    val description:String,
//    val img:ImageVector
)

object introText{
    val introTextList = listOf(
        introInfo(
            title = "What is FICS?",
            description = "Welcome to the new way for college students to learn about the biggest investment market in the world: the Fixed Income market."
        ),
        introInfo(
            title = "Why Now?",
            description = "With the global fixed income market reaching almost $130 trillion, there is more opportunity than ever for you to generate income from investing."
        ),

    )

}







fun startLetsInvestScreen(navController: NavController){
    navController.navigate(WelcomeNav.LetsInvest.route)
}



@Composable
@Preview
fun IntroPreview() {
//    IntroductionScreen()
}