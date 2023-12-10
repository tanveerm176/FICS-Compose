package com.example.fics_compose

import android.os.Parcelable
import androidx.compose.runtime.mutableStateListOf
import kotlinx.parcelize.Parcelize

/*// class to hold the users portfolio, including wallet, net worth, investments, monthly ROI, number of bonds purchased*/
@Parcelize
class usrInfo(
    var wallet: Double = 10000.00,
    var netWorth: Double = 0.0,
    var investment: Double = 0.0,
    var monthlyReturn: Double = 0.0,
    var totalGains: Double = 0.0,
    var numBonds: Int = 0,
    var trades: Int = 0,
    var month: Int = 0
) : Parcelable {

    //mutableStateListOf updates lazy column when list is modified
    val investList = mutableStateListOf<List<Any>>()

    // functions to calculate users net worth, investments, and monthly ROI
    // note: made numBonds integer for all functions
    // note: updated wallet and investment so that new bonds add on to old bonds
    fun calcNetWorth(wallet: Double, investment: Double): Double {
        this.netWorth =  wallet + investment
        return this.netWorth
    }

    fun calcInvestments(numBonds: Int, bondPrice: Double): Double {
        this.investment +=  numBonds * bondPrice
        return this.investment
    }

    fun monthlyReturn(numBonds: Int, bondPrice: Double, interestRate: Double): Double {
        val interestRateDec: Double = interestRate / 100
        this.monthlyReturn +=  numBonds * bondPrice * interestRateDec
        return this.monthlyReturn
    }

    // update wallet with monthly return
    fun addMonthlyReturn(): Double {
        this.wallet += this.monthlyReturn
        return this.wallet
    }

    // update number of trades made
    fun addNumBonds() : Int{
        this.numBonds += this.numBonds
        return this.numBonds
    }

    fun calcGains(netWorth: Double) : Double{
        this.totalGains = this.netWorth - 10000.00 //note:need to correct
        return this.totalGains
    }

    fun incrementTrades() : Int{
        this.trades += 1
        return this.trades
    }

    fun incrementMonth():Int{
        this.month+=1
        return this.month
    }

    // reset user info to default state
    fun reset(): usrInfo {
        this.wallet = 10000.00
        this.netWorth = 0.0
        this.investment = 0.0
        this.monthlyReturn = 0.0
        this.numBonds = 0
        this.month = 0
        return this
    }
}