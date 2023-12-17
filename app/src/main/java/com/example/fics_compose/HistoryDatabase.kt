package com.example.fics_compose

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HistoryItem::class/*, NetWorthList::class*/], version=2, exportSchema = false)
abstract class HistoryDatabase:RoomDatabase() {
    abstract fun historyDAO():HistoryDAO
}