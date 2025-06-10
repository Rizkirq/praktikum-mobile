package com.example.BookApp.di

import android.app.Application
import androidx.room.Room
import com.example.BookApp.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import com.example.BookApp.local.BookDatabase

@Module
@InstallIn(SingletonComponent::class)
object PersistanceModule {
    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): BookDatabase {
        return Room.databaseBuilder(
            application.applicationContext,
            BookDatabase::class.java,
            "book_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(db: BookDatabase) : UserDao {
        return db.userDao()
    }
}