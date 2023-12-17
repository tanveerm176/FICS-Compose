package com.example.fics_compose

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryItem::class], version=1, exportSchema = false)
abstract class HistoryDatabase:RoomDatabase() {
    abstract fun historyDAO():HistoryDAO
}