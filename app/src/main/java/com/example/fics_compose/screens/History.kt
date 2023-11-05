package com.example.fics_compose.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import java.util.Date

@Composable
fun HistoryScreen() {
    Spacer(modifier = Modifier.height(24.dp))
    InvestmentsList(investments = HistoryData.investmentHistory)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistoryTopAppBar() {
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
            HistoryScreen()
        }
    }
}

data class Investment(val date: String,
                      val finalNetWorth: Int,
                      val initialWallet: Int,
                      val numOfTrades: Int)

@Composable
fun HistoryCard(investment: Investment){
    Row(modifier = Modifier.fillMaxWidth()){
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = investment.date,
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
                            text = "Net Worth $${investment.finalNetWorth}",
                            color = Color.Black,
                            modifier = Modifier.padding(all = 3.dp),
                        );
                        Text(
                            text = "Initial Wallet $${investment.initialWallet}",
                            color = Color.Black,
                            modifier = Modifier.padding(all = 3.dp),
                        );
                        Text(
                            text = "Total Trades: ${investment.numOfTrades}",
                            color = Color.Black,
                            modifier = Modifier.padding(all = 3.dp),
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun InvestmentsList(investments: List<Investment>) {
    LazyColumn {
        items(investments) {investments ->
            HistoryCard(investments)
        }
    }
}

object HistoryData{
    val investmentHistory = listOf(
        Investment(
            date = "1/1/2023",
            finalNetWorth = 10000,
            initialWallet = 1080,
            numOfTrades = 5
        ),
        Investment(
            date = "3/1/2023",
            finalNetWorth = 25100,
            initialWallet = 3000,
            numOfTrades = 6
        )
    )
}

@Preview
@Composable
fun HistoryScreenPreview() {
    FICSComposeTheme {
        HistoryScreen()
        InvestmentsList(HistoryData.investmentHistory)
    }
}