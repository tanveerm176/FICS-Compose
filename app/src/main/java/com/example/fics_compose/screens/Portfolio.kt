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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.fics_compose.BondInfo
import com.example.fics_compose.InternalNav
import com.example.fics_compose.usrInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

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
                            if (user != null) {
                                returnToSimulator(navController, user)
                            }
                            if (user != null) {
                                Log.d("current invest-list", "${user.investList.toList()}")
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

        ) { innerPadding ->
        if (user != null) {
            Column(modifier = Modifier.padding(innerPadding)) {

                userCard(user = user)
                PortfolioScreen(user)

            }
        }
    }
}


@Composable
fun userCard(user:usrInfo){
//    Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 8.dp) {
//        Row(
//            modifier = Modifier
//                .background(Color.Black)
//                .fillMaxWidth()
//                .padding(4.dp),
//            verticalAlignment = Alignment.CenterVertically,
//            horizontalArrangement = Arrangement.spacedBy(5.dp)
//        ) {

            Column {
                Text(
                    text = "Wallet: $${user.wallet}",
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
                        user.investment
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
                        user.calcNetWorth(user.wallet,user.investment)
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
                        user.monthlyReturn
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
//    }
    
//}

@Composable
fun PortfolioScreen(user: usrInfo?) {
    if (user != null) {
        PortfolioList(portfolio = user.investList, user)
    }
}


@Composable
fun PortfolioList(portfolio: SnapshotStateList<BondInfo>, user: usrInfo) {
    LazyColumn {
        itemsIndexed(portfolio) {index, bondPurchased ->
            PortfolioCard(bondPurchased, index, user)
        }
    }
}


@Composable
fun PortfolioCard(bondPurchased: BondInfo, index:Int, user: usrInfo){
//    var reloadUserCard by remember {
//        mutableStateOf(false)
//    }
//
//    var reloadUser by remember {
//        mutableStateOf(user)
//    }

//    val bondTitle:String = bondPurchased[0].toString()
//    val bondPrice:Double = bondPurchased[1].toString().toDouble()
//    val bondRate = bondPurchased[2].toString().toDouble()
//    val numBonds = bondPurchased[3].toString().toDouble()

    val bondTitle = bondPurchased.bondTitle
    val bondPrice = bondPurchased.bondPrice
    val bondRate = bondPurchased.interestRate
    val numBonds = bondPurchased.numberOfBonds

    Log.d("bond","Bond Price: $bondPrice at interest rate $bondRate")
    val cardColor = if (bondPrice == 0.0 && bondRate == 0.0) {
        Color(android.graphics.Color.parseColor("#FF7F7F"))
    } else {
        Color.LightGray
    }

//
//    if (reloadUserCard){
//        userCard(user = reloadUser)
//    }
    Row(modifier = Modifier.fillMaxWidth()){
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Spacer(modifier = Modifier.height(6.dp))


            Spacer(modifier = Modifier.height(4.dp))

            Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 8.dp, color = Color.Transparent){
                Row(
                    modifier = Modifier
                        .background(cardColor)
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

                            user.wallet += (bondPrice*numBonds)
                            user.investList.removeAt(index)

                            // TODO: When selling a defaulted bond, display a snackbar or alert box.

//                            val newUser = modifyUser(user,bondPrice,numBonds)
//                            reloadUser = newUser
//                            reloadUserCard = !reloadUserCard

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

fun modifyUser(user: usrInfo, bondPrice:Double, numBonds:Double): usrInfo{
    user.wallet += (bondPrice * numBonds)
    user.incrementTrades()

    return user
}

fun returnToSimulator(navController: NavController, user:usrInfo){
    navController.currentBackStackEntry?.savedStateHandle?.set("user",user)
    navController.navigate(InternalNav.Simulator.route)
}