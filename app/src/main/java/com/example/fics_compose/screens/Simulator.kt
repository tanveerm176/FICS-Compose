package com.example.fics_compose.screens

import android.R.attr.value
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


data class BondOption(val title: String, val price: Double, val interest: Double)

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
        )
        Text(
            text = "Interest Rate: $" + bond.interest,
            modifier = Modifier.padding(all = 5.dp),
        )
        Text(
            text = "Enter quantity: ",
            modifier = Modifier.padding(top = 5.dp),
        )
        var numericValue by remember { mutableIntStateOf(0) }
        NumericInputField(
            value = numericValue,
            onValueChange = {numericValue = it}
        )
    }
}

@Composable
// Simulator screen with bond defulat value
fun SimulatorScreen(wallet: Double, netWorth: Double, bonds: BondOption = BondOption("US Treasury Bond", 24.50, 3.5)) {
    Spacer(modifier = Modifier.width(8.dp))

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Timer()

        Spacer(modifier = Modifier.height(8.dp))

        Surface(
            shape = MaterialTheme.shapes.medium,
            shadowElevation = 5.dp,
            color = Color(0xffbfbdc1)
        ) {
            BondCard(bond = bonds)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            modifier = Modifier
                .padding(all = 5.dp)
                .align(Alignment.CenterHorizontally),
            onClick = { /*TODO*/ },
        ) {
            Text(
                text = "Invest",
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Column {
            Text(
                text = "Wallet: $$wallet",
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp,
                //add color to text
                color = Color(0xFFDEB841),
                //change size of text
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(all = 5.dp),
            )
            Text(
                text = "Net Worth: $$netWorth",
                color = Color(0xFFDEB841),
                fontWeight = FontWeight.ExtraBold,
                fontSize = 15.sp,
                //change size of text
                style = MaterialTheme.typography.titleMedium,
                //add padding to body text
                modifier = Modifier.padding(all = 5.dp),
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

@Preview
@Composable
fun PreviewSimulation() {
    SimulatorScreen(wallet = 120.00, netWorth = 130.00, bonds = BondOption("US Treasury Bond", 24.50, 3.5))
}

// Timer for simulation
@Composable
fun Timer() {
    var month by remember { mutableIntStateOf(1) }
    var isRunning by remember { mutableStateOf(false) }
    var elapsedTime by remember { mutableLongStateOf(0L) }
    var baseTime by remember { mutableLongStateOf(0L) }

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
                            if (elapsedTime >= month * 30000) {
                                month += 1
                            }
                            if (month == 12) {
                                isRunning = false
                                elapsedTime = 0
                                baseTime = System.currentTimeMillis()
                            }
                        }
                    }
                }
            }
        ) {
            Text(text = if (isRunning) "Pause" else "Start")
        }

        Button(
            onClick = {
                isRunning = false
                elapsedTime = 0
                baseTime = System.currentTimeMillis()
                month = 1
            }
        ) {
            Text(text = "Stop")
        }
    }
}

fun formatTime(time: Long): String {
    val seconds = (time / 1000).toInt()
    val minutes = seconds / 60
    val remainingSeconds = seconds % 60
    return "%02d:%02d".format(minutes, remainingSeconds)
}

