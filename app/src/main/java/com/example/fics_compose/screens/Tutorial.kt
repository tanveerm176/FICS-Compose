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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fics_compose.WelcomeNav

@Composable
fun TutorialScreen(navController: NavController) {
    TutorialCard(navController = navController, displayText = tutorialText.tutorialTextList)


}

@Composable
fun TutorialCard(
    navController: NavController,
    displayText:List<tutorialInfo>
){
    val maxSlides = displayText.size-1
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
            Button(onClick = { startGoTimeScreen(navController) }) {
                Text(text = "SKIP")
            }

            Button(onClick = {
                if (i==maxSlides)
                {
                    startGoTimeScreen(navController)

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

data class tutorialInfo(
    val title:String,
    val description:String,
//    val img:ImageVector
)

object tutorialText{
    val tutorialTextList = listOf(
        tutorialInfo(
            title = "How to Play",
            description = "Follow the tutorial to learn about the buttons and metrics to make your experience smooth"
        ),
        tutorialInfo(
            title = "Net Worth",
            description = "Net worth is the value of you!\n" +
                    "Net worth = your wallet + portfolio value"
        ),

        )

}

fun startGoTimeScreen(navController: NavController){
    navController.navigate(WelcomeNav.GoTime.route)
}


@Composable
@Preview
fun TutorialPreview() {
//    TutorialScreen()
}