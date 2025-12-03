package com.example.assignmenttrack.model

data class CalendarTask(
    val day: Int,
    val tasks: List<Task>? = null
)
