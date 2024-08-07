package com.example.fics_compose.presentation.ui.screens

import android.annotation.SuppressLint
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
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.fics_compose.domain.model.BondInfo
import com.example.fics_compose.data.db.DatabaseBuilder
import com.example.fics_compose.presentation.screendata.BondOption
import com.example.fics_compose.domain.model.UserInfo
import com.example.fics_compose.presentation.ui.components.SimulatorContent
import com.example.fics_compose.presentation.ui.components.SimulatorDialog
import com.example.fics_compose.presentation.ui.theme.lightGray
import com.example.fics_compose.presentation.viewmodel.SimulatorViewModel


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimulatorScreen(
    userInfo: UserInfo,
    onResetSimClick: () -> Unit,
    onSkipClick: () -> Unit,
    navigateToHistory: () -> Unit,
    onShoppingCartClick: () -> Unit,
    simulatorViewModel: SimulatorViewModel = viewModel()
) {
//    var simNumber = remember { mutableIntStateOf(0) }
//    val scope = rememberCoroutineScope()
//    val snackbarHostState = remember { SnackbarHostState() }
//    var showHelp = remember { mutableStateOf(false) }


    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = simulatorViewModel.snackbarHostState)
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
                    onClick = { simulatorViewModel.showHelpDialog() },
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

            if (simulatorViewModel.showHelpDialog.value) {
                ShowSimHelpDialog(
                    onSkip = { simulatorViewModel.dismissHelpDialog() },
                    simulatorViewModel.simNumber
                )
            }

            SimulatorCard(
                userInfo,
//                bondsList = simulatorViewModel.bondsList,
//                simNumber = simulatorViewModel.simNumber,
//                scope,
//                snackbarHostState = simulatorViewModel.snackbarHostState,
                onSkipClick = onSkipClick,
                navigateToHistory = navigateToHistory,
                onShoppingCartClick = onShoppingCartClick,
                simulatorViewModel = simulatorViewModel
            )
        }
    }
}

@Composable
fun SimulatorCard(
    userInfo: UserInfo,
//    bondsList: List<BondOption>,
//    simNumber: Int,
//    simNumber: MutableIntState,
//    scope: CoroutineScope,
//    snackbarHostState: SnackbarHostState,
    onSkipClick: () -> Unit,
    navigateToHistory: () -> Unit,
    onShoppingCartClick: () -> Unit,
    simulatorViewModel: SimulatorViewModel
) {
    /* for traversing bonds list*/
    var bondsListIndex by remember { mutableIntStateOf(userInfo.month.intValue) }
    var numOfBonds by remember { mutableIntStateOf(userInfo.numBonds.intValue) }

    var currentBond = simulatorViewModel.bondsList[userInfo.month.intValue]

    var month by remember { mutableIntStateOf(userInfo.month.intValue + 1) }
    val currContext = LocalContext.current

    val database = DatabaseBuilder.getDatabase(currContext)
    val dao = database.historyDAO()

    val scope = rememberCoroutineScope()

//    var showAlertDialog by remember { mutableStateOf(false) }

    if (simulatorViewModel.showAlertDialog) {

//        val randomInt = Random.nextInt(0, userInfo.investList.size - 1)
//        val randomBondTitle = userInfo.investList[randomInt].bondTitle
        val (randomInt, randomBondTitle) = simulatorViewModel.getRandomBond(userInfo)
        ShowAlertDialog(
            randomBondTitle = randomBondTitle,
            onSkip = {
                onSkipClick()
                userInfo.defaultRisk(randomInt)
//                showAlertDialog = false
                simulatorViewModel.dismissAlertDialog()
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
            BondCard(
                bond = simulatorViewModel.bondsList[bondsListIndex],
                userInfo = userInfo,
                simulatorViewModel = simulatorViewModel
            )

            Column {
                UserCard(
                    bond = simulatorViewModel.bondsList[bondsListIndex],
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

                        /*update wallet with monthly return*/
                        userInfo.wallet.doubleValue += userInfo.monthlyReturn.doubleValue

                        /* increment bond*/
                        bondsListIndex = (bondsListIndex + 1) % simulatorViewModel.bondsList.size
                        currentBond = simulatorViewModel.bondsList[bondsListIndex]

                        /* end simulation, go to history screen*/
                        if (month == 13) {
                            simulatorViewModel.toastMessages(currContext, "finish")
                            simulatorViewModel.finishSimulation(scope, userInfo, dao)
                            navigateToHistory()

                            /*Reset user and sim*/
                            /*val usrHistory = HistoryItem(
                                netWorth = userInfo.netWorth,
                                wallet = userInfo.wallet.doubleValue,
                                gains = userInfo.totalGains,
                                trades = userInfo.trades.intValue
                            )
                            scope.launch {
                                insertHistory(usrHistory, dao)
                            }*/
                        }

                        /* new bond category*/
                        if (month % 4 == 0) {
                            simulatorViewModel.incrementSimNumber()
                            simulatorViewModel.showNewBondSnackBar(scope)
//                            simNumber += 1
                            /*scope.launch {
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
                            }*/
                        }

                        /* default risk*/
                        if (month == 7) {
//                            showAlertDialog = true
                            simulatorViewModel.showAlertDialog()
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
    simulatorViewModel: SimulatorViewModel
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
                    WhiteBox("Return", "$${simulatorViewModel.calcMonthlyReturn(bond)}")
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
//                onInvestClicked = onInvestClicked
            )
            /* Invest Button*/
            Button(
                onClick = {
                    /* add bond to investment list*/
                    var bondInfo =
                        BondInfo(bond.title, bond.price, bond.interestRate, numberOfBonds)
                    userInfo.investList.add(bondInfo)

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
//    onInvestClicked: () -> Unit
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
private fun ShowSimHelpDialog(
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
//    val showDialog = remember { mutableStateOf(true) }
    AlertDialog(
        onDismissRequest = {
//                showDialog.value = false
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
//                        showDialog.value = false
                    onSkip()
                },
            ) {
                Text("Ok")
            }
        }
    )

//    if (showDialog.value) {}
}

/*private fun toastMessages(context: Context, flg: String) {
    when (flg) {
        "reset" -> Toast.makeText(context, "Simulation Reset", Toast.LENGTH_LONG).show()
        "finish" -> Toast.makeText(
            context,
            "Simulation Complete, View Portfolio",
            Toast.LENGTH_LONG
        ).show()

        "newBond" -> Toast.makeText(context, "New Bond!", Toast.LENGTH_LONG).show()
    }
}*/

/*
suspend fun insertHistory(item: HistoryItem, dao: HistoryDAO) {
    dao.insert(item)
}*/
