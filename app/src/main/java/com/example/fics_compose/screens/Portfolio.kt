package com.example.fics_compose.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.fics_compose.ui.theme.FICSComposeTheme
import com.example.fics_compose.usrInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioTopAppBar(user: usrInfo?) {
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
                        Text("Current Portfolio")
                    }
                }
            )
            scrollBehavior = scrollBehavior
        },
    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            if (user != null) {
                PortfolioScreen(user)
            }
        }
    }
}

@Composable
fun PortfolioScreen(user: usrInfo?) {
    Spacer(modifier = Modifier.height(24.dp))
//    addToPortfolio(portfolio)
    if (user != null) {
        PortfolioList(portfolio = user.investList, user)
    }
}

/*@Composable
fun addToPortfolio(portfolio: List<Any>){
    var portfolioList = mutableListOf<Any>()

    if (portfolio != null) {
        portfolioList.add(portfolio)
    }

//    Log.d("portfolioList","${portfolioList[0]}")

    PortfolioList(portfolio = portfolioList)
}*/

@Composable
fun PortfolioList(portfolio: List<List<Any>>, user: usrInfo) {
    LazyColumn {
        items(portfolio) {portfolio ->
            PortfolioCard(portfolio, user)
        }
    }
}

@Composable
fun PortfolioCard(portfolio: List<Any>, user: usrInfo){

    val bondTitle:String = portfolio[0].toString()
    val bondPrice:Double = portfolio[1].toString().toDouble()
    val bondRate = portfolio[2].toString().toDouble()
    val numBonds = portfolio[3].toString().toDouble()

    Row(modifier = Modifier.fillMaxWidth()){
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Spacer(modifier = Modifier.height(6.dp))


            Spacer(modifier = Modifier.height(4.dp))

            Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 8.dp){
                Row(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    Column{
                        Text(
                            text = "Bond Title: $bondTitle",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleMedium
                        );
                        Text(
                            text = "Price: $$bondPrice",
                            color = Color.Black,
                            modifier = Modifier.padding(all = 3.dp),
                        );
                        Text(
                            text = "Interest Rate: %$bondRate",
                            color = Color.Black,
                            modifier = Modifier.padding(all = 3.dp),
                        );
                        Text(
                            text = "Amount Bought: $numBonds",
                            color = Color.Black,
                            modifier = Modifier.padding(all = 3.dp),
                        )
                    }

                    Button(
                        modifier = Modifier.padding(all = 5.dp),
                        onClick = {
                            user.wallet += (bondPrice * numBonds)
                            user.incrementTrades()
                            val idxRemove = portfolio.indexOf(bondTitle)
                            user.investList.removeAt(idxRemove)
                            Log.d("sellBond","{${user.investList}}")
                        }
                    ) {
                        Text(
                            text = "Sell"
                        )

                    }
                }
            }
        }
    }
}

fun sellBond(user: usrInfo, bondTitle: String){
//    user.investList
    //change wallet, networth, and investments if sell button clicked
}

@Preview
@Composable
fun previewPortfolio(){

    val testList: List<List<Any>> = listOf(listOf("Treasury Notes", 100.0, 2.0, 5.0),listOf("Treasury Notes", 100.0, 2.0, 5.0),listOf("Treasury Notes", 100.0, 2.0, 5.0))

    FICSComposeTheme {
//        PortfolioScreen(testList)

    }
}
