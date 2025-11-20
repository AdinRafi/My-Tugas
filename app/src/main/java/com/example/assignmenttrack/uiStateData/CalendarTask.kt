package com.example.assignmenttrack.uiStateData

import com.example.assignmenttrack.Model.Task

data class CalendarTask(
    val day:Int,
    val tasks:List<Task>? = null
)

