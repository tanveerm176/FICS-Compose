package com.example.fics_compose

import androidx.room.Dao
import androidx.room.*

@Dao
interface HistoryDAO {
    @Insert
    suspend fun insert(historyItem: HistoryItem)

    @Query("SELECT * FROM histories ORDER BY ID DESC")
    suspend fun getAllPortfolios():List<HistoryItem>
}