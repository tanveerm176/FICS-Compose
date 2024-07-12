package com.example.fics_compose.presentation.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.fics_compose.presentation.ui.theme.FICSComposeTheme

@Composable
fun SimulatorDialog(
    showDialog: Boolean,
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    onPrev: () -> Unit,
    simNumber: Int,
) {
    var title = SimulatorContent[simNumber].title
    var info = SimulatorContent[simNumber].info

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
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 8.dp, top = 8.dp),
                        horizontalArrangement = Arrangement.End,
                        verticalAlignment = Alignment.Top,
                    ) {
                        Button(
                            onClick = { onDismissRequest() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "Exit")
                        }
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.bodySmall,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        modifier = Modifier.padding(16.dp),
                        textAlign = TextAlign.Center,
                    )
                    Text(
                        text = info,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(16.dp),
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.Bottom,
                    ) {
                        Button(
                            onClick = { onPrev() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Prev")
                        }
                        Button(
                            onClick = { onConfirmation() },
                            modifier = Modifier.padding(8.dp),
                        ) {
                            Icon(
                                imageVector = Icons.Default.ArrowForward,
                                contentDescription = "Next"
                            )
                        }
                    }
                }
            }
        }
    }
}

data class DialogContent(
    val title: String,
    val info: String
)

val SimulatorContent = listOf(
    DialogContent(
        title = "Treasury Bonds",
        info = "T-Bonds have maturities exceeding ten years, typically up to 30 years. Like T-Notes, they pay a fixed interest rate every six months, and the investor receives the face value at maturity."
        //"This is an investment simulation to help you learn about investing into the fixed income market.\n\nClick NEXT to learn how to use the simulator and to walk through some key financial terms."
    ),
    DialogContent(
        title = "Corporate Bonds",
        info = "Bonds issued by corporations to raise capital for various purposes, such as financing operations, expansion, or acquisitions."
        //"Click START  to start the simulation and the timer.\nNote: Every 10 seconds is equivalent a month passing.\nand every month you get the option to invest in a new bond.\n\nIf you need more time to consider an investment, click PAUSE to pause the simulation.\n\nIf you would like to redo the simulation from the beginning, click RESTART to reset the simulation."
    ),
    DialogContent(
        title = "Asset-Backed Bonds",
        info = "Asset-backed securities (ABS) are financial instruments that represent an ownership interest in a pool of assets, typically loans or receivables. These assets can include auto loans, credit card receivables, mortgages, student loans, and other types of debt. The cash flows from the underlying assets are used to make payments to the holders of the asset-backed securities."
        //"You can choose how many bonds you want to invest in based on the price and interest rate of the bond and your wallet.\n\nYour wallet is the cash you have available to invest.\n\nYour Net Worth is the difference between what you own (assets) and what you owe (liabilities). It represents your overall financial value or wealth.\n\nThe Monthly Return is the periodic interest rate payment."
    ),
    DialogContent(
        title = "TIPS",
        info = "TIPS are designed to protect investors from inflation. The principal amount of TIPS increases with inflation and decreases with deflation, while interest payments are calculated based on the adjusted principal."
        //"Fixed income is a type of investment that pays the investor a fixed amount on a fixed schedule."
    ),
    DialogContent(
        title = "Mutual Funds",
        info = "Bond mutual funds invest in a diversified portfolio of bonds. Investors buy shares in the mutual fund, and the fund manager makes decisions on behalf of the investors. Bond mutual funds can focus on specific types of bonds, such as government bonds, corporate bonds, or municipal bonds."
        //"A bond is a debt security, which means borrowers issue bonds to raise money from investors willing to lend them money for a certain amount of time. \n\nWhen you buy a bond, you are lending to the issuer, which may be a government, municipality, or corporation. In return, the issuer promises to pay you a specified rate of interest during the life of the bond and to repay the principal, also known as face value or par value of the bond, when it matures or comes due after a set period of time."
    ),
    DialogContent(
        title = "ETFs",
        info = "Bond ETFs are similar to bond mutual funds but are traded on stock exchanges like individual stocks. They provide investors with an opportunity to buy and sell bond exposure throughout the trading day at market prices."
        //"A Treasury bond is a long-term, low-risk government debt security issued by the U.S. Department of the Treasury.\nIt is considered one of the safest investments due to the backing of the U.S. government."
    ),
    // Add more dialog content here as needed
)

@Preview
@Composable
fun PreviewDialog() {
    var showDialog by remember { mutableStateOf(true) }
    val dialogs = listOf(
        DialogContent(
            title = "Welcome to the FICS simulation!",
            info = "Click next for some helpful terms for you to know."
        ),
        DialogContent(
            title = "Fixed Income",
            info = "Fixed income is a type of investment that pays the investor a fixed amount on a fixed schedule."
        ),
    )
    var currentDialog = 0

    FICSComposeTheme {

        if (currentDialog < dialogs.size) {

            SimulatorDialog(
                showDialog = true,
                onDismissRequest = { showDialog = false },
                onConfirmation = {
                    currentDialog++
                },
                onPrev = {
                    currentDialog--
                },
                currentDialog
            )
        }
    }
}