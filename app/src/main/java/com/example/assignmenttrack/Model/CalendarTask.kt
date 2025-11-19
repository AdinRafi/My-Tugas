package com.example.assignmenttrack.Model

data class CalendarTask(
    val day:Int,
    val tasks:List<Task>? = null
)

