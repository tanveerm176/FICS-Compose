package com.example.fics_compose.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.fics_compose.data.dao.HistoryDAO
import com.example.fics_compose.data.dto.HistoryItem

@Database(entities = [HistoryItem::class], version = 1, exportSchema = false)
abstract class HistoryDatabase : RoomDatabase() {
    abstract fun historyDAO(): HistoryDAO
}