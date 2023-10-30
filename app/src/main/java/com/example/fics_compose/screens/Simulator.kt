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


data class BondOption(val title: String, val price: Double, val interest: Double)

@Composable
fun BondCard(bond: BondOption) {
    Column (
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = bond.title,
            //add color to text
            color = Color(0xFF8a191f),
            //change size of text
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
fun SimulatorScreen(wallet: Double, netWorth: Double, bonds: BondOption) {
    Spacer(modifier = Modifier.width(8.dp))
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "Year 1 of 10",
            fontWeight = FontWeight.ExtraBold,
            fontSize = 25.sp,
            //add color to text
            color = Color(0xFFDEB841),
            //change size of text
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(all = 5.dp),
        )
        Button(
            modifier = Modifier
                .padding(all = 5.dp),
            onClick = { /*TODO*/ },
        ) {
            Text(
                text = "Pause",
            )
        }

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

@Composable
@Preview
fun SimulatorScreenPreview() {
    SimulatorScreen(wallet = 120.00, netWorth = 130.00, bonds = BondOption("US Treasury Bond", 24.50, 3.5))
}