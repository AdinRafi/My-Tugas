package com.example.assignmenttrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.assignmenttrack.ui.theme.AssignmentTrackTheme
import java.time.Instant

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AssignmentTrackTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainLayout(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
@Composable
fun MainLayout(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color(0xFFCAD6FF))
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ProfileSection(name = "Faiz")
            TaskListScreen()
        }
        AddTask(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = 8.dp)
        )
    }
}


// handle showing list of card task (emg nge lag pas masih debug)
@Composable
fun TaskListScreen(modifier: Modifier = Modifier) {
    var taskList by remember { mutableStateOf(TaskList) }

    LazyColumn(
        modifier = modifier.padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 8.dp)
    ){
        items(items = taskList, key = { task -> task.id }) { task ->
            TaskCard(task)
            Spacer(Modifier.height(16.dp))
        }

    }
}

// single card task
@Composable
fun TaskCard(task: Task) {
    Card(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .fillMaxWidth()
            .background(Color.White),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 8.dp, bottom = 16.dp, top = 8.dp)
        ){
            Text(
                text = task.deadline.toString(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 4.dp, top = 4.dp, bottom = 16.dp),
                color = Color(0xFF2260FF),
                fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
            )

            Column(
                modifier = Modifier
                    .clip(CardDefaults.shape)
                    .background(color = Color(0xFFCAD6FF))
                    .fillMaxWidth(0.85f)
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 24.dp),
            ) {
                Text(text = task.type, style = MaterialTheme.typography.titleLarge, color = Color(0xFF2260FF))

                Spacer(Modifier.height(4.dp))

                Text(text = task.title, style = MaterialTheme.typography.titleMedium, color = Color.Black)

                Spacer(Modifier.height(8.dp))

                Text(text = task.description, style = MaterialTheme.typography.bodySmall, color = Color.Black)
            }
        }
    }
}



// Profil (bagian atas di dashboard)
@Composable
fun ProfileSection(name: String) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.12f)
            .background(color = Color.White),
        Arrangement.SpaceBetween
    ){
        Box (){  }

        // bagian buat show nama
        Box(
            modifier = Modifier
                .padding(16.dp)
                .background(color = Color.LightGray)
                .padding(16.dp)
        ) {
            Text(text = "Hello $name", color = Color.Black)
        }

        // setting button/notif (kgk tau buat apaan pakek aja dlu)
        Button(
            onClick = { /* Handle button click */ },
            modifier = Modifier
                .padding(16.dp)
                .size(width = 120.dp, height = 65.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.LightGray
            )
        ){
            Text(text = "Button (dummy)", color = Color.Black)
        }
    }
}

@Composable
fun AddTask(modifier: Modifier = Modifier){
    Button(
        onClick = {/* handle click */},
        modifier = modifier
            .padding(bottom = 24.dp) // Add padding to lift it from the absolute bottom
            .size(width = 220.dp, height = 40.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF2260FF)
        )
    ) {
        Text(
            text = "+ Tambah Tugas",
            color = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    AssignmentTrackTheme {
        TaskListScreen(modifier = Modifier)
    }
}
