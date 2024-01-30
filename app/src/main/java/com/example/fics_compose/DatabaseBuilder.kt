package com.example.fics_compose

import android.content.Context
import androidx.room.Room

object DatabaseBuilder {
    private var INSTANCE : HistoryDatabase? = null

    fun getDatabase(context: Context): HistoryDatabase{
        /*prevents two instances of db being created --> single source of truth*/
        return INSTANCE ?: synchronized(this){
            val instance = Room.databaseBuilder(
                context.applicationContext,
                HistoryDatabase::class.java,
                "histories"
            ).build()
            INSTANCE = instance
            instance
        }
    }
}