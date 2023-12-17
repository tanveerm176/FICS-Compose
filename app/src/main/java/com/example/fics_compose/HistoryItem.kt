package com.example.fics_compose

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "histories")
data class HistoryItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val netWorth: Double,
    val wallet: Double,
    val gains: Double,
    val trades: Int
)
