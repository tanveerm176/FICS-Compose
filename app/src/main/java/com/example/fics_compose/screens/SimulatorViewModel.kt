package com.example.fics_compose.screens

import android.app.Application
import android.content.Context
import android.widget.Toast
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.example.fics_compose.HistoryDAO
import com.example.fics_compose.HistoryItem
import com.example.fics_compose.ScreenData.BondData
import com.example.fics_compose.ScreenData.BondOption
import com.example.fics_compose.UserInfo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.random.Random

class SimulatorViewModel(): ViewModel() {
    /*viewModels survive recomposition and reconfigs*/
    val bondsList = BondData.BondDataList

//    var bondsListIndex by mutableIntStateOf(userInfo.month.intValue)
    var simNumber = 0
        private set /*allows access outside of class, but can only be modified inside ViewModel*/

    var showHelpDialog = mutableStateOf(false) /*any changes to a mutableState var causes recomposition*/
        private set

    var showAlertDialog by mutableStateOf(false)

    val snackbarHostState = SnackbarHostState()

    /*val usrHistory = HistoryItem(
        netWorth = userInfo.netWorth,
        wallet = userInfo.wallet.doubleValue,
        gains = userInfo.totalGains,
        trades = userInfo.trades.intValue
    )*/


    fun showHelpDialog(){
        showHelpDialog.value = true
    }

    fun dismissHelpDialog(){
        showHelpDialog.value = false
    }

    fun incrementSimNumber(){
        simNumber++
    }

    fun dismissAlertDialog(){
        showAlertDialog = false
    }

    fun showAlertDialog(){
        showAlertDialog = true
    }

    fun showNewBondSnackBar(scope: CoroutineScope){
        scope.launch {
            val result = snackbarHostState
                .showSnackbar(
                    message = "New Bond Category! Click the help button to learn some key information!",
                    actionLabel = "Dismiss",
                    duration = SnackbarDuration.Short
                )
            when (result) {
                SnackbarResult.ActionPerformed -> {}
                SnackbarResult.Dismissed -> {}
            }
        }
    }

    fun finishSimulation(scope: CoroutineScope, userInfo:UserInfo, dao: HistoryDAO){
        val usrHistory = HistoryItem(
            netWorth = userInfo.netWorth,
            wallet = userInfo.wallet.doubleValue,
            gains = userInfo.totalGains,
            trades = userInfo.trades.intValue
        )
        scope.launch {
            insertHistory(usrHistory, dao)
        }
    }

    private suspend fun insertHistory(item: HistoryItem, dao: HistoryDAO) {
        dao.insert(item)
    }

    fun toastMessages(context: Context, flg: String) {
        when (flg) {
            "reset" -> Toast.makeText(context, "Simulation Reset", Toast.LENGTH_LONG).show()
            "finish" -> Toast.makeText(
                context,
                "Simulation Complete, View Portfolio",
                Toast.LENGTH_LONG
            ).show()

            "newBond" -> Toast.makeText(context, "New Bond!", Toast.LENGTH_LONG).show()
        }
    }

    fun generateRandomBond(userInfo:UserInfo){
        val randomInt = Random.nextInt(0, userInfo.investList.size - 1)
        val randomBondTitle = userInfo.investList[randomInt].bondTitle
    }

    fun calcMonthlyReturn(bond: BondOption): Double{
        return (bond.price * bond.interestRate / 100)
    }


    fun getRandomBond(userInfo: UserInfo): Pair<Int, String>{
        val randInt = Random.nextInt(0, userInfo.investList.size - 1)
        val randBondTitle = userInfo.investList[randInt].bondTitle

        return Pair(randInt, randBondTitle)
    }



}