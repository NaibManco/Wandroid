package com.naib.wandroid.base.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [], version = 1)
abstract class AppDatabase private constructor() : RoomDatabase() {

    companion object {
        private var instance: AppDatabase? = null

        @Synchronized
        fun get(context: Context): AppDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(context, AppDatabase::class.java, "wan.db")
                    .fallbackToDestructiveMigration()
                    .build()
            }
            return instance!!
        }
    }
}