package com.example.assignmenttrack.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.assignmenttrack.data.Task
import java.time.format.DateTimeFormatter

// single card task
@Composable
fun TaskCard(task: Task) {
    // Define a formatter for the deadline
    val formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy")

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp)), // Apply clip to the Card itself for proper shadow rendering
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 8.dp, bottom = 16.dp, top = 8.dp)
        ){
            Text(
                text = task.deadline.toString(),
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 4.dp, top = 4.dp, bottom = 16.dp),
                color = Color(0xFF2260FF),
                fontWeight = FontWeight.SemiBold
            )

            Column(
                modifier = Modifier
                    .fillMaxWidth(0.85f)
                    .clip(RoundedCornerShape(16.dp)) // Use a more specific shape for the inner column
                    .background(color = Color(0xFFCAD6FF))
                    .padding(start = 16.dp, top = 8.dp, end = 16.dp, bottom = 24.dp),
            ) {
                Text(text = task.type, style = MaterialTheme.typography.titleLarge, color = Color(0xFF2260FF))

                Spacer(modifier = Modifier.height(4.dp))

                Text(text = task.title, style = MaterialTheme.typography.titleMedium, color = Color.Black)

                Spacer(Modifier.height(8.dp))

                Text(text = task.description, style = MaterialTheme.typography.bodySmall, color = Color.Black)
            }
        }
    }
}
