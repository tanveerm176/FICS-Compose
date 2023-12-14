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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.example.fics_compose.BondOption
import com.example.fics_compose.BottomNavBar
import com.example.fics_compose.InternalNav
import com.example.fics_compose.usrInfo


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimulatorTopAppBar(navController: NavController, user: usrInfo? = null) {
    var scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var showHelp = remember { mutableStateOf(false) }
    var simNumber = remember { mutableIntStateOf(0) }

    Scaffold(
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
            SimulatorScreen(navController, user, simNumber)
        }
    }
}

@Composable
fun SimulatorScreen(navController: NavController, user: usrInfo? = null, simNumber: MutableIntState){
    Spacer(modifier = Modifier.height(24.dp))
//    ShowDialog(onSkip = {})
    if (user == null) { //when first starting the sim
        SimulatorCard(usrInfo(), bonds = TestData.testDataList, navController, simNumber)
    }
    else{ //when returning from portfolio screen
        SimulatorCard(user, bonds = TestData.testDataList, navController, simNumber)
    }
}

@Composable
fun SimulatorCard(
    userInfo : usrInfo,
    bonds: List<BondOption>,
    navController: NavController,
    simNumber: MutableIntState
) {
    // for traversing bonds list
    var i by remember { mutableIntStateOf(userInfo.month) }
    var numOfBonds by remember { mutableIntStateOf(userInfo.numBonds) }
    var currentBond by remember { mutableStateOf(bonds[i]) }

    // Note (SS): removed timer functionality and associated variables
    var month by remember { mutableIntStateOf(userInfo.month+1) }
    val currContext = LocalContext.current

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

            IconButton(onClick = { startPortfolioScreen(navController,userInfo)}) {
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
                    // Update the month and current bond
                    month += 1
                    userInfo.incrementMonth()
                    i = (i + 1) % bonds.size
                    currentBond = bonds[i]
                    if (month == 13) {
                        toastMessages(currContext, "finish")
                        //TODO: Reset user and sim

                        //note:START HISTORY SCREEN WHEN SIM FINISHES
                        startHistoryScreen(navController, userInfo)
                    }
                    if (month % 3 == 0) {
                        simNumber.value += 1
                    }
                }
            )
        }

        Spacer(modifier = Modifier.height(8.dp))


        //User Portfolio Info
        //TODO: Create Card Around User Portfolio --> Better UI
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Wallet: $${userInfo.wallet}",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp,
                //add color to text
                color = Color(0xFFDEB841),
                //change size of text
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(all = 5.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Investments: $${
                    userInfo.calcInvestments(
                        userInfo.numBonds,
                        bonds[i].price
                    )
                }",
                color = Color(0xFFDEB841),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp,
                //change size of text
                style = MaterialTheme.typography.titleMedium,
                //add padding to body text
                modifier = Modifier.padding(all = 5.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Net Worth: $${
                    userInfo.calcNetWorth(
                        userInfo.wallet,
                        userInfo.investment
                    )
                }",
                color = Color(0xFFDEB841),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp,
                //change size of text
                style = MaterialTheme.typography.titleMedium,
                //add padding to body text
                modifier = Modifier.padding(all = 5.dp),
                textAlign = TextAlign.Center
            )

            Text(
                text = "Monthly Return: $${
                    userInfo.monthlyReturn(
                        userInfo.numBonds,
                        bonds[i].price,
                        bonds[i].interestRate
                    )
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
}

@Composable
fun BondCard(
    bond: BondOption,
    numberOfBonds: Int,
    onNumberOfBondsChanged: (Int) -> Unit,
    userInfo: usrInfo,
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
        // Note (S.S.): Moved Invest Button here for smoother functioning
        Button(
            modifier = Modifier
                .padding(all = 5.dp),
            onClick = {
                // decrease price of bond from wallet
                userInfo.wallet -= (bond.price * numberOfBonds)
                // update monthlyReturn, investment, and net worth
                userInfo.monthlyReturn(numberOfBonds, bond.price, bond.interestRate)
                userInfo.calcInvestments(numberOfBonds, bond.price)
                userInfo.calcNetWorth(userInfo.wallet, userInfo.investment)

                // note (S.S.): This does not change what the user sees in the input field, but it does allow
                //  user to keep investing the same number of bonds
                numBonds = 0
                userInfo.incrementTrades()

                var bondInfo: List<Any> = mutableListOf(bond.title,bond.price,bond.interestRate, numberOfBonds.toDouble())
                userInfo.investList.add(bondInfo)
                Log.d("investList","{${userInfo.investList.toList()}}")
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

//add 12 instances of data class for each month, 3 for testing for now
//data class setup for testing with Sowjan's timer functionality
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
                    // Display the next dialog content
                    currentDialogIndex--
                }
            },
            currentDialogIndex
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

fun startHistoryScreen(navController:NavController, portfolio:usrInfo){
    navController.currentBackStackEntry?.savedStateHandle?.set("port",portfolio)
    navController.navigate(BottomNavBar.History.route)
}

fun startPortfolioScreen(navController: NavController, portfolio: usrInfo){
    navController.currentBackStackEntry?.savedStateHandle?.set("portfolio", portfolio)
    navController.navigate(InternalNav.Portfolio.route)
}