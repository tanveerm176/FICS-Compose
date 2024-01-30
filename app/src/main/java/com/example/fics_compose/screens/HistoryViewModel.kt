package com.example.fics_compose.screens

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fics_compose.DatabaseBuilder
import com.example.fics_compose.HistoryItem
import com.example.fics_compose.initialWallet
import kotlinx.coroutines.launch
import kotlin.math.truncate

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    val historyList = mutableStateListOf<HistoryItemModel>()
    private val database = DatabaseBuilder.getDatabase(getApplication())

    init {
        addToHistory()
    }

    private fun addToHistory() {
        viewModelScope.launch {
            val portfolios = database.historyDAO().getAllPortfolios()
            portfolios.forEach { portfolio ->
                val historyItem = HistoryItemModel(portfolio, calculateROI(portfolio.gains))
                historyList.add(historyItem)
            }
        }
    }

    private fun calculateROI(gains: Double): Double {
        return truncate((gains / initialWallet) * 100.0)
    }
}

data class HistoryItemModel(
    val netWorth: Double,
    val wallet: Double,
    val trades: Int,
    val roi: Double
) {
    constructor(
        historyItem: HistoryItem,
        roi: Double
    ) : this(
        netWorth = historyItem.netWorth,
        wallet = historyItem.wallet,
        trades = historyItem.trades,
        roi = roi
    )
}
