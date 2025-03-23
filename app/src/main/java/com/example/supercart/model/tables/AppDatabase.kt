package com.example.supercart.model.tables

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.util.Log

@Database(entities = [User::class, Product::class, CartItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,  // Use applicationContext to avoid leaks
                    AppDatabase::class.java,
                    "cart_database"
                ).allowMainThreadQueries() // Remove in production to avoid UI thread blocking
                    .fallbackToDestructiveMigration()
                    .build()
                instance = newInstance
                Log.d("AppDatabase", "Database Initialized")
                newInstance
            }
        }
    }
}
