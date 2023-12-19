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
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.fics_compose.R
import com.example.fics_compose.WelcomeNav
import com.example.fics_compose.ui.theme.lightGray
import com.example.fics_compose.ui.theme.yellow
import kotlin.math.max
import kotlin.math.min

@Composable
fun TutorialScreen(navController: NavController) {
    TutorialCard(navController = navController, displayText = tutorialText.tutorialTextList)


}

@Composable
fun TutorialCard(
    navController: NavController,
    displayText:List<tutorialInfo>
) {
    val maxSlides = displayText.size - 1
    var i by remember { mutableIntStateOf(0) }
    var currentPage by remember { mutableStateOf(0) }
    var currentText by remember { mutableStateOf(displayText[i]) }

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
                text = currentText.title,
                style = MaterialTheme.typography.titleMedium,
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .size(width = 200.dp, height = 200.dp)
                    .background(Color.Transparent) // Optional: Add a background color for clarity
            ) {
                Image(
                    painter = painterResource(id = currentText.img!!),
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
                        .absolutePadding(left = 10.dp, right = 10.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = currentText.description!!,
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
                            if (currentPage > 0) {
                                currentPage -= 1
                                currentText = displayText[currentPage]
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
                val startPage = max(0, currentPage - visiblePageNumbers / 2)
                val endPage = min(maxSlides, startPage + visiblePageNumbers - 1)

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
                                currentPage = page
                                currentText = displayText[currentPage]
                            }
                            .background(
                                color = if (page == currentPage) Color(0xFF8A191D) else Color(
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
                if (endPage < maxSlides - 0.5) {
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
                            if (currentPage < maxSlides) {
                                currentPage += 1
                                currentText = displayText[currentPage]
                                Log.d("NextPageButton", "Navigating to page $currentPage")
                            } else {
                                startGoTimeScreen(navController)
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
                onClick = { startGoTimeScreen(navController) },
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
data class tutorialInfo(
    val title: String,
    val img: Int?,
    val description: String?
)

object tutorialText{
    val tutorialTextList = listOf(
        tutorialInfo(
            title = "How to Play",
            img = R.drawable.tutorial1,
            description = "Follow the tutorial to learn about the buttons and metrics to make your experience smooth."
        ),
        tutorialInfo(
            "The Goal",
            R.drawable.tutorial2,
            "You have been given \$10,000 and a goal: to make as much money at the end of 12 months as you can. ",
        ),
        tutorialInfo(
            "Key Metrics" ,
            R.drawable.tutorial3,
            "Note your net worth, investments, federal funds rate, and wallet.",
        ),
        tutorialInfo(
            "Net Worth",
            R.drawable.tutorial4,
            "Your net worth is the combined value of your wallet and investments.",
        ),
        tutorialInfo(
            "Investments",
            R.drawable.tutorial5,
            "Your investments is the sum of the number of bonds multiplied by each of their prices.",
        ),
        tutorialInfo(
            "Wallet",
            R.drawable.tutorial6,
            "Keep track of your wallet, which tells you how much cash you have on hand to invest instantaneously.",
        ),
        tutorialInfo(
            "Fed Funds Rate",
            R.drawable.tutorial7,
            "The fed funds rate is the interest rate of the federal government, which indicates how well the economy is performing. High fed funds rate means lower bond prices, or a weaker economy. Low fed funds rate means higher bond prices, or a strong economy.",
        ),
        tutorialInfo(
            "An Intro to Bonds ",
            R.drawable.tutorial8,
            "Bonds provide a way for governments and companies to raise capital for various purposes, such as funding infrastructure projects or expanding business operations. Investors, in turn, are attracted to bonds for their fixed income stream and relative stability compared to more volatile investments like stocks. However, it's important to note that the value of bonds can fluctuate in response to changes in interest rates and other market conditions.",
        ),
        tutorialInfo(
            "Bond Transactions",
            R.drawable.tutorial9,
            "How does a bond transaction work: When an entity issues a bond, it essentially borrows money from bondholders. The bondholder, in turn, becomes a creditor to the issuer. In exchange for lending their money, bondholders receive periodic interest payments (referred to as coupon payments) and, upon maturity, the return of the principal amount they initially invested.",
        ),
        tutorialInfo(
            "How to Invest",
            R.drawable.tutorial10,
            "To invest, select the number of bonds you want to buy and click the buy button."
        ),
        tutorialInfo(
            "Check your portfolio",
            R.drawable.tutorial11,
            "If you want to see your whole portfolio of bonds you already bought, click on the shopping cart. There, you can also sell bonds from your portfolio. That money will go back into your wallet."
        ),
        tutorialInfo(
            "Your Goal",
            R.drawable.tutorial12,
            "The goal is to maximize your net worth by diversifying your portfolio and buying bonds at the best time. Buy at low price, sell at high price."
        ),
        tutorialInfo(
            "Need Help?",
            R.drawable.tutorial13,
            "Click on the help button if you want to learn more or return to the tutorial."
        )
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