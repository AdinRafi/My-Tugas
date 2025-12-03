package com.example.assignmenttrack.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.assignmenttrack.model.Task
import com.example.assignmenttrack.ui.components.EditTaskForm
import com.example.assignmenttrack.viewModel.TaskListViewModel


@Composable
fun EditTaskScreen(onEditSubmit: () -> Unit, taskListViewModel: TaskListViewModel = hiltViewModel(), oldTask: Task) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.White) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            Spacer(modifier = Modifier.height(16.dp))
            EditTaskForm(oldTask = oldTask, taskListViewModel = taskListViewModel, onEditSubmit = onEditSubmit)
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
