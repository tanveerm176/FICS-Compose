package com.example.fics_compose.screens

import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fics_compose.BondInfo
import com.example.fics_compose.InternalNav
import com.example.fics_compose.R
import com.example.fics_compose.UserInfo
import com.example.fics_compose.ui.theme.lightGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch



@Composable
fun PortfolioScreen(user: UserInfo?, navController: NavController) {
    if (user != null) {
        val wallet = remember { mutableDoubleStateOf(user.wallet) }
        val investments = remember { mutableDoubleStateOf(user.investments) }
        val netWorth = remember { mutableDoubleStateOf(user.netWorth) }
        val monthlyReturn = remember { mutableDoubleStateOf(user.monthlyReturn) }
        var showHelp = remember { mutableStateOf(false) }

        Column(
            modifier = Modifier
                .background(color = lightGray)
                .padding(start = 5.dp, end = 8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ){
            Row {
                    IconButton(
                        onClick = {
                            if (user != null) {
                                returnToSimulator(navController, user)
                            }
                            if (user != null) {
                                Log.d("current invest-list", "${user.investList.toList()}")
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                Spacer(modifier = Modifier.width(24.dp))
                Button(
                    onClick = { showHelp.value = true },
                    shape = RoundedCornerShape(200.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 10.dp,
                        pressedElevation = 6.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8A191D),
                        contentColor = Color.White,
                    ),
                ){
                    Text(
                        text = "?",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }


            if (showHelp.value) {
                ShowHelpDialog(onSkip = { showHelp.value = false })
            }

            Text(
                text = "Current Portfolio",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(15.dp)
            )
            Text(
                text = "Here’s everything you’ve already invested in.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(start = 62.dp, end = 65.dp)
            )
            Text(
                text = " . . .",
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFDEB841),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(start = 62.dp, end = 65.dp)
            )


            userCard(wallet, investments, netWorth, monthlyReturn)
            LazyColumn {
                itemsIndexed(user.investList) { index, bondPurchased ->
                    PortfolioCard(
                        bondPurchased,
                        index,
                        user,
                        wallet,
                        investments,
                        netWorth,
                        monthlyReturn
                    )
                }
            }
        }
    }
}

@Composable
private fun ShowHelpDialog(onSkip: () -> Unit) {
    var showDialog by remember { mutableStateOf(true) }
    var currentDialogIndex by remember { mutableStateOf(0) }
    var dialogList = SimulatorContent

    if (currentDialogIndex < dialogList.size) {
        val currentDialog = dialogList[currentDialogIndex]

        AlertDialog(
            onDismissRequest = {
                onSkip()
                showDialog = false // Dismiss the dialog
            },
            title = {
                Text(currentDialog.title)
            },
            text = {
                Text(currentDialog.info)
            },
            confirmButton = {
                Button(
                    onClick = {
                        if (currentDialogIndex < dialogList.size - 1) {
                            // Display the next dialog content
                            currentDialogIndex++
                        }
                    },
                ) {
                    Text("Next")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        onSkip()
                        showDialog = false // Dismiss the dialog
                    },
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}

@Composable
fun userCard(
    wallet: MutableDoubleState,
    investments: MutableDoubleState,
    netWorth: MutableDoubleState,
    monthlyReturn: MutableDoubleState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 3.dp, bottom = 20.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Column 1: Wallet
        Column(
            modifier = Modifier
                .weight(1.11f)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Wallet",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF8A191D),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(3.dp)
            )
            Text(
                text = "$${wallet.value}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        // Divider
        Divider(
            modifier = Modifier
                .height(60.dp)
                .width(1.1.dp)
                .background(Color.Gray),
        )

        // Column 2: Net Worth
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Net Worth",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF8A191D),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "$${netWorth.value}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }

        // Divider
        Divider(
            modifier = Modifier
                .height(60.dp)
                .width(1.1.dp)
                .background(Color.Gray),
        )
        // Column 3: Total Investments
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "Investments",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF8A191D),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(5.dp)
            )
            Text(
                text = "$${investments.value}",
                style = MaterialTheme.typography.bodyMedium,
            )
        }
    }
}



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioCard(
    bondPurchased: BondInfo,
    index: Int,
    user: UserInfo,
    wallet: MutableDoubleState,
    investments: MutableDoubleState,
    netWorth: MutableDoubleState,
    monthlyReturn: MutableDoubleState
) {
    var isExpanded by remember { mutableStateOf(false) }
    val bondTitle = bondPurchased.bondTitle
    val bondPrice = bondPurchased.bondPrice
    val bondRate = bondPurchased.interestRate
    val numBonds = bondPurchased.numberOfBonds
    val investval = bondPurchased.investment

    val showAlert = remember { mutableStateOf(false) }

    if (showAlert.value) {
        ShowAlertDialog()
    }

    val cardColor = if (bondPrice == 0.0) {
        Color(0xFF8A191D)
    } else {
        Color.Transparent
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(8.dp))
        Column() {
            Spacer(modifier = Modifier.height(6.dp))

            Spacer(modifier = Modifier.height(4.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { isExpanded = !isExpanded }
                    .background(
                        color = cardColor,
                        shape = RoundedCornerShape(10.dp)
                    )
                    .border(3.dp, Color(0xFFDEB841), RoundedCornerShape(10.dp))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(0.55f)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "$bondTitle",
                                color = Color.Black,
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                        // Divider
                        Divider(
                            modifier = Modifier
                                .height(60.dp)
                                .width(1.5.dp)
                                .background(Color.Gray),
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(all = 3.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Number Bought",
                                color = Color(0xFF8A191D),
                                style = MaterialTheme.typography.bodySmall,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "$numBonds",
                                color = Color.Black,
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                        // Divider
                        Divider(
                            modifier = Modifier
                                .height(60.dp)
                                .width(1.5.dp)
                                .background(Color.Gray),
                        )
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(top = 6.dp, start = 3.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Investment Value",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF8A191D),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "$$investval",
                                color = Color.Black,
                                modifier = Modifier.padding(all = 3.dp),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    if (isExpanded) {
                        // Additional content when expanded
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 16.dp)
                        ) {
                            // First column
                            Column(
                                modifier = Modifier
                                    .weight(0.9f)
                                    .padding(start = 5.dp, end = 5.dp)
                            ) {
                                WhiteBox("Interest Rate", "$bondRate%")
                                Spacer(modifier = Modifier.height(16.dp))
                                WhiteBox("Bond Price", "$$bondPrice")
                            }

                            // Second column
                            Column(
                                modifier = Modifier
                                    .weight(1f)
                                    .padding(start = 5.dp, end = 5.dp)
                            ) {
                                WhiteBox("Return", "$${bondPurchased.monthlyReturn}")
                                Spacer(modifier = Modifier.height(24.dp))

                                // Nested Row for the second row
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {

                                    // Second column in the second row
                                    Box(
                                        modifier = Modifier.weight(1f),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Button(
                                            onClick = {
                                                if (bondPrice == 0.0 && bondRate == 0.0) {
                                                    showAlert.value = true
                                                }
                                                user.wallet += (bondPrice * numBonds)
                                                wallet.value = user.wallet
                                                user.monthlyReturn -= bondPurchased.monthlyReturn
                                                monthlyReturn.value = user.monthlyReturn
                                                user.investments -= bondPurchased.investment
                                                investments.value = user.investments
                                                netWorth.value = user.netWorth
                                                user.investList.removeAt(index)
                                                Log.d(
                                                    "bondSold",
                                                    "Bond Sold: $bondTitle at index $index"
                                                )
                                                Log.d("sellBond", "{${user.investList.toList()}}")
                                            } ,
                                            shape = RoundedCornerShape(7.dp),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFFDEB841),
                                                contentColor = Color.White,
                                            )
                                        ) {
                                            Text(text = "Sell")
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun WhiteBox(label: String, value: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Column (modifier = Modifier
            .padding(10.dp)){
            Text(text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = Color(0xFFDEB841),
                textAlign = TextAlign.Center)
            Spacer(modifier = Modifier.height(2.dp))
            Text(text = value,
                style = MaterialTheme.typography.displayLarge,
                textAlign = TextAlign.Center)
        }
    }
}

@Composable
private fun ShowAlertDialog() {
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Remember!")
                }
            },
            text = {
                Text(
                    text = "Credit risk should be a huge consideration every time you invest. " +
                            "Make sure to research your companies and understand what they need " +
                            "the bond for before you buy their debt, or else they might default and " +
                            "you take on a loss, which means all of the investment value from that " +
                            "bond must be subtracted from your net worth. Watch out for buying " +
                            "too much that you go into the negatives – that means you’ve gone into " +
                            "debt in order to buy debt (bad idea)."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                    },
                ) {
                    Text("Dismiss")
                }
            }
        )
    }
}

fun returnToSimulator(navController: NavController, user:UserInfo){
    navController.currentBackStackEntry?.savedStateHandle?.set("user",user)
    navController.navigate(InternalNav.Simulator.route)
}