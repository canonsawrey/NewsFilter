package com.csawrey.newsstreams.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [DatabaseNewsStream::class, DatabaseSearchItem::class, DatabaseCachedStoryItem::class], version = 1)
abstract class AppDatabase: RoomDatabase() {

    abstract fun newsStreamDao(): NewsStreamDao
    abstract fun searchItemsDao(): SearchItemsDao
    abstract fun cachedResultItemDao(): CachedResultItemsDao

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