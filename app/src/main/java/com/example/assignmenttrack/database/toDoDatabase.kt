package com.example.assignmenttrack.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.assignmenttrack.model.Task
import com.example.assignmenttrack.model.User

@Database(entities = [Task::class, User::class], version = 1, exportSchema = false)
@TypeConverters(DbConverter::class)
abstract class TodoDatabase: RoomDatabase(){

    companion object{
        const val NAME = "Todo_DB"

        @Volatile // Ensures all threads see the same instance
        private var INSTANCE: TodoDatabase? = null

        fun getDatabase(context: Context): TodoDatabase {
            // Use synchronization to prevent multiple threads from creating the instance
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TodoDatabase::class.java,
                    NAME
                ).build()
                INSTANCE = instance
                instance // return the instance
            }
        }
    }

    abstract fun getUserDao(): UserDao
    abstract fun getTaskDao(): TaskDao
}