package com.example.BookApp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.BookApp.models.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User)

    @Query("SELECT * FROM users WHERE userId = :userId")
    fun getUserById(userId: String): Flow<User?>

    @Query("DELETE FROM users")
    suspend fun deleteAll()
}