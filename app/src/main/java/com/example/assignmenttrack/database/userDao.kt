package com.example.assignmenttrack.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.assignmenttrack.model.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM User WHERE id = 1 LIMIT 1")
    fun getUser(): Flow<User?>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertDefaultUser(user: User)

    @Query("UPDATE User SET name = :name WHERE id = 1")
    suspend fun updateName(name: String)

    @Query("UPDATE User SET profilePicturePath = :profilePicturePath WHERE id = 1")
    suspend fun updatePhotoProfile(profilePicturePath: String)
}