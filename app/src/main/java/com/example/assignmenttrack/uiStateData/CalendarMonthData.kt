package com.example.assignmenttrack.uiStateData

data class CalendarMonthData(
    val daysInMonth: Int,
    val offset: Int,
    val previousMonthStartDay: Int,
    val remainingCells: Int
)
