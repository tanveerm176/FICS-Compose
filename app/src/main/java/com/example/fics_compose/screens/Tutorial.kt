package com.example.fics_compose.screens

import android.util.Log
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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.absolutePadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.fics_compose.R
import com.example.fics_compose.ScreenData.TutorialInfo
import com.example.fics_compose.ScreenData.TutorialText
import com.example.fics_compose.WelcomeNav
import com.example.fics_compose.ui.theme.lightGray
import com.example.fics_compose.ui.theme.yellow
import kotlin.math.max
import kotlin.math.min


@Composable
fun TutorialScreen(
    onTutorialSkipClicked: () -> Unit,
//    displayText: List<TutorialInfo> = TutorialText.tutorialTextList,
    tutorialViewModel: TutorialViewModel = viewModel()
) {
//    val maxSlides = displayText.size - 1
//    var i by remember { mutableIntStateOf(0) }
//    var currentPage by remember { mutableStateOf(0) }
//    var currentText by remember { mutableStateOf(displayText[i]) }
    val descriptionScrollState = rememberScrollState()

    Box(
        modifier = Modifier
            .background(color = lightGray)
            .fillMaxWidth(),
    ) {
        Column(
            modifier = Modifier
                .padding(30.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                modifier = Modifier.padding(top=6.dp),
                text = tutorialViewModel.currentText.title,
                style = MaterialTheme.typography.titleMedium,
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .size(width = 200.dp, height = 200.dp)
                    .background(Color.Transparent) // Optional: Add a background color for clarity
            ) {
                Image(
                    painter = painterResource(id = tutorialViewModel.currentText.img),
                    contentDescription = "Image",
                    modifier = Modifier
                        .fillMaxSize() // Fill the entire Box with the Image
                )
            }
            BoxWithConstraints(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = yellow, shape = RectangleShape)
                    .absolutePadding(top = 4.dp, bottom = 4.dp)
                    .border(1.5.dp, Color.White)
                    .size(width = 200.dp, height = 260.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(descriptionScrollState)
                        .absolutePadding(left = 10.dp, right = 10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = tutorialViewModel.currentText.description,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(5.dp)
                    )
                }
            }

            // Page Indicator
            Row(
                modifier = Modifier.padding(top = 15.dp, bottom = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Previous Page Button
                Box(
                    modifier = Modifier
                        .clickable {
                            if (tutorialViewModel.currentPage > 0) {
                                tutorialViewModel.previousPage()
                            }
                        }
                        .background(
                            color = Color(0xFFDEB841),
                            shape = RectangleShape // Use RectangleShape for square buttons
                        )
                        .border(0.5.dp, Color.White, RectangleShape) // Add white border
                        .size(50.dp, 35.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack, // Use built-in icon for previous
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                // Page Number Buttons
                val visiblePageNumbers = 3
                val startPage = max(0, tutorialViewModel.currentPage - visiblePageNumbers / 2)
                val endPage = min(tutorialViewModel.maxSlides, startPage + visiblePageNumbers - 1)

                if (startPage > 0) {
                    // Display "..." if there are more pages to the left
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFDEB841),
                                shape = RectangleShape // Use RectangleShape for square buttons
                            )
                            .border(0.5.dp, Color.White, RectangleShape) // Add white border
                            .size(35.dp, 35.dp),
                    ) {
                        Text(
                            text = "...",
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                for (page in startPage..endPage) {
                    Box(
                        modifier = Modifier
                            .clickable {
                                tutorialViewModel.currentPage = page
                                tutorialViewModel.displayCurrentText(tutorialViewModel.currentPage)
                            }
                            .background(
                                color = if (page == tutorialViewModel.currentPage) Color(0xFF8A191D) else Color(
                                    0xFFDEB841
                                ),
                                shape = RectangleShape // Use RectangleShape for square buttons
                            )
                            .border(0.5.dp, Color.White, RectangleShape) // Add white border
                            .size(30.dp, 35.dp),
                    ) {
                        Text(
                            text = (page + 1).toString(),
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                // "..." Box with Border
                if (endPage < tutorialViewModel.maxSlides - 0.5) {
                    Box(
                        modifier = Modifier
                            .background(
                                color = Color(0xFFDEB841),
                                shape = RectangleShape // Use RectangleShape for square buttons
                            )
                            .border(0.5.dp, Color.White, RectangleShape) // Add white border
                            .size(35.dp, 35.dp),
                    ) {
                        Text(
                            text = "...",
                            color = Color.White,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }

                // Next Page Button
                Box(
                    modifier = Modifier
                        // Add logging statements in the "Next Page Button" click listener
                        .clickable {
                            if (tutorialViewModel.currentPage < tutorialViewModel.maxSlides) {
                                tutorialViewModel.nextPage()
//                                Log.d("NextPageButton", "Navigating to page $currentPage")
                            } else {
                                onTutorialSkipClicked()
                                Log.d("NextPageButton", "Navigating to GoTimeScreen")
                            }
                        }
                        .background(
                            color = Color(0xFFDEB841),
                            shape = RectangleShape // Use RectangleShape for square buttons
                        )
                        .border(0.5.dp, Color.White, RectangleShape) // Add white border
                        .size(50.dp, 35.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward, // Use built-in icon for next
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }

            Button(
                onClick = onTutorialSkipClicked,
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
                        contentDescription = null,
                        tint = Color(0xFF8A191D),
                    )
                }
            }
        }
    }
}



@Composable
@Preview
fun TutorialPreview() {
    TutorialScreen(onTutorialSkipClicked = {
        Log.d("tutorialSkip","Tutorial Skip Button Clicked")
    })
}
