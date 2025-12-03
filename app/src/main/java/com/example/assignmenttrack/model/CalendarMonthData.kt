package com.example.assignmenttrack.model

data class CalendarMonthData(
    val daysInMonth: Int,
    val offset: Int,
    val previousMonthStartDay: Int,
    val remainingCells: Int
)
