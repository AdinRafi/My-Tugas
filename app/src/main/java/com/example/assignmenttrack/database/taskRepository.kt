package com.example.assignmenttrack.database

import com.example.assignmenttrack.model.Task
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZoneId


class TaskRepository(private val taskDao: TaskDao) {
    fun getAllTasks(): Flow<List<Task>> = taskDao.getAllTasks()

    suspend fun insertTask(task: Task) = taskDao.insertTask(task)

    suspend fun deleteTask(taskId: Int) = taskDao.deleteTask(taskId)

    suspend fun completeTask(taskId: Int) = taskDao.completeTask(taskId)

    fun getTasksByMonth(month: Int, year: Int): Flow<List<Task>> {
        val monthStr = "%02d".format(month)
        val yearStr = year.toString()
        return taskDao.getTasksByMonth(monthStr, yearStr)
    }

    fun getTasksByDate(date: Long): Flow<List<Task>> {
        return taskDao.getTasksByDate(date)
    }
}