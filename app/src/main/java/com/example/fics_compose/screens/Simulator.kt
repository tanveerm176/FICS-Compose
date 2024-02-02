package com.example.fics_compose.screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.example.fics_compose.BondInfo
import com.example.fics_compose.BottomNavBar
import com.example.fics_compose.DatabaseBuilder
import com.example.fics_compose.HistoryDAO
import com.example.fics_compose.HistoryItem
import com.example.fics_compose.ScreenData.BondData
import com.example.fics_compose.ScreenData.BondOption
import com.example.fics_compose.UserInfo
import com.example.fics_compose.ui.theme.lightGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimulatorScreen(
    userInfo: UserInfo,
    onResetSimClick: () -> Unit,
    onSkipClick: () -> Unit,
    navigateToHistory: () -> Unit,
    onShoppingCartClick: () -> Unit
) {
    var simNumber = remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var showHelp = remember { mutableStateOf(false) }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) {
        Column(
            modifier = Modifier
                .background(color = lightGray)
                .padding(top = 5.dp, start = 5.dp, end = 11.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                /* Reset Button*/
                Button(
                    onClick = onResetSimClick,
                    elevation = ButtonDefaults.buttonElevation(pressedElevation = 6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8A191D),
                    )
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset", tint = Color.White)
                }
                /*Help Button*/
                Button(
                    onClick = { showHelp.value = true },
                    shape = RoundedCornerShape(30.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 10.dp,
                        pressedElevation = 6.dp
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8A191D),
                        contentColor = Color.White,
                    ),
                ) {
                    Text(
                        text = "?",
                        fontWeight = FontWeight.ExtraBold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }

            if (showHelp.value) {
                ShowDialog(onSkip = { showHelp.value = false }, simNumber.value)
            }

            SimulatorCard(
                userInfo,
                bonds = BondData.BondDataList,
                simNumber,
                scope,
                snackbarHostState,
                onSkipClick = onSkipClick,
                navigateToHistory = navigateToHistory,
                onShoppingCartClick = onShoppingCartClick
            )
        }
    }
}

