package com.example.assignmenttrack.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DataUsage
import androidx.compose.material.icons.outlined.PersonOutline
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import com.example.assignmenttrack.R
import com.example.assignmenttrack.ui.theme.leagueSpartan
import com.example.assignmenttrack.viewModel.UserViewModel
import java.io.File

// Profil (bagian atas di dashboard)
@Composable
fun ProfileSection(viewModel: UserViewModel = hiltViewModel(), onProfileClick: () -> Unit, onStatClick: () -> Unit, onCalendarClick: () -> Unit) {
    val user by viewModel.user.collectAsStateWithLifecycle()

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.12f),
        color = Color.White
    ) {
        Row(
            modifier = Modifier
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // bagian buat show profile pict
            Row(
                modifier = Modifier
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .clip(shape = CircleShape)
                        .size(70.dp)
                        .border(width = 1.dp, color = Color.Black, shape = CircleShape),
                    painter = rememberAsyncImagePainter(
                        model = user.profilePicturePath?.takeIf { it.isNotEmpty() }?.let { File(it) }
                            ?: R.drawable.profile
                    ),
                    contentScale = ContentScale.Crop,
                    contentDescription = ("User Profile"),
                )

                Column {
                    Text(
                        text = "Halo!",
                        color = Color(0xFF2260FF), style = MaterialTheme.typography.titleMedium,
                        fontFamily = leagueSpartan,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.padding(start = 8.dp)
                    )

                    Text(
                        text = user.name,
                        color = Color.Black, style = MaterialTheme.typography.titleLarge,
                        fontFamily = leagueSpartan,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 8.dp)
                    )
                }

            }


            // setting button/notif (kgk tau buat apaan pakek aja dlu)
            Row(
                modifier = Modifier
                    .padding(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                IconButton(
                    onClick = onCalendarClick,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFCAD6FF))
                        .align(Alignment.CenterVertically)
                        .height(40.dp)
                        .width(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.CalendarMonth,
                        contentDescription = "Show Calendar",
                        tint = Color.DarkGray,
                    )
                }

                IconButton(
                    onClick = onStatClick,
                    modifier = Modifier
                        .padding(end = 12.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFCAD6FF))
                        .align(Alignment.CenterVertically)
                        .height(40.dp)
                        .width(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.DataUsage,
                        contentDescription = "Show Stat",
                        tint = Color.DarkGray,
                    )
                }

//              Profile Button
                IconButton(
                    onClick = onProfileClick,
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .clip(CircleShape)
                        .background(Color(0xFFCAD6FF))
                        .align(Alignment.CenterVertically)
                        .height(40.dp)
                        .width(40.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.PersonOutline,
                        contentDescription = "Settings",
                        tint = Color.DarkGray,
                    )
                }
            }
        }
    }
}
