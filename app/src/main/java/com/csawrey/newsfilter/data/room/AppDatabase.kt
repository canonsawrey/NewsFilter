package com.csawrey.newsfilter.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DatabaseSearchItem::class, StoredQueryResultItem::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun searchItemsDao(): SearchItemsDao
    abstract fun queryResultItemDao(): QueryResultItemsDao

    companion object {
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                INSTANCE = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "app.db")
                    .build()
            }
            return INSTANCE!!
        }
    }
}