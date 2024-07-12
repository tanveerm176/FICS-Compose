package com.example.fics_compose.presentation.ui.screens

import android.util.Log
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fics_compose.presentation.ui.theme.lightGray
import com.example.fics_compose.presentation.ui.theme.yellow
import com.example.fics_compose.presentation.viewmodel.IntroductionViewModel

@Composable
fun IntroductionScreen(
    onSkipIntroButtonClicked: () -> Unit,
    introductionViewModel: IntroductionViewModel = viewModel()
) {
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
                text = introductionViewModel.currentContent.title,
                style = MaterialTheme.typography.titleMedium
            )
            Image(
                painter = painterResource(id = introductionViewModel.currentContent.img),
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
                    text = introductionViewModel.currentContent.description,
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
                            introductionViewModel.previousPage()
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
                                introductionViewModel.updatePage(page)
                            }
                            .background(
                                color = if (page == introductionViewModel.currentPage) Color(
                                    0xFF8A191D
                                )
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
                                    onSkipIntroButtonClicked()
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
                onClick = onSkipIntroButtonClicked,
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

@Composable
@Preview
fun IntroPreview() {
    IntroductionScreen(onSkipIntroButtonClicked = {
        Log.d("Intro-Skipped", "Intro Skip Button Clicked")
    })
}

