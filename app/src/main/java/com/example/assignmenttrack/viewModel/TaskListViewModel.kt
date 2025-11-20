package com.example.assignmenttrack.viewModel

import androidx.lifecycle.ViewModel
import com.example.assignmenttrack.Data.TaskList
import com.example.assignmenttrack.Model.Task
import com.example.assignmenttrack.uiStateData.TaskListUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TaskListViewModel: ViewModel(){
//    mutable backing property for tasklist
    private val _taskState = MutableStateFlow(TaskListUiState())
//    Backing Property for tasklist (buat exposed immutable data ke UI)
    val taskState: StateFlow<TaskListUiState> = _taskState.asStateFlow()

    init {
        loadTasks()
    }

    private fun loadTasks() {
        _taskState.update {
            it.copy(tasks = TaskList)
        }
    }

    fun addTask(task: Task) {
        _taskState.update { current ->
            current.copy(tasks = current.tasks + task)
        }
    }

    fun deleteTask(task: Task) {
        _taskState.update { current ->
            current.copy(tasks = current.tasks.filterNot { it.id == task.id })
        }
    }

    fun setTaskStatus(task: Task) {
        _taskState.update { current ->
            current.copy(
                tasks = current.tasks.map {
                    if (it.id == task.id) it.copy(status = !it.status) else it
                }
            )
        }
    }

    fun completeTask(task: Task) {
        deleteTask(task)
        setTaskStatus(task)
    }
}