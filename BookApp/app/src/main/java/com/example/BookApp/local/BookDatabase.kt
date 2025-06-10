package com.example.BookApp.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.BookApp.dao.UserDao
import com.example.BookApp.models.User

@Database(entities = [User::class], version = 2, exportSchema = false)
abstract class BookDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}