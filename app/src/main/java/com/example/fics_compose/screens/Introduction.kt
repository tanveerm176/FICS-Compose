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
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fics_compose.R
import com.example.fics_compose.ScreenData.IntroductionData
import com.example.fics_compose.ScreenData.IntroductionText
import com.example.fics_compose.WelcomeNav
import com.example.fics_compose.ui.theme.lightGray
import com.example.fics_compose.ui.theme.yellow

@Composable
fun IntroductionScreen(
    startLetsInvestScreen: () -> Unit,
//    displayText: List<IntroductionData> = IntroductionText.introTextList,
    introductionViewModel: IntroductionViewModel = viewModel()
) {
//    val introductionUiState by introductionViewModel.introductionUiState.collectAsState()
//    val maxSlides = displayText.size - 1
//    var introListIndex by remember { mutableIntStateOf(0) }
//
//    var currentPage by remember { mutableIntStateOf(0) }
//
//    var currentText by remember { mutableStateOf(displayText[introListIndex]) }

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
        ) {
            Text(
                modifier = Modifier.padding(top = 6.dp),
                text = introductionViewModel.currentText.title,
                style = MaterialTheme.typography.titleMedium
            )
            Image(
                painter = painterResource(id = introductionViewModel.currentText.img),
                contentDescription = null,
                modifier = Modifier
                    .size(252.dp)
                    .clip(MaterialTheme.shapes.medium)
            )

            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = yellow, shape = RectangleShape)
                    .absolutePadding(top = 6.dp, bottom = 13.dp)
                    .border(1.5.dp, Color.White)
                    .size(width = 200.dp, height = 125.dp)
            ) {
                Text(
                    text = introductionViewModel.currentText.description,
                    modifier = Modifier.padding(5.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
            }

            /* Page Indicator*/
            Row(
                modifier = Modifier.padding(top = 17.dp, bottom = 26.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                /* Previous Page Button*/
                Box(
                    modifier = Modifier
                        .clickable {
                            if (introductionViewModel.currentPage > 0) {
                                introductionViewModel.previousPage()
                            }
                        }
                        .background(
                            color = Color(0xFFDEB841),
                            shape = RectangleShape // Use RectangleShape for square buttons
                        )
                        .border(1.dp, Color.White, RectangleShape) // Add white border
                        .size(42.dp, 50.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, 
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                /* Page Number Buttons*/
                for (page in 0 until introductionViewModel.maxSlides + 1) {
                    Box(
                        modifier = Modifier
                            .clickable {
                                introductionViewModel.currentPage = page
                                introductionViewModel.displayCurrentText(introductionViewModel.currentPage)
                            }
                            .background(
                                color = if (page == introductionViewModel.currentPage) Color(0xFF8A191D)
                                else Color(0xFFDEB841),
                                shape = RectangleShape // Use RectangleShape for square buttons
                            )
                            .border(1.dp, Color.White, RectangleShape) // Add white border
                            .size(42.dp, 50.dp),
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
                        .clickable(
                            onClick = {
                                if (introductionViewModel.currentPage < introductionViewModel.maxSlides) {
                                    introductionViewModel.nextPage()
                                } else {
                                    startLetsInvestScreen()
                                }
                            }
                        )
                        .background(
                            color = Color(0xFFDEB841),
                            shape = RectangleShape // Use RectangleShape for square buttons
                        )
                        .border(1.dp, Color.White, RectangleShape) // Add white border
                        .size(42.dp, 50.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward, // Use built-in icon for next
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
            
            Spacer(modifier = Modifier.size(40.dp))

            Button(
                onClick = startLetsInvestScreen,
                modifier = Modifier
                    .border(1.5.dp, Color(0xFF8A191D), RoundedCornerShape(30.dp))
                    .align(Alignment.End),
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
                    Text(text = "Skip", color = Color(0xFF8A191D), fontWeight = FontWeight.Bold)
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowRight,
                        contentDescription = "Skip to Let's Invest Screen",
                        tint = Color(0xFF8A191D),
                    )
                }
            }
        }
    }
}

