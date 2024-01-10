package com.example.fics_compose.screens

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.twotone.ShoppingCart
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
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
import androidx.compose.material3.Surface
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
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import com.example.fics_compose.BondInfo
import com.example.fics_compose.BondOption
import com.example.fics_compose.BottomNavBar
import com.example.fics_compose.DatabaseBuilder
import com.example.fics_compose.HistoryDAO
import com.example.fics_compose.HistoryItem
import com.example.fics_compose.InternalNav
import com.example.fics_compose.R
import com.example.fics_compose.UserInfo
import com.example.fics_compose.ui.theme.lightGray
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random


//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun SimulatorTopAppBar(navController: NavController, user: UserInfo? = null) {
//    var scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
//    var showHelp = remember { mutableStateOf(false) }
//    var simNumber = remember { mutableIntStateOf(0) }
//    val scope = rememberCoroutineScope()
//    val snackbarHostState = remember { SnackbarHostState() }
//
//    Scaffold(
//        snackbarHost = {
//            SnackbarHost(hostState = snackbarHostState)
//        },
//        topBar = {
//            TopAppBar(
//                colors = TopAppBarDefaults.smallTopAppBarColors(
//                    containerColor = MaterialTheme.colorScheme.primaryContainer,
//                    titleContentColor = MaterialTheme.colorScheme.secondary,
//                ),
//                title = {
//                    Box(modifier = Modifier.fillMaxWidth()) {
//                        Text(
//                            modifier = Modifier.align(Alignment.CenterStart),
//                            text = "FICS Simulator"
//                        )
//                        Button(
//                            onClick = { showHelp.value = true },
//                            modifier = Modifier
//                                .align(Alignment.TopEnd)
//                                .padding(horizontal = 5.dp)
//                        ) {
//                            Text(text = "Help")
//                        }
//                        if (showHelp.value) {
//                            ShowDialog(onSkip = {showHelp.value = false}, simNumber.value)
//                        }
//                    }
//                }
//            )
//            scrollBehavior = scrollBehavior
//        },
//    ) {innerPadding ->
//        Box(modifier = Modifier.padding(innerPadding)){
//            SimulatorScreen(navController, user, simNumber, scope, snackbarHostState)
//        }
//    }
//}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimulatorScreen(
    navController: NavController,
    user: UserInfo? = null
){
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
//                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Reset Button
                Button(
                    onClick = { resetSimulation(navController) },
                    elevation = ButtonDefaults.buttonElevation(pressedElevation = 6.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF8A191D),
                    )
                ) {
                    Icon(Icons.Default.Refresh, contentDescription = "Reset", tint = Color.White)
                }
                //Help Button
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

            //when first starting the sim
            if (user == null) {
                SimulatorCard(
                    UserInfo(),
                    bonds = TestData.testDataList,
                    navController,
                    simNumber,
                    scope,
                    snackbarHostState
                )
            }
            //when returning from portfolio screen
            else {
                SimulatorCard(
                    user,
                    bonds = TestData.testDataList,
                    navController,
                    simNumber,
                    scope,
                    snackbarHostState
                )
            }
        }
    }
}

