package com.example.fics_compose

import android.os.Parcelable
import androidx.compose.runtime.mutableStateListOf
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
@Parcelize
class UserInfo(
    var wallet: Double = initialWallet,
    var investments: Double = 0.0,
    var monthlyReturn: Double = 0.0,
    var numBonds: Int = 0,
    var trades: Int = 0,
    var month: Int = 0,
) : Parcelable {

    //mutableStateListOf updates lazy column when list is modified
    val investList = mutableStateListOf<BondInfo>()
    val netWorthList = mutableListOf<Double>()

    val netWorth: Double
        get() = wallet + investments
    val totalGains: Double
        get() = netWorth - initialWallet


    fun incrementTrades() : Int{
        this.trades += 1
        return this.trades
    }

    fun incrementMonth():Int{
        this.month+=1
        return this.month
    }

    fun defaultRisk(i: Int) {
        this.monthlyReturn -= investList[i].monthlyReturn
        this.investments +=  investList[i].numberOfBonds * investList[i].bondPrice

        investList[i].bondPrice = 0.0
        investList[i].interestRate = 0.0
    }

    // reset user info to default state
    fun reset(): UserInfo {
        this.wallet = initialWallet
        this.investments = 0.0
        this.monthlyReturn = 0.0
        this.numBonds = 0
        this.month = 0
        this.trades = 0
        this.totalGains
        this.netWorth
        this.investList.clear()
        this.netWorthList.clear()
        return this
    }
}