@Composable
fun SimulatorCard(
    userInfo: UserInfo,
    bonds: List<BondOption>,
    simNumber: MutableIntState,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
    onSkipClick: () -> Unit,
    navigateToHistory: () -> Unit,
    onShoppingCartClick: () -> Unit,
) {
    /* for traversing bonds list*/
    var i by remember { mutableIntStateOf(userInfo.month.intValue) }
    var numOfBonds by remember { mutableIntStateOf(userInfo.numBonds.intValue) }
    var currentBond = bonds[userInfo.month.intValue]

    var month by remember { mutableIntStateOf(userInfo.month.intValue + 1) }
    val currContext = LocalContext.current

    val database = DatabaseBuilder.getDatabase(currContext)
    val dao = database.historyDAO()

    var showAlertDialog by remember { mutableStateOf(false) }
    if (showAlertDialog) {

        /*note: Random int range should start from 3 since only corporate bonds incur credit default risk,
            however this results in app crash if user has < 3 bonds in their current portfolio,
            user is also unable to sell bonds from portfolio if range starts from 3 due to this crash*/
        val randomInt = Random.nextInt(0, userInfo.investList.size - 1)
        val randomBondTitle = userInfo.investList[randomInt].bondTitle
        ShowAlertDialog(
            randomBondTitle = randomBondTitle,
            onSkip = {
                onSkipClick()
                userInfo.defaultRisk(randomInt)
                showAlertDialog = false
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = lightGray)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        )
        {
            Row {
                /* Month Display*/
                Text(
                    text = "Month $month of 12",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 25.sp,
                    color = Color(0xFF8a191f),
                    style = MaterialTheme.typography.titleMedium,
                )
                Spacer(modifier = Modifier.width(8.dp))

            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                /* First Column (Image 1)*/
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Row(
                        modifier = Modifier
                            .padding(8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                    ) {
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 10.dp)
                        ) {
                            Text(
                                text = "Net Worth",
                                color = Color(0xFF8A191D),
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 10.dp, bottom = 1.dp),
                            )
                            Text(
                                text = "$${userInfo.netWorth}",
                                color = if (userInfo.netWorth >= 0) Color(0xff027148) else Color.Red,
                                style = MaterialTheme.typography.displayLarge,
                                modifier = Modifier.padding(start = 10.dp),
                            )
                        }
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 10.dp)
                        ) {
                            Text(
                                text = "Investments",
                                color = Color(0xFF8A191D),
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 20.dp, bottom = 1.dp)
                            )
                            Text(
                                text = "$${userInfo.investments.doubleValue}",
                                color = if (userInfo.investments.doubleValue >= 0) Color(0xff027148) else Color.Red,
                                style = MaterialTheme.typography.displayLarge,
                                modifier = Modifier.padding(start = 20.dp),
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(2.dp))
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Gray)
            )

            Image(
                painter = painterResource(id = currentBond.img),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .clip(MaterialTheme.shapes.medium)
            )
            BondCard(bond = bonds[i], userInfo = userInfo)

            Column {
                UserCard(
                    bond = bonds[i],
                    onShoppingCartClick = onShoppingCartClick,
                    numberOfBonds = numOfBonds,
                    onNumberOfBondsChanged = {
                        numOfBonds = it
                    },
                    userInfo = userInfo,
                    onInvestClicked = {
                        /* increment month*/
                        month += 1
                        userInfo.incrementMonth()
                        userInfo.wallet.doubleValue += userInfo.monthlyReturn.doubleValue

                        /* increment bond*/
                        i = (i + 1) % bonds.size
                        currentBond = bonds[i]

                        /* end simulation, go to history screen*/
                        if (month == 13) {
                            toastMessages(currContext, "finish")

                            /*Reset user and sim*/
                            val usrHistory = HistoryItem(
                                netWorth = userInfo.netWorth,
                                wallet = userInfo.wallet.doubleValue,
                                gains = userInfo.totalGains,
                                trades = userInfo.trades.intValue
                            )
                            scope.launch {
                                insertHistory(usrHistory, dao)
                            }
                            navigateToHistory()
                        }

                        /* new bond category*/
                        if (month % 4 == 0) {
                            simNumber.value += 1
                            scope.launch {
                                val result = snackbarHostState
                                    .showSnackbar(
                                        message = "New Bond Category! Click the help button to learn some key information!",
                                        actionLabel = "Dismiss",
                                        duration = SnackbarDuration.Short
                                    )
                                when (result) {
                                    SnackbarResult.ActionPerformed -> {}
                                    SnackbarResult.Dismissed -> {}
                                }
                            }
                        }

                        /* default risk*/
                        if (month == 7) {
                            showAlertDialog = true
                        }
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}


/* Function to calculate percentage change to be displayed as interactive UI*/
/*private fun calculatePercentChange(oldValue: Double, newValue: Double): Double {
    return if (oldValue != 0.0) {
        ((newValue - oldValue) / oldValue) * 100
    } else {
        // Handle the case where the old value is zero to avoid division by zero
        if (newValue == 0.0) {
            0.0
        } else {
            0.0
        }
    }
}*/

@Composable
fun BondCard(
    bond: BondOption,
    userInfo: UserInfo,
) {
    val bondRate = bond.interestRate

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.5.dp, Color(0xFFDEB841), RoundedCornerShape(10.dp))
            .background(
                Color.LightGray,
                RoundedCornerShape(10.dp)
            ) // Set the background color and shape of the box
    ) {
        Column(
            modifier = Modifier
                .padding(8.dp),
        ) {
            Text(
                text = bond.title,
                color = Color(0xFF8a191f),
                style = MaterialTheme.typography.displayMedium,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 1.dp)
            ) {
                /* First column*/
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    WhiteBox("Interest Rate", "${bondRate}%")
                    Spacer(modifier = Modifier.height(3.dp))
                    WhiteBox("Bond Price", "$${bond.price}")
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 1.dp, end = 1.dp, bottom = 1.dp)
                ) {
                    WhiteBox("Return", "$${(bond.price * bond.interestRate / 100)}")
                    Spacer(modifier = Modifier.height(3.dp))
                    WhiteBox("Wallet", "$${userInfo.wallet.doubleValue}")
                }
            }
        }
    }

}


