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
//                        if (showHelp.value) {
//                            ShowDialog(onSkip = {showHelp.value = false})
//                        }
                    }
                }
            )
            scrollBehavior = scrollBehavior
        },
    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            SimulatorScreen(navController, user)
        }
    }
}

@Composable
fun SimulatorScreen(navController: NavController, user: usrInfo? = null){
    Spacer(modifier = Modifier.height(24.dp))
//    ShowDialog(onSkip = {})
    if (user == null) { //when first starting the sim
        SimulatorCard(usrInfo(), bonds = TestData.testDataList, navController)
    }
    else{ //when returning from portfolio screen
        SimulatorCard(user, bonds = TestData.testDataList, navController)
    }
}

@Composable
fun SimulatorCard(
    userInfo : usrInfo,
    bonds: List<BondOption>,
    navController: NavController
) {
    // for traversing bonds list
    var i by remember { mutableIntStateOf(userInfo.month) }
    var numOfBonds by remember { mutableIntStateOf(userInfo.numBonds) }
    var currentBond by remember { mutableStateOf(bonds[i]) }

    // Note (SS): removed timer functionality and associated variables
    var month by remember { mutableIntStateOf(userInfo.month) }
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
                    if (month == 12) {
                        toastMessages(currContext, "finish")
                        //TODO: Reset user and sim

                        //note:START HISTORY SCREEN WHEN SIM FINISHES
                        startHistoryScreen(navController, userInfo)
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

/*
fun formatTime(time: Long): String {
    val seconds = (time / 1000).toInt()
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}
*/


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

/*// Note (S.S.): For some reason, when the help button is pressed, the dialog shows up with the text
// slightly bigger. Not sure why.
@Composable
private fun ShowDialog (
    onSkip: () -> Unit,
){
    var showDialog by remember { mutableStateOf(true) }
    var currentDialogIndex by remember { mutableIntStateOf(0) }
    val dialogs = listOf(
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

    if (currentDialogIndex < dialogs.size) {
        val currentDialogContent = dialogs[currentDialogIndex]

        SimulatorDialog(
            showDialog = showDialog,
            onDismissRequest = {
                onSkip()
                showDialog = false // Dismiss the dialog
            },
            onConfirmation = {
                if (currentDialogIndex < dialogs.size - 1) {
                    // Display the next dialog content
                    currentDialogIndex++
                } else {
                    // If it's the last dialog, close the dialog
                    showDialog = false
                }
            },
            title = currentDialogContent.title,
            info = currentDialogContent.info
        )
    }
}*/

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