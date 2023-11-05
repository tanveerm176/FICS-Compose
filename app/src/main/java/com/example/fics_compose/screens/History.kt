package com.example.fics_compose.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.ui.unit.dp
import com.example.fics_compose.ui.theme.FICSComposeTheme
import com.example.fics_compose.usrInfo


//TODO: parcelable can't be persistent, need to create an internal data class and set results to its attr
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryTopAppBar(result: usrInfo?) {
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
                        Text("Portfolio")
                    }
                }
            )
            scrollBehavior = scrollBehavior
        },
    ) {innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)){
            HistoryScreen(result)
        }
    }
}

@Composable
fun HistoryScreen(result: usrInfo?) {
    Spacer(modifier = Modifier.height(24.dp))
    addToPortfolio(result)
}

@Composable
fun addToPortfolio(result: usrInfo?){
    var portfolioList = mutableListOf<usrInfo>() //TODO:

    if (result != null) {
        portfolioList.add(result)
    }

//    Log.d("portfolioList","${portfolioList[0]}")

    InvestmentsList(portfolio = portfolioList)
}

@Composable
fun InvestmentsList(portfolio: List<usrInfo>) {
    LazyColumn {
        items(portfolio) {portfolio ->
            HistoryCard(portfolio)
        }
    }
}


@Composable
fun HistoryCard(portfolio: usrInfo){
    Row(modifier = Modifier.fillMaxWidth()){
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Number of Trades: ${portfolio.numBonds}",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleMedium
            );

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
                            text = "Final Net Worth: $${portfolio.netWorth}",
                            color = Color.Black,
                            modifier = Modifier.padding(all = 3.dp),
                        );
                        Text(
                            text = "Remaining Wallet $${portfolio.wallet}",
                            color = Color.Black,
                            modifier = Modifier.padding(all = 3.dp),
                        );
                        Text(
                            text = "Total Gains: ${portfolio.calcGains(portfolio.netWorth)}",
                            color = Color.Black,
                            modifier = Modifier.padding(all = 3.dp),
                        )
                    }
                }
            }
        }
    }
}


