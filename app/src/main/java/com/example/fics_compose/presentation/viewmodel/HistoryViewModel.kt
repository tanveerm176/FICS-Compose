package com.example.fics_compose.presentation.viewmodel

import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.fics_compose.data.db.DatabaseBuilder
import com.example.fics_compose.data.dto.HistoryItem
import com.example.fics_compose.domain.model.initialWallet
import kotlinx.coroutines.launch
import kotlin.math.truncate

class HistoryViewModel(application: Application) : AndroidViewModel(application) {

    val historyViewModelList = mutableStateListOf<HistoryViewModelItem>()
    private val database = DatabaseBuilder.getDatabase(getApplication())

    init {
        addToHistory()
    }

    private fun addToHistory() {
        viewModelScope.launch {
            val portfolios = database.historyDAO().getAllPortfolios()
            portfolios.forEach { portfolio ->
                val historyItem = HistoryViewModelItem(portfolio, calculateROI(portfolio.gains))
                historyViewModelList.add(historyItem)
            }
        }
    }

    private fun calculateROI(gains: Double): Double {
        return truncate((gains / initialWallet) * 100.0)
    }
}

data class HistoryViewModelItem(
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
