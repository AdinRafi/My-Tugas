package com.example.assignmenttrack.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmenttrack.database.TaskRepository
import com.example.assignmenttrack.model.Task
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TaskListViewModel @Inject constructor(private val db: TaskRepository) : ViewModel(){

    // Expose tasks dari repository
    val tasks: Flow<List<Task>> = db.getAllTasks()

    // Tambah task
    fun addTask(task: Task) {
        viewModelScope.launch {
            db.insertTask(task)
        }
    }

    // Hapus task
    fun deleteTask(taskId: Int) {
        viewModelScope.launch {
            db.deleteTask(taskId)
        }
    }

    // Tandai task selesai
    fun completeTask(taskId: Int) {
        viewModelScope.launch {
            db.completeTask(taskId)
        }
    }
}