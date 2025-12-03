package com.example.assignmenttrack.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.assignmenttrack.viewModel.UserViewModel
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.window.Dialog

@Composable
fun ChangeNameDialog(onDismiss: () -> Unit,  userViewModel: UserViewModel){
    val currentUser = userViewModel.user.collectAsState().value.name
    val (currentName, setCurrentName) = remember {  mutableStateOf(currentUser.toString())}

    Dialog(onDismissRequest = onDismiss ){
        Card(
            colors = CardDefaults.cardColors(containerColor = Color.White),
            elevation = CardDefaults.cardElevation(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(220.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(
                    text = "Change Name",
                    fontFamily = FontFamily.SansSerif,
                    modifier = Modifier
                        .padding(bottom = 16.dp),
                    fontWeight = FontWeight.SemiBold,
                    fontSize = androidx.compose.material3.MaterialTheme.typography.titleLarge.fontSize,
                    color = Color(0xFF2260FF)
                )
                TextField(
                    value = currentName,
                    onValueChange = { setCurrentName(it) },
                    singleLine = true,
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = Color.White,
                        unfocusedContainerColor = Color.White,
                        disabledContainerColor = Color.White,
                        errorContainerColor = Color.White,
                        cursorColor = Color.DarkGray,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        focusedTextColor = Color.Black,
                        unfocusedTextColor = Color.DarkGray,
                    ),
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .fillMaxWidth()
                        .border(2.dp, Color(0xFF9EB6FF), shape = RoundedCornerShape(10.dp))
                        .clip(shape = RoundedCornerShape(10.dp)),
                    textStyle = androidx.compose.ui.text.TextStyle(fontWeight = FontWeight.Medium)
                )
                GeneralSubmitButton(
                    onClick = {
                        userViewModel.updateName(currentName)
                        onDismiss()
                    },
                    text = "Change",
                    modifier = Modifier.padding(top = 16.dp)
                )
            }
        }
    }
}