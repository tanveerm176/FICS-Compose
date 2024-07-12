package com.example.fics_compose.data.dao

import androidx.room.Dao
import androidx.room.*
import com.example.fics_compose.data.dto.HistoryItem

@Dao
interface HistoryDAO {
    @Insert
    suspend fun insert(historyItem: HistoryItem)

    @Query("SELECT * FROM histories ORDER BY ID DESC")
    suspend fun getAllPortfolios(): List<HistoryItem>
}