@Composable
fun UserCard(
    bond: BondOption,
    onShoppingCartClick: () -> Unit,
    numberOfBonds: Int,
    onNumberOfBondsChanged: (Int) -> Unit,
    userInfo: UserInfo,
    onInvestClicked: () -> Unit
) {
    var numBonds by remember { mutableIntStateOf(numberOfBonds) }
    Spacer(modifier = Modifier.height(25.dp))
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
        ) {
            NumericInputField(
                value = numBonds,
                onValueChange = {
                    onNumberOfBondsChanged(it)
                },
                onInvestClicked = onInvestClicked
            )
            /* Invest Button*/
            Button(
                onClick = {
                    // add bond to investment list
                    var bondInfo =
                        BondInfo(bond.title, bond.price, bond.interestRate, numberOfBonds)
                    userInfo.investList.add(bondInfo)
                    Log.d("investList", "{${userInfo.investList.toList()}}") // log new list

                    userInfo.incrementTrades()
                    userInfo.wallet.doubleValue -= (bond.price * numberOfBonds)
                    userInfo.monthlyReturn.doubleValue += bondInfo.monthlyReturn
                    userInfo.investments.doubleValue += bondInfo.investment
                    userInfo.netWorthList.add(userInfo.netWorth)

                    numBonds = 0
                    onInvestClicked()
                },
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .height(40.dp)
                    .width(80.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 10.dp,
                    pressedElevation = 6.dp
                ),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFDEB841),
                    contentColor = Color.White,
                )
            ) {
                Text(
                    text = "Buy",
                )
            }
            Spacer(modifier = Modifier.width(50.dp))

            IconButton(onClick = onShoppingCartClick) {
                Icon(
                    Icons.TwoTone.ShoppingCart,
                    contentDescription = "Investment List",
                    modifier = Modifier.size(50.dp)
                )
            }

        }

    }
}


@Composable
fun NumericInputField(
    value: Int,
    onValueChange: (Int) -> Unit,
    onInvestClicked: () -> Unit
) {
    var text by remember { mutableStateOf(value.toString()) }

    Column {
        BasicTextField(
            value = text,
            onValueChange = {
                if (it.isEmpty() || it.isDigitsOnly()) {
                    text = it
                    onValueChange(it.toIntOrNull() ?: 0)
                }
            },
            textStyle = LocalTextStyle.current.copy(
                color = Color.Black,
                textAlign = TextAlign.Center
            ),
            modifier = Modifier
                .height(40.dp)
                .width(115.dp)
                .border(2.dp, Color(0xFFDEB841), shape = RoundedCornerShape(4.dp)),
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            /*keyboardActions = KeyboardActions(
                onDone = { onInvestClicked() }
            )*/
        )
    }
}


@Composable
private fun ShowDialog(
    onSkip: () -> Unit,
    simNumber: Int
) {
    var showDialog by remember { mutableStateOf(true) }
    var currentDialogIndex by remember { mutableIntStateOf(simNumber) }
    var dialogList = SimulatorContent

    if (currentDialogIndex < dialogList.size) {

        SimulatorDialog(
            showDialog = showDialog,
            onDismissRequest = {
                onSkip()
                currentDialogIndex = simNumber
                showDialog = false // Dismiss the dialog
            },
            onConfirmation = {
                if (currentDialogIndex < dialogList.size - 1) {
                    // Display the next dialog content
                    currentDialogIndex++
                }
            },
            onPrev = {
                if (currentDialogIndex > 0) {
                    // Display the previous dialog content
                    currentDialogIndex--
                }
            },
            currentDialogIndex
        )
    }
}


@Composable
private fun ShowAlertDialog(randomBondTitle: String, onSkip: () -> Unit) {
    val showDialog = remember { mutableStateOf(true) }

    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
                onSkip()
            },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Uh Oh!")
                }
            },
            text = {
                Text(
                    text = "Oh no! In a shocking turn of events, $randomBondTitle has " +
                            "encountered a credit risk crisis and must default on their bonds. Their bonds " +
                            "are worth basically nothing now, so let’s take it off of your hands and out of " +
                            "your portfolio value."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showDialog.value = false
                        onSkip()
                    },
                ) {
                    Text("Ok")
                }
            }
        )
    }
}

private fun toastMessages(context: Context, flg: String) {
    when (flg) {
        "reset" -> Toast.makeText(context, "Simulation Reset", Toast.LENGTH_LONG).show()
        "finish" -> Toast.makeText(
            context,
            "Simulation Complete, View Portfolio",
            Toast.LENGTH_LONG
        ).show()

        "newBond" -> Toast.makeText(context, "New Bond!", Toast.LENGTH_LONG).show()
    }
}

suspend fun insertHistory(item: HistoryItem, dao: HistoryDAO) {
    dao.insert(item)
}