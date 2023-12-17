package com.example.fics_compose.screens

import android.R.attr.value
import android.content.Context
import android.util.Log
import android.widget.Toast
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
import com.example.fics_compose.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimulatorTopAppBar(navController: NavController, user: UserInfo? = null) {
    var scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var showHelp = remember { mutableStateOf(false) }
    var simNumber = remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                ),
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier.align(Alignment.CenterStart),
                            text = "FICS Simulator"
                        )
                        Button(
                            onClick = { showHelp.value = true },
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .padding(horizontal = 5.dp)
                        ) {
                            Text(text = "Help")
                        }
                        if (showHelp.value) {
                            ShowDialog(onSkip = {showHelp.value = false}, simNumber.value)
                        }
                    }
                }
            )
            scrollBehavior = scrollBehavior
        },
    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            SimulatorScreen(navController, user, simNumber, scope, snackbarHostState)
        }
    }
}

@Composable
fun SimulatorScreen(
    navController: NavController,
    user: UserInfo? = null,
    simNumber: MutableIntState,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState,
){
    Spacer(modifier = Modifier.height(24.dp))
    //when first starting the sim
    if (user == null) {
        SimulatorCard(UserInfo(), bonds = TestData.testDataList, navController, simNumber, scope, snackbarHostState)
    }
    //when returning from portfolio screen
    else{
        SimulatorCard(user, bonds = TestData.testDataList, navController, simNumber, scope, snackbarHostState)
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

    Spacer(modifier = Modifier.width(8.dp))

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center,
        ) {

            // Month Display
            Text(
                text = "Month $month of 12",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 25.sp,
                color = Color(0xFFDEB841),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(all = 5.dp),
            )

            Spacer(modifier = Modifier.width(8.dp))

            IconButton(onClick = { startPortfolioScreen(navController, userInfo) }) {
                Icon(Icons.Filled.ShoppingCart, contentDescription = "Investment List")
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        //Bond Card
        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 5.dp,
            color = Color(0xffbfbdc1)
            //TODO: Add padding to bond card and account for longer titles
        ) {
            BondCard(
                bond = bonds[i],
                numberOfBonds = numOfBonds,
                onNumberOfBondsChanged = {
                    numOfBonds = it
                },
                userInfo = userInfo,
                onInvestClicked = {
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
        UserCard(userInfo = userInfo)
    }
}

@Composable
fun BondCard(
    bond: BondOption,
    numberOfBonds: Int,
    onNumberOfBondsChanged: (Int) -> Unit,
    userInfo: UserInfo,
    onInvestClicked: () -> Unit,
) {

    var numBonds by remember { mutableIntStateOf(numberOfBonds) }

    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = bond.title,
            color = Color(0xFF8a191f),
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            modifier = Modifier.padding(all = 5.dp),
        )
        Text(
            text = "Price: $" + bond.price,
            modifier = Modifier.padding(all = 5.dp),
            color = Color(0xFF08010F)
        )
        Text(
            text = "Interest Rate: % " + bond.interestRate,
            modifier = Modifier.padding(all = 5.dp),
            color = Color(0xFF08010F)
        )
        Text(
            text = "Monthly Return: $" + (bond.price * bond.interestRate / 100),
            modifier = Modifier.padding(all = 5.dp),
            color = Color(0xFF08010F)
        )
        Text(
            text = "Buy # of Bonds: ",
            modifier = Modifier.padding(top = 5.dp),
            color = Color(0xFF08010F)
        )
        NumericInputField(
            value = numBonds,
            onValueChange = {
                onNumberOfBondsChanged(it)
            }
        )
        // Invest Button
        Button(
            modifier = Modifier.padding(all = 5.dp),
            onClick = {
                // add bond to investment list
                var bondInfo = BondInfo(bond.title, bond.price, bond.interestRate, numberOfBonds)
                userInfo.investList.add(bondInfo)
                Log.d("investList","{${userInfo.investList.toList()}}") // log new list

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
        ) {
            Text(
                text = "Invest",
            )
        }
    }
}

@Composable
fun UserCard(
    userInfo: UserInfo,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Wallet: $${userInfo.wallet}",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 15.sp,
            color = Color(0xFFDEB841),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(all = 5.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Investments: $${
                userInfo.investments
            }",
            color = Color(0xFFDEB841),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 15.sp,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(all = 5.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Net Worth: $${
                userInfo.netWorth
            }",
            color = Color(0xFFDEB841),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 15.sp,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(all = 5.dp),
            textAlign = TextAlign.Center
        )

        Text(
            text = "Monthly Return: $${
                userInfo.monthlyReturn
            }",
            color = Color(0xFFDEB841),
            fontWeight = FontWeight.ExtraBold,
            fontSize = 15.sp,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(all = 5.dp),
            textAlign = TextAlign.Center
        )

    }
}


@Composable
fun NumericInputField(value: Int, onValueChange: (Int) -> Unit) {
    var text by remember { mutableStateOf(value.toString()) }

    BasicTextField(
        value = text,
        onValueChange = {
            if (it.isEmpty() || it.isDigitsOnly()) {
                text = it
                onValueChange(it.toIntOrNull() ?: 0)
            }
        },
        textStyle = LocalTextStyle.current.copy(color = Color.Black),
        modifier = Modifier
            .padding(16.dp)
            .width(80.dp)
            .border(1.5.dp, Color.Gray, shape = RoundedCornerShape(4.dp)),
        singleLine = true,
    )
}

object TestData{
    val testDataList = listOf(
        BondOption(
            title = "Treasury Bills",
            price = 100.00,
            interestRate = 2.00
        ),
        BondOption(
            title = "Treasury Notes",
            price = 200.00,
            interestRate = 3.00
        ),
        BondOption(
            title = "Treasury Bonds",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "Apple",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "Twitter",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "AT&T",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "FTX",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "Asset Backed",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "TIPS",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "Municipal Bond",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "Munis",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "ETFs",
            price = 500.00,
            interestRate = 0.5
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