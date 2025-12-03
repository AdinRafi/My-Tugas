package com.example.assignmenttrack.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class User(
    @PrimaryKey var id: Int = 1,
    var name: String,
    var profilePicturePath: String?,
)