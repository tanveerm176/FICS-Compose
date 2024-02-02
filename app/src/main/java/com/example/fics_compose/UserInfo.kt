package com.example.fics_compose

import android.os.Parcelable
import androidx.compose.runtime.MutableDoubleState
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import com.example.fics_compose.ScreenData.BondData
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

/**
 * Data class holding info on Bond invested, including the number of bonds
 * bought, price, interest rate, etc.
 */
@Parcelize
data class BondInfo(
    val bondTitle: String,
    var bondPrice: Double,
    var interestRate: Double,
    val numberOfBonds: Int,
) : Parcelable {
    val monthlyReturn: Double
        get() = numberOfBonds * bondPrice * (interestRate/100)
    val investment: Double
        get() = numberOfBonds * bondPrice
}

const val initialWallet = 10000.00

/**
 * Class to hold User Portfolio info, including current wallet,
 * net worth, total investments, total monthly ROI, and total number of trades
 */
class UserInfo(
    var wallet: MutableDoubleState = mutableDoubleStateOf(initialWallet),
    var investments: MutableDoubleState = mutableDoubleStateOf( 0.0),
    var monthlyReturn: MutableDoubleState = mutableDoubleStateOf(0.0),
    var numBonds: MutableIntState = mutableIntStateOf(0),
    var trades: MutableIntState = mutableIntStateOf(0),
    var month: MutableIntState = mutableIntStateOf(0),
) {

    //mutableStateListOf updates lazy column when list is modified
    val investList = mutableStateListOf<BondInfo>()
    val netWorthList = mutableListOf<Double>()

    val netWorth: Double
        get() = wallet.doubleValue + investments.doubleValue
    val totalGains: Double
        get() = netWorth - initialWallet


    fun incrementTrades() {
        trades.intValue += 1
    }

    fun incrementMonth() {
        if(month.intValue < BondData.BondDataList.size - 1) {
            month.intValue += 1
        }
    }

    fun defaultRisk(i: Int) {
        monthlyReturn.doubleValue -= investList[i].monthlyReturn
        investments.doubleValue +=  investList[i].numberOfBonds * investList[i].bondPrice

        investList[i].bondPrice = 0.0
        investList[i].interestRate = 0.0
    }

    // reset user info to default state
    fun reset() {
        wallet.doubleValue = initialWallet
        investments.doubleValue = 0.0
        monthlyReturn.doubleValue = 0.0
        numBonds.intValue = 0
        month.intValue = 0
        trades.intValue = 0
        investList.clear()
        netWorthList.clear()
    }
}