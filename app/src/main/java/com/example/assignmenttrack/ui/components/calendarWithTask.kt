package com.example.assignmenttrack.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.assignmenttrack.data.CalendarTask
import java.time.LocalDate
import java.time.YearMonth


private const val CALENDAR_COLUMNS = 7

@Composable
fun CalendarWithTask(
    modifier: Modifier = Modifier,
    calendarInput: List<CalendarTask>,
    year:Int,
    month:Int,
    onDayClick:(Int, Int, Int)->Unit,
    onMonthChange:(Int, Int) -> Unit = {_, _ ->}
    ){

    val yearMonth = YearMonth.of(year, month)
    val daysInMonth = yearMonth.lengthOfMonth()
    val firstDayOfMonth = LocalDate.of(year, month, 1)
    val dayOfWeek = firstDayOfMonth.dayOfWeek.value

    val offset = dayOfWeek - 1
    val previousMonth = yearMonth.minusMonths(1)
    val daysInPreviousMonth = previousMonth.lengthOfMonth()
    val previousMonthStartDay = daysInPreviousMonth - offset + 1

    val totalCells = offset + daysInMonth
    val remainingCells = if (totalCells % 7 == 0) 0 else 7 - (totalCells % 7)

    val months = listOf(
        "January", "February", "March", "April", "May", "June",
        "July", "August", "September", "October", "November", "December"
    )

    val today = LocalDate.now()
    val isCurrentMonth = today.year == year && today.monthValue == month
    val currentDay = if (isCurrentMonth) today.dayOfMonth else null

    fun getPreviousMonth(): Pair<Int, Int> {
        val newMonth = if (month == 1) 12 else month - 1
        val newYear = if (month == 1)  year - 1 else year
        return Pair(newMonth, newYear)
    }

    fun getNextMonth(): Pair<Int, Int> {
        val newMonth = if (month == 12) 1 else month + 1
        val newYear = if (month == 12)  year + 1 else year
        return Pair(newMonth, newYear)
    }

    Column(
        modifier = Modifier
            .background(Color(0xFFCAD6FF)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ){
            IconButton(onClick = {
                val(newMonth, newYear) = getPreviousMonth()
                onMonthChange(newMonth, newYear)
            }){
                Icon(
                    modifier = Modifier
                        .size(32.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
                    tint = Color(0xFF2260FF),
                    contentDescription = "Previous Month"
                )
            }

            Text(
                modifier = Modifier
                    .padding(12.dp),
                text = "${months[month - 1]} $year",
                fontWeight = FontWeight.SemiBold,
                fontSize = 24.sp,
                color = Color(0xFF2260FF),
            )

            IconButton(onClick = {
                val(newMonth, newYear) = getNextMonth()
                onMonthChange(newMonth, newYear)
            }){
                Icon(
                    modifier = Modifier
                        .size(32.dp),
                    imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
                    tint = Color(0xFF2260FF),
                    contentDescription = "Next Month"
                )
            }
        }

        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color.White)
                .padding(16.dp)
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val days = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
                days.forEach { day ->
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(RoundedCornerShape(12.dp))
                            .background(Color(0xFF2260FF))
                            .padding(vertical = 8.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = day,
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp
                        )
                    }
                }
            }

            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                columns = GridCells.Fixed(CALENDAR_COLUMNS),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(offset){ index ->
                    val day = previousMonthStartDay + index
                    val (prevMonth, prevYear) = getPreviousMonth()
                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clickable { onDayClick(day, prevMonth, prevYear) },
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = day.toString(),
                            color = Color.LightGray,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                items(daysInMonth){ index ->
                    val day = index + 1
                    val task = calendarInput.find { it.day == day }
                    val hasTask = task?.task?.any { !it.status } ?: false
                    val isToday = day == currentDay

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .then(
                                when{
                                    hasTask -> Modifier
                                        .clip(CircleShape)
                                        .background(Color(0xFF2260FF))
                                    isToday -> Modifier
                                        .clip(CircleShape)
                                        .background(Color(0xFFFFD700))
                                    else -> Modifier
                                }
                            )
                            .clickable { onDayClick(day, month, year) },
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = day.toString(),
                            color = when {
                                isToday || hasTask -> Color.White
                                else -> Color.Black
                            },
                            fontWeight = when {
                                isToday || hasTask -> FontWeight.Bold
                                else -> FontWeight.Normal
                            },
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }

                items(remainingCells){ index ->
                    val day = index + 1
                    val (nextMonth, nextYear) = getNextMonth()

                    Box(
                        modifier = Modifier
                            .aspectRatio(1f)
                            .clickable { onDayClick(day, nextMonth, nextYear) },
                        contentAlignment = Alignment.Center
                    ){
                        Text(
                            text = day.toString(),
                            color = Color.LightGray,
                            fontSize = 14.sp,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, widthDp = 400, heightDp = 700)
@Composable
fun CalendarPreview() {
    var currentYear by remember { mutableStateOf(2025) }
    var currentMonth by remember { mutableStateOf(11) }
    var selectedDay by remember { mutableStateOf<Triple<Int, Int, Int>?>(null) }

    // Dummy data
    val calendarTasks = listOf(
        CalendarTask(day = 12, task = emptyList()),
        CalendarTask(day = 13, task = emptyList()),
        CalendarTask(day = 15, task = emptyList())
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CalendarWithTask(
            calendarInput = calendarTasks,
            onDayClick = { day, month, year ->
                selectedDay = Triple(day, month, year)
                println("Clicked: $day/$month/$year")
            },
            year = currentYear,
            month = currentMonth,
            onMonthChange = { newMonth, newYear ->
                currentMonth = newMonth
                currentYear = newYear
            }
        )

        // Info tanggal yang diklik
        selectedDay?.let { (day, month, year) ->
            Spacer(modifier = Modifier.height(16.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(Color(0xFFECF1FF))
                    .padding(16.dp)
            ) {
                Text(
                    text = "Selected: $day/${month}/${year}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = Color(0xFF2260FF)
                )
            }
        }
    }
}