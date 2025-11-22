package com.example.assignmenttrack.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    var name: String,
    var profilePictureId: Int,
    var taskCompleted: Int,
    var taskLate: Int,
    var taskPending: Int,
    var taskTotal: Int = taskCompleted + taskLate + taskPending,

    var tugasTotal: Int,
    var belajarTotal: Int,
    var kerjaTotal: Int,
)