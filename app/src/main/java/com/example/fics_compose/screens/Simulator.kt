package com.example.fics_compose.screens

import android.R.attr.value
import android.content.Context
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
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.fics_compose.BottomNavBar
import com.example.fics_compose.usrInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serializable



@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimulatorTopAppBar(navController: NavController) {
    var scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.secondary,
                ),
                title = {
                    Box(modifier = Modifier.fillMaxWidth()) {
                        Text("FICS Simulator")
                    }
                }
            )
            scrollBehavior = scrollBehavior
        },
    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            SimulatorScreen(navController)
        }
    }
}

@Composable
fun SimulatorScreen(navController: NavController){
    Spacer(modifier = Modifier.height(24.dp))
    ShowDialog()
    SimulatorCard(usrInfo(), bonds = TestData.testDataList, navController)
}


@Composable
private fun ShowDialog (){
    var showDialog by remember { mutableStateOf(true) }
    var currentDialogIndex by remember { mutableStateOf(0) }
    val dialogs = listOf(
        DialogContent(
            title = "Welcome to the FICS simulation!",
            info = "Click next for some helpful terms for you to know."
        ),
        DialogContent(
            title = "Fixed Income",
            info = "Fixed income is a type of investment that pays the investor a fixed amount on a fixed schedule."
        ),
        DialogContent(
            title = "Bond",
            info = "A bond is a debt security, which means borrowers issue bonds to raise money from investors willing to lend them money for a certain amount of time. \nWhen you buy a bond, you are lending to the issuer, which may be a government, municipality, or corporation. In return, the issuer promises to pay you a specified rate of interest during the life of the bond and to repay the principal, also known as face value or par value of the bond, when it matures or comes due after a set period of time."
        ),
        // Add more dialog content here as needed
    )

    if (currentDialogIndex < dialogs.size) {
        val currentDialogContent = dialogs[currentDialogIndex]

        SimulatorDialog(
            showDialog = showDialog,
            onDismissRequest = {
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
}


data class BondOption(val title: String, val price: Double, val interestRate: Double)

//add 12 instances of data class for each month, 3 for testing for now
//data class setup for testing with Sowjan's timer functionality
object TestData{
    val testDataList = listOf(
        BondOption(
            title = "Treasury Notes",
            price = 100.00,
            interestRate = 2.00
        ),
        BondOption(
            title = "Treasury Bonds",
            price = 200.00,
            interestRate = 3.00
        ),
        BondOption(
            title = "Treasury Securities",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "Federal Financing Bank",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "Domestic Series",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "Special Purpose Vehicle",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "Foreign Series",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "Government Account Series",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "Treasury Bills",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "Treasury Notes",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "Treasury Bonds",
            price = 500.00,
            interestRate = 0.5
        ),
        BondOption(
            title = "Total Marketable",
            price = 500.00,
            interestRate = 0.5
        ),
    )
}


@Composable
fun SimulatorCard(
    userInfo : usrInfo,
    bonds: List<BondOption>,
    navController: NavController
) {
    // for traversing bonds list
    var i by remember { mutableIntStateOf(0) }
    var numOfBonds by remember { mutableIntStateOf(0) }
    var currentBond by remember { mutableStateOf(bonds[i]) }

    // for Timer functionality
    var month by remember { mutableIntStateOf(1) }
    var isRunning by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableLongStateOf(0L) }
    var baseTime by remember { mutableLongStateOf(0L) }
    val currContext = LocalContext.current


    Spacer(modifier = Modifier.width(8.dp))

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        // note: replaced Time and Pause button with Timer function
        // note(S.S): replaced Timer card and added in the core functionalities into SimulationCard

        // Month Display
        Text(
            text = "Month $month of 12",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 25.sp,
            color = Color(0xFFDEB841),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(all = 5.dp),
        )

        // Timer Display
        Text(
            text = formatTime(elapsedTime),
            color = Color(0xffbfbdc1),
            fontSize = 20.sp
        )

        // Timer Buttons (Start/Pause, Restart)
        Row(
            modifier = Modifier.padding(8.dp)
        ) {
            //Start / Pause Button
            Button(
                onClick = {
                    if (isRunning) {
                        isRunning = false
                        baseTime += System.currentTimeMillis() - elapsedTime
                    } else {
                        isRunning = true
                        baseTime = System.currentTimeMillis() - elapsedTime
                        CoroutineScope(Dispatchers.Main).launch {
                            while (isRunning) {
                                elapsedTime = System.currentTimeMillis() - baseTime
                                delay(100)

                                // every month, update bond card and user portfolio
                                if (elapsedTime >= month * 10000 && month < 12) {
                                    month += 1
                                    i += 1
                                    currentBond = bonds[if (i + 1 < bonds.size) i + 1 else 0]
                                    userInfo.addMonthlyReturn()
                                    toastMessages(currContext, "newBond")

                                }
                                if (month == 12) {
                                    isRunning = false
                                    elapsedTime = 0
                                    baseTime = System.currentTimeMillis()
                                    toastMessages(currContext, "finish")

                                    //note:START HISTORY SCREEN WHEN SIM FINISHES
                                    startHistoryScreen(navController,userInfo)
                                }
                            }
                        }
                    }
                }
            ) {
                Text(text = if (isRunning) "Pause" else "Start")
            }

            Spacer(modifier = Modifier.width(4.dp))

            // Restart Button
            Button(
                onClick = {
                    isRunning = false
                    elapsedTime = 0
                    baseTime = System.currentTimeMillis()
                    month = 1
                    userInfo.reset()
                    i = 0
                    currentBond = bonds[0]
                    toastMessages(currContext, "reset")
                }
            ) {
                Text(text = "Restart")
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
                // user to keep investing the same number of bonds
                numBonds = 0
                userInfo.incrementTrades()
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

fun formatTime(time: Long): String {
    val seconds = (time / 1000).toInt()
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}

@Composable
@Preview
fun SimulatorScreenPreview() {
//    SimulatorCard(userInfo = usrInfo(), bonds = TestData.testDataList)
}

//TODO: Replace Toast Msg with Dialog Boxes in Final
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
