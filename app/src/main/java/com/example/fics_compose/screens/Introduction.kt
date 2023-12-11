package com.example.fics_compose.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fics_compose.R
import com.example.fics_compose.WelcomeNav
import com.example.fics_compose.ui.theme.lightGray
import com.example.fics_compose.ui.theme.yellow

@Composable
fun IntroductionScreen(navController: NavController) {
    IntroCard(navController = navController, displayText = introText.introTextList)

}

@Composable
fun IntroCard(
    navController: NavController,
    displayText:List<introInfo>,
){
    val maxSlides = displayText.size-1
    var i by remember {
        mutableIntStateOf(0)
    }
    var currentPage by remember { mutableStateOf(0) }
    var currentText by remember {
        mutableStateOf(displayText[i])
    }
    Box(
        modifier = Modifier
            .background(color = lightGray)
            .fillMaxHeight()
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(
                modifier = Modifier
                    .padding(top=10.dp, bottom = 5.dp),
                text = currentText.title,
                style = MaterialTheme.typography.titleMedium
            )
            Image(
                painter = painterResource(id = currentText.img),
                contentDescription = null,
                modifier = Modifier
                    .size(250.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

            Box(
                modifier = Modifier
                    .background(color = yellow, shape = MaterialTheme.shapes.medium)
                    .padding(20.dp)
            ) {
                Text(
                    text = currentText.description,
                    modifier = Modifier.padding(start = 5.dp, top = 15.dp, bottom = 15.dp)
                )
            }

// Page Indicator
            Row(
                modifier = Modifier
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Previous Page Button
                Box(
                    modifier = Modifier
                        .clickable {
                            if (currentPage > 0) {
                                currentPage -= 1
                                currentText = displayText[currentPage]
                            }
                        }
                        .background(
                            color = Color(0xFFDEB841),
                            shape = RectangleShape // Use RectangleShape for square buttons
                        )
                        .border(1.dp, Color.White, RectangleShape) // Add white border
                        .size(50.dp, 50.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, // Use built-in icon for previous
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // Page Number Buttons
                for (page in 0 until maxSlides + 1) {
                    Box(
                        modifier = Modifier
                            .clickable {
                                currentPage = page
                                currentText = displayText[currentPage]
                            }
                            .background(
                                color = if (page == currentPage) Color.Blue else Color(0xFFDEB841),
                                shape = RectangleShape // Use RectangleShape for square buttons
                            )
                            .border(1.dp, Color.White, RectangleShape) // Add white border
                            .size(50.dp),
                    ) {
                        Text(
                            text = (page + 1).toString(),
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                // Next Page Button
                Box(
                    modifier = Modifier
                        .clickable {
                            if (currentPage < maxSlides) {
                                currentPage += 1
                                currentText = displayText[currentPage]
                            } else {
                                startLetsInvestScreen(navController)
                            }
                        }
                        .background(
                            color = Color(0xFFDEB841),
                            shape = RectangleShape // Use RectangleShape for square buttons
                        )
                        .border(1.dp, Color.White, RectangleShape) // Add white border
                        .size(50.dp, 50.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward, // Use built-in icon for next
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            // Skip Button
            Row(
                modifier = Modifier
                    .padding(top = 16.dp)
                    .clickable {
                        startLetsInvestScreen(navController)
                    }

            ) {
                Text(text = "Skip", color = Color(0xFFDEB841))
                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color(0xFFDEB841),
//                    modifier = Modifier.padding(start = 4.dp)
                )
            }


//            Row {
//                Button(onClick = { startLetsInvestScreen(navController) }) {
//                    Text(text = "SKIP")
//                }
//
//                Button(onClick = {
//                    if (i == maxSlides) {
//                        startLetsInvestScreen(navController)
//
//                    } else {
//                        i += 1
//                        currentText = displayText[i]
//                    }
//
//                })
//                { Text(text = "NEXT") }
//            }

        }
    }

}



data class introInfo(
    val title:String,
    val description:String,
    val img: Int
)

object introText{
    val introTextList = listOf(
        introInfo(
            title = "What is FICS?",
            img = R.drawable.intro1,
            description = "Welcome to the new way for college students to learn about the biggest investment market in the world: the Fixed Income market."
        ),
        introInfo(
            title = "Why Now?",
            img = R.drawable.intro2,
            description = "With the global fixed income market reaching almost $130 trillion, there is more opportunity than ever for you to generate income from investing."
        ),
        introInfo(
            title = "FICS is Here",
            img = R.drawable.intro3,
            description = "We offer a fun, interactive trading simulation for you to learn firsthand how to invest in bonds – without real money. "
        ),
        introInfo(
            title = "Glossary",
            img = R.drawable.intro4,
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

/*
    Box(
        modifier = Modifier
            .background(color = lightGray)
            .fillMaxHeight()
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp),
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
                // You can customize the content of the button here
                // For example, you can add Text, Icon, or any other Composables
                Text(
                    text = "Get Started",
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
 */