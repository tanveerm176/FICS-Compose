package com.example.fics_compose.presentation.screendata

import com.example.fics_compose.data.dto.HistoryItem

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
