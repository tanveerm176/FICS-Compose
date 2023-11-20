package com.example.fics_compose.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.fics_compose.DialogContent
import com.example.fics_compose.ui.theme.FICSComposeTheme

@Composable
fun SimulatorDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    title: String,
    info: String,
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = { onDismissRequest() },
        ) {
            // Draw a rectangle shape with rounded corners inside the dialog
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(16.dp),
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = info,
                        modifier = Modifier.padding(16.dp),
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Button(
                            onClick = { onDismissRequest() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("SKIP")
                        }
                        Button(
                            onClick = { onConfirmation() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Text("NEXT")
                        }
                    }
                }
            }
        }
    }
}

// TODO: Create separate functions for each dialog set so it can be passed in as a parameter for ShowDialog()

fun IntroDialog(): List<DialogContent> {
    return Intro
}

fun HelpDialog(): List<DialogContent> {
    return Intro
}

fun TreasuryDialog(): List<DialogContent> {
    return Treasury
}


val Intro = listOf(
    DialogContent(
        title = "Welcome to the FICS!",
        info = "This is an investment simulation to help you learn about investing into the fixed income market.\n\nClick NEXT to learn how to use the simulator and to walk through some key financial terms."
    ),
    DialogContent(
        title = "Instructions",
        info = "Click START  to start the simulation and the timer.\nNote: Every 10 seconds is equivalent a month passing.\nand every month you get the option to invest in a new bond.\n\nIf you need more time to consider an investment, click PAUSE to pause the simulation.\n\nIf you would like to redo the simulation from the beginning, click RESTART to reset the simulation."
    ),
    DialogContent(
        title = "Investing",
        info = "You can choose how many bonds you want to invest in based on the price and interest rate of the bond and your wallet.\n\nYour wallet is the cash you have available to invest.\n\nYour Net Worth is the difference between what you own (assets) and what you owe (liabilities). It represents your overall financial value or wealth.\n\nThe Monthly Return is the periodic interest rate payment."
    ),
    DialogContent(
        title = "Fixed Income",
        info = "Fixed income is a type of investment that pays the investor a fixed amount on a fixed schedule."
    ),
    DialogContent(
        title = "Bond",
        info = "A bond is a debt security, which means borrowers issue bonds to raise money from investors willing to lend them money for a certain amount of time. \n\nWhen you buy a bond, you are lending to the issuer, which may be a government, municipality, or corporation. In return, the issuer promises to pay you a specified rate of interest during the life of the bond and to repay the principal, also known as face value or par value of the bond, when it matures or comes due after a set period of time."
    ),
    DialogContent(
        title = "Treasury Bond",
        info = "A Treasury bond is a long-term, low-risk government debt security issued by the U.S. Department of the Treasury.\nIt is considered one of the safest investments due to the backing of the U.S. government."
    ),
    // Add more dialog content here as needed
)

val Treasury = listOf(
    DialogContent(
        title = "Treasury Bonds",
        info = "Lorem ipsum"
    ),
)