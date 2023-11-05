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
import androidx.compose.foundation.layout.fillMaxHeight
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.Serializable


//class to hold the users portfolio, including wallet, net worth, investments, monthly ROI, number of bonds purchased
data class usrInfo(
    var wallet: Double = 10000.00,
    var netWorth: Double = 0.0,
    var investment: Double = 0.0,
    var monthlyReturn: Double = 0.0,
    var numBonds : Double = 0.0
): Serializable {

    //functions to calculate users net worth, investments, and monthly ROI
    fun calcNetWorth(wallet: Double, investment: Double): Double {
        this.netWorth =  wallet + investment
        return this.netWorth
    }

    fun calcInvestments(numBonds: Double, bondPrice: Double): Double {
        this.investment =  numBonds * bondPrice
        return this.investment
    }

    fun monthlyReturn(numBonds: Double, bondPrice: Double, interestRate: Double): Double {
        val interestRateDec: Double = interestRate / 100
        this.monthlyReturn =  numBonds * bondPrice * interestRateDec
        return this.monthlyReturn
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SimulatorTopAppBar() {
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
            SimulatorScreen()
        }
    }
}

@Composable
fun SimulatorScreen(){
    Spacer(modifier = Modifier.height(24.dp))
    SimulatorCard(usrInfo(), bonds = BondOption("US Treasury Bond", 24.50, 3.5))
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
            title = "Treasury Inflation-Protected Securities (TIPS)",
            price = 500.00,
            interestRate = 0.5
        )
    )
}


@Composable
fun SimulatorCard(userInfo : usrInfo, bonds: BondOption) {
    Spacer(modifier = Modifier.width(8.dp))

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        //note: replaced Time and Pause button with Timer function
        Timer()

        Spacer(modifier = Modifier.height(8.dp))

        //Bond Card
        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 5.dp,
            color = Color(0xffbfbdc1)
            //TODO: Add padding to bond card and account for longer titles
        ) {
            BondCard(bond = bonds)
        }

        Spacer(modifier = Modifier.height(8.dp))

        //Invest Button
        Button(
            modifier = Modifier
                .padding(all = 5.dp)
                .align(Alignment.CenterHorizontally),
            onClick = {
                      /*TODO update NetWorth, Wallet, Investments, NumBonds*/
                      },
        ) {
            Text(
                text = "Invest",
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
                text = "Investments: $${userInfo.calcInvestments(userInfo.numBonds,bonds.price)}",
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
                text = "Net Worth: $${userInfo.calcNetWorth(userInfo.wallet, userInfo.investment)}",
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
                text = "Monthly Return: $${userInfo.monthlyReturn(userInfo.numBonds,bonds.price,bonds.interestRate)}",
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
fun BondCard(bond: BondOption) {
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

        var numericValue by remember { mutableIntStateOf(0) }
        NumericInputField(
            value = numericValue,
            onValueChange = {numericValue = it}
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


@Composable
@Preview
fun SimulatorScreenPreview() {
    SimulatorCard(userInfo = usrInfo(), bonds = BondOption("US Treasury Bond", 24.50, 3.5))
}

//Timer Function for Simulator, allows for Start, Pause, and Restart
//Monthly Increments every 30 seconds until 1 year is complete
@Composable
fun Timer() {
    var month by remember { mutableIntStateOf(1) }
    var isRunning by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableLongStateOf(0L) }
    var baseTime by remember { mutableLongStateOf(0L) }

    val currContext = LocalContext.current

    Text(
        text = "Month $month of 12",
        fontWeight = FontWeight.ExtraBold,
        fontSize = 25.sp,
        color = Color(0xFFDEB841),
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(all = 5.dp),
    )

    Text(
        text = formatTime(elapsedTime),
        color = Color(0xffbfbdc1),
        fontSize = 20.sp
    )

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

                            //TODO: Modify timer from 30 secs -> 20 or 15
                            if (elapsedTime >= month * 30000) {
                                month += 1

                                //TODO: display new bond option from list
                                //TODO: update wallet with monthly return (interest payment from investments)
                            }
                            if (month == 12) {
                                isRunning = false
                                elapsedTime = 0
                                baseTime = System.currentTimeMillis()

                                toastMessages(currContext, "finish")
                                //TODO: send user portfolio to history screen
                                //TODO: display toast message that simulation is over
                                //TODO: start history screen activity

                            }
                        }
                    }
                }
            }
        ) {
            Text(text = if (isRunning) "Pause" else "Start")
        }

        Spacer(modifier = Modifier.width(4.dp))



        //Restart Button
        Button(
            onClick = {
                isRunning = false
                elapsedTime = 0
                baseTime = System.currentTimeMillis()
                month = 1

                toastMessages(currContext, "reset")
                //TODO: add functionality to reset to default
            }
        ) {
            Text(text = "Restart")
        }
    }
}

fun formatTime(time: Long): String {
    val seconds = (time / 1000).toInt()
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}


//TODO: Replace Toast Msg with Dialog Boxes in Final
private fun toastMessages(context: Context, flg:String){
    when(flg){
        "reset" -> Toast.makeText(context, "Simulation Reset", Toast.LENGTH_LONG).show()
        "finish" -> Toast.makeText(context, "Simulation Complete, View Portfolio", Toast.LENGTH_LONG).show()
    }

}