@Composable
fun SimulatorCard(
    userInfo : UserInfo,
    bonds: List<BondOption>,
    navController: NavController,
    simNumber: MutableIntState,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
) {
    // for traversing bonds list
    var i by remember { mutableIntStateOf(userInfo.month) }
    var numOfBonds by remember { mutableIntStateOf(userInfo.numBonds) }
    var currentBond by remember { mutableStateOf(bonds[i]) }

    var month by remember { mutableIntStateOf(userInfo.month + 1) }
    val currContext = LocalContext.current

    val database = DatabaseBuilder.getDatabase(currContext)
    val dao = database.historyDAO()

    var showAlertDialog by remember { mutableStateOf(false) }
    if (showAlertDialog) {
        val randomInt = Random.nextInt(3, userInfo.investList.size - 1)
        val randomBondTitle = userInfo.investList[randomInt].bondTitle
        ShowAlertDialog(
            randomBondTitle = randomBondTitle,
            onSkip = {
                startPortfolioScreen(navController, userInfo)
                userInfo.defaultRisk(randomInt)
                showAlertDialog = false
            }
        )
    }

    var previousNetWorth by remember { mutableStateOf(userInfo.netWorth) }
    var previousInvestments by remember { mutableStateOf(userInfo.investments) }
    var previousWallet by remember { mutableStateOf(userInfo.wallet) }
    var previousMonthlyReturn by remember { mutableStateOf(userInfo.monthlyReturn) }

    val netWorthPercentDiff = calculatePercentChange(previousNetWorth, userInfo.netWorth)
    val investmentsPercentDiff = calculatePercentChange(previousInvestments, userInfo.investments)
    val walletPercentDiff = calculatePercentChange(previousWallet, userInfo.wallet)
    val monthlyReturnPercentDiff = calculatePercentChange(previousMonthlyReturn, userInfo.monthlyReturn)

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
                // Month Display
                Text(
                    text = "Month $month of 12",
                    fontWeight = FontWeight.ExtraBold,
                    fontSize = 25.sp,
                    color = Color(0xFF8a191f),
                    style = MaterialTheme.typography.titleMedium,
                    //                modifier = Modifier.padding(all = 5.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
                //Portfolio Shopping Cart
                //            IconButton(onClick = { startPortfolioScreen(navController, userInfo) }) {
                //                Icon(Icons.Filled.ShoppingCart, contentDescription = "Investment List")
                //            }

            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                //                        .padding(top = 16.dp)
            ) {
                // First Column (Image 1)
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
                                color = Color.Black,
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodyMedium,
                                modifier = Modifier.padding(start = 10.dp, bottom=1.dp),
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
                                modifier = Modifier.padding(start = 20.dp, bottom=1.dp)
                            )
                            Text(
                                text = "$${userInfo.investments}",
                                color = if (userInfo.investments >= 0) Color(0xff027148) else Color.Red,
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

            //Bond Card
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
////                    .padding(16.dp)
//                    .border(1.5.dp, Color(0xFFDEB841), RectangleShape)
//                    .background(Color.LightGray, RectangleShape) // Set the background color and shape of the box
//            )/
            Column{
                UserCard(
                    bond = bonds[i],
                    navController,
                    numberOfBonds = numOfBonds,
                    onNumberOfBondsChanged = {
                        numOfBonds = it
                    },
                    userInfo = userInfo,
                    onInvestClicked = {

                        // Update previous values
                        previousNetWorth = userInfo.netWorth
                        previousInvestments = userInfo.investments
                        previousWallet = userInfo.wallet
                        previousMonthlyReturn = userInfo.monthlyReturn

                        // increment month
                        month += 1
                        userInfo.incrementMonth()
                        userInfo.wallet += userInfo.monthlyReturn

                        // increment bond
                        i = (i + 1) % bonds.size
                        currentBond = bonds[i]

                        // end simulation, go to history screen
                        if (month == 13) {
                            toastMessages(currContext, "finish")
                            //TODO: Reset user and sim
                            val usrHistory = HistoryItem(
                                netWorth = userInfo.netWorth,
                                wallet = userInfo.wallet,
                                gains = userInfo.totalGains,
                                trades = userInfo.trades
                            )
                            scope.launch {
                                insertHistory(usrHistory, dao)
                            }
                            startHistoryScreen(navController)
                        }

                        // new bond category
                        if (month % 3 == 0) {
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

                        // default risk
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


// Function to calculate percentage change
private fun calculatePercentChange(oldValue: Double, newValue: Double): Double {
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
}

// Function to reset the simulation state
private fun resetSimulation(navController: NavController) {
    // Replace the lines below with the initial values of your UserInfo properties
    val initialWallet = 1000.0
    val initialInvestments = 0.0
    val initialMonthlyReturn = 0.0
    val initialNumBonds = 0
    val initialMonth = 0

    // Reset user data and simulation counters
    val initialUser = UserInfo(
        wallet = initialWallet,
        investments = initialInvestments,
        monthlyReturn = initialMonthlyReturn,
        numBonds = initialNumBonds,
        month = initialMonth
    )

    // Navigate back to the simulator screen with the reset state
    navController.navigate(BottomNavBar.Simulator.route) {
        popUpTo(navController.graph.startDestinationId) {
            saveState = true
        }
        launchSingleTop = true
    }
}

//@Composable
//fun TransparentBox(
//    label: String,
//    value: String,
//    upArrowImageRes: Int,
//    downArrowImageRes: Int
//) {
//    Box(
//        modifier = Modifier
//            .fillMaxWidth(0.5f)
//            .background(color = Color.Transparent)
//            .padding(10.dp),
//        contentAlignment = Alignment.CenterStart
//    ) {
//        // Display label and value
//        Column(
//            horizontalAlignment = Alignment.Start
//        ) {
//            Text(
//                text = label,
//                style = MaterialTheme.typography.bodyMedium,
//                color = Color(0xFFDEB841),
//                textAlign = TextAlign.Start
//            )
//            Spacer(modifier = Modifier.height(2.dp))
//            Text(
//                text = value,
//                style = MaterialTheme.typography.bodyLarge,
//                textAlign = TextAlign.Start
//            )
//        }

// Display arrow image
//        val arrowImageRes = if (value.toDouble() >= 0) upArrowImageRes else downArrowImageRes
//        Image(
//            painter = painterResource(id = arrowImageRes),
//            contentDescription = null,
//            modifier = Modifier
//                .size(24.dp) // Adjust the size as needed
//                .padding(start = 8.dp)
//        )
//    }
//}



@Composable
fun BondCard(
    bond: BondOption,
//    numberOfBonds: Int,
//    onNumberOfBondsChanged: (Int) -> Unit,
    userInfo: UserInfo,
//    onInvestClicked: () -> Unit
) {
    val bondRate = bond.interestRate
    val month = userInfo.month

//    var numBonds by remember { mutableIntStateOf(numberOfBonds) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
//                    .padding(16.dp)
            .border(1.5.dp, Color(0xFFDEB841), RoundedCornerShape(10.dp))
            .background(Color.LightGray, RoundedCornerShape(10.dp)) // Set the background color and shape of the box
    ) {
        Column(
            modifier = Modifier
//                .fillMaxWidth()
                .padding(8.dp),
//            verticalArrangement = Arrangement.Center,
//            horizontalAlignment = Alignment.CenterHorizontally
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
                // First column
                Column(
                    modifier = Modifier
                        .weight(1f)
//                        .padding(start = 5.dp, end = 5.dp)
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
                    WhiteBox("Wallet", "$${userInfo.wallet}")
                }
            }
        }
    }

//            Text(
//                text = "Price: $" + bond.price,
////                    modifier = Modifier.padding(all = 5.dp),
//                color = Color(0xFF08010F)
//            )
//            Text(
//                text = "Interest Rate: % " + bond.interestRate,
////                    modifier = Modifier.padding(all = 5.dp),
//                color = Color(0xFF08010F)
//            )
//            Text(
//                text = "Monthly Return: $" + (bond.price * bond.interestRate / 100),
////                    modifier = Modifier.padding(all = 5.dp),
//                color = Color(0xFF08010F)
//            )
//            Text(
//                text = "Buy # of Bonds: ",
////                    modifier = Modifier.padding(top = 5.dp),
//                color = Color(0xFF08010F)
//            )
//            NumericInputField(
//                value = numBonds,
//                onValueChange = {
//                    onNumberOfBondsChanged(it)
//                }
//            )
//            // Invest Button
//            Button(
////                    modifier = Modifier.padding(all = 5.dp),
////                    modifier = Modifier.align(Alignment.),
//                onClick = {
//                    // add bond to investment list
//                    var bondInfo =
//                        BondInfo(bond.title, bond.price, bond.interestRate, numberOfBonds)
//                    userInfo.investList.add(bondInfo)
//                    Log.d("investList", "{${userInfo.investList.toList()}}") // log new list
//
//                    // update trades, wallet, total monthly ROI, invesments, and net worth list
//                    userInfo.incrementTrades()
//                    userInfo.wallet -= (bond.price * numberOfBonds)
//                    userInfo.monthlyReturn += bondInfo.monthlyReturn
//                    userInfo.investments += bondInfo.investment
//                    userInfo.netWorthList.add(userInfo.netWorth)
//
//                    // note (S.S.): This does not change what the user sees in the input field, but it does allow
//                    // user to keep investing the same number of bonds
//                    numBonds = 0
//                    onInvestClicked()
//                },
//            ) {
//                Text(
//                    text = "Invest",
//                )
//            }
}



@Composable
fun UserCard(
    bond: BondOption,
    navController: NavController,
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
//            verticalAlignment = Alignment.Start,
//            modifier = Modifier.fillMaxWidth()
        ) {
            NumericInputField(
                value = numBonds,
                onValueChange = {
                    onNumberOfBondsChanged(it)
                }
            )
            // Invest Button
            Button(
                //                    modifier = Modifier.padding(all = 5.dp),
                //                    modifier = Modifier.align(Alignment.),
                onClick = {
                    // add bond to investment list
                    var bondInfo =
                        BondInfo(bond.title, bond.price, bond.interestRate, numberOfBonds)
                    userInfo.investList.add(bondInfo)
                    Log.d("investList", "{${userInfo.investList.toList()}}") // log new list

                    // update trades, wallet, total monthly ROI, invesments, and net worth list
                    userInfo.incrementTrades()
                    userInfo.wallet -= (bond.price * numberOfBonds)
                    userInfo.monthlyReturn += bondInfo.monthlyReturn
                    userInfo.investments += bondInfo.investment
                    userInfo.netWorthList.add(userInfo.netWorth)

                    // note (S.S.): This does not change what the user sees in the input field, but it does allow
                    // user to keep investing the same number of bonds
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

            IconButton(onClick = { startPortfolioScreen(navController, userInfo) }) {
                Icon(Icons.TwoTone.ShoppingCart, contentDescription = "Investment List", modifier = Modifier.size(50.dp))
            }

        }
//        Text(
//            text = "Investment Value: $${bond.}",
//            color = Color(0xFF8a191f),
//            fontWeight = FontWeight.ExtraBold,
//            fontSize = 15.sp,
//        )
//        Text(
//            text = "Wallet: $${userInfo.wallet}",
//            fontWeight = FontWeight.ExtraBold,
//            fontSize = 15.sp,
//            color = Color(0xFF8a191f),
//            style = MaterialTheme.typography.titleMedium,
//            modifier = Modifier.padding(all = 5.dp),
//            textAlign = TextAlign.Center
//        )
//
//        Text(
//            text = "Investments: $${
//                userInfo.investments
//            }",
//            color = Color(0xFF8a191f),
//            fontWeight = FontWeight.ExtraBold,
//            fontSize = 15.sp,
//            style = MaterialTheme.typography.titleMedium,
//            modifier = Modifier.padding(all = 5.dp),
//            textAlign = TextAlign.Center
//        )
//
//        Text(
//            text = "Net Worth: $${
//                userInfo.netWorth
//            }",
//            color = Color(0xFF8a191f),
//            fontWeight = FontWeight.ExtraBold,
//            fontSize = 15.sp,
//            style = MaterialTheme.typography.titleMedium,
//            modifier = Modifier.padding(all = 5.dp),
//            textAlign = TextAlign.Center
//        )
//
//        Text(
//            text = "Monthly Return: $${
//                userInfo.monthlyReturn
//            }",
//            color = Color(0xFF8a191f),
//            fontWeight = FontWeight.ExtraBold,
//            fontSize = 15.sp,
//            style = MaterialTheme.typography.titleMedium,
//            modifier = Modifier.padding(all = 5.dp),
//            textAlign = TextAlign.Center
//        )

    }
}


@Composable
fun NumericInputField(value: Int, onValueChange: (Int) -> Unit) {
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
        )
    }
}

object TestData{
    val testDataList = listOf(
        BondOption(
            title = "Treasury Bill",
            img = R.drawable.treasuries,
            price = 100.00,
            interestRate = 2.00
        ),
        BondOption(
            title = "Treasury Note",
            img = R.drawable.treasuries,
            price = 200.00,
            interestRate = 3.00,
        ),
        BondOption(
            title = "Treasury Bond",
            img = R.drawable.treasuries,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "Apple",
            img = R.drawable.apple,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "Twitter",
            img = R.drawable.twitter,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "AT&T",
            img = R.drawable.att,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "FTX",
            img = R.drawable.fedinterestrateup,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "Asset Backed",
            img = R.drawable.assetbacked,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "TIPS",
            img = R.drawable.tips,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "Municipal Bond",
            img = R.drawable.mutualfunds,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "Munis",
            img = R.drawable.munis,
            price = 500.00,
            interestRate = 0.5,
        ),
        BondOption(
            title = "ETFs",
            img = R.drawable.etf,
            price = 500.00,
            interestRate = 0.5,
        ),
    )
}

@Composable
private fun ShowDialog (
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

//TODO: Replace Toast Msg with Dialog Boxes 2in Final
private fun toastMessages(context: Context, flg:String) {
    when (flg) {
        "reset" -> Toast.makeText(context, "Simulation Reset", Toast.LENGTH_LONG).show()
        "finish" -> Toast.makeText(context, "Simulation Complete, View Portfolio", Toast.LENGTH_LONG).show()
        "newBond" -> Toast.makeText(context, "New Bond!", Toast.LENGTH_LONG).show()
    }
}

fun startHistoryScreen(navController:NavController){
    navController.navigate(BottomNavBar.History.route)
}

fun startPortfolioScreen(navController: NavController, portfolio: UserInfo){
    navController.currentBackStackEntry?.savedStateHandle?.set("portfolio", portfolio)
    navController.navigate(InternalNav.Portfolio.route)
}

suspend fun insertHistory(item:HistoryItem, dao:HistoryDAO){
    dao.insert(item)
}