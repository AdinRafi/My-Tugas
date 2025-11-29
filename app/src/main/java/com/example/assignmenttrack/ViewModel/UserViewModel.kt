package com.example.assignmenttrack.viewModel

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.assignmenttrack.database.TaskRepository
import com.example.assignmenttrack.database.UserRepository
import com.example.assignmenttrack.model.Task
import com.example.assignmenttrack.model.User
import com.example.assignmenttrack.uiStateData.defaultUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor
    (private val userRepository: UserRepository,
     private val taskRepository: TaskRepository
): ViewModel()
{
    val user: StateFlow<User> = combine(
        userRepository.getUser(),
        taskRepository.getAllTasks()
    ) { userData, tasks ->
        userData?.copy(
            taskCompleted = tasks.count { it.status == true },
            taskLate = tasks.count { it.status == false && it.deadline.isBefore(Instant.now()) },
            taskPending = tasks.count { it.status == null },
            tugasTotal = tasks.count { it.type == Task.TaskType.Tugas },
            kerjaTotal = tasks.count { it.type == Task.TaskType.Kerja },
            belajarTotal = tasks.count { it.type == Task.TaskType.Belajar },
            taskTotal = tasks.size
        ) ?: defaultUser
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = defaultUser
    )

    fun updatePhotoProfile(uri: Uri) {
        viewModelScope.launch {
            val updated = user.value.copy(profilePictureUri = uri.toString())
            userRepository.updateUser(updated)
        }
    }

    fun updateName(name: String) {
        viewModelScope.launch {
            val currentUser = user.value
            val updated = currentUser.copy(name = name)
            userRepository.updateUser(updated)
        }
    }
    // Bug di StatScreen dimana tidak berubah biarpun ada tugas - Medium
    // Bug di Profile biarpun terganti tapi tidak selamanya dan harus ganti screen - Hard
    // Bug di calendar dimana semua tugas pada tanggal yang sama ditandai
    // serta catatan tanggal yang dipencet masih ada - Easy?
}