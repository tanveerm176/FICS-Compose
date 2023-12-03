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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.fics_compose.BottomNavBar
import com.example.fics_compose.InternalNav
import com.example.fics_compose.ui.theme.FICSComposeTheme
import com.example.fics_compose.usrInfo

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PortfolioTopAppBar(user: usrInfo?, navController:NavController) {
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
                },

                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                            if (user != null) {
                                Log.d("current invest-list","${user.investList.toList()}")
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
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
    if (user != null) {
        PortfolioList(portfolio = user.investList, user)
    }
}


@Composable
fun PortfolioList(portfolio: MutableList<List<Any>>, user: usrInfo) {
//    var portfolio = remember { mutableIntStateOf<portfolio>()}

    LazyColumn {
        itemsIndexed(portfolio) {index, bondPurchased ->
            PortfolioCard(bondPurchased, index, user)
        }
    }
}

@Composable
fun PortfolioCard(bondPurchased: List<Any>, index:Int, user: usrInfo){

    val bondTitle:String = bondPurchased[0].toString()
    val bondPrice:Double = bondPurchased[1].toString().toDouble()
    val bondRate = bondPurchased[2].toString().toDouble()
    val numBonds = bondPurchased[3].toString().toDouble()

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
                            color = Color.Black,
                            style = MaterialTheme.typography.titleMedium,

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
                            user.investList.removeAt(index)

                            Log.d("bondSold","Bond Sold: $bondTitle at index $index")

                            Log.d("sellBond","{${user.investList.toList()}}")
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

//fun sellBond(user: usrInfo, bondTitle: String){
////    user.investList
//    //change wallet, networth, and investments if sell button clicked
//}

@Preview
@Composable
fun previewPortfolio(){

    val testList: List<List<Any>> = listOf(listOf("Treasury Notes", 100.0, 2.0, 5.0),listOf("Treasury Notes", 100.0, 2.0, 5.0),listOf("Treasury Notes", 100.0, 2.0, 5.0))

    FICSComposeTheme {
//        PortfolioScreen(testList)

    }
}
