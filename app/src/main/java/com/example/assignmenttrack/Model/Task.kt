package com.example.assignmenttrack.Model


import java.time.Instant
import java.time.LocalDateTime
import java.time.Month
import java.time.ZoneId


data class Task(
    val id: Int,
    val type: TaskType,
    val title: String,
    val description: String,
    val deadline: Instant,
    val status: Boolean,
) {
    enum class TaskType {
        Tugas,
        Kerja,
        Belajar
    }
}

fun createInstant(year: Int, month: Month, day: Int, hour: Int, minute: Int): Instant {
    val localDateTime = LocalDateTime.of(year, month, day, hour, minute)
    return localDateTime.atZone(ZoneId.systemDefault()).toInstant()
}