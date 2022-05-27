package com.example.mytodolist.ui.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    val text: String = "",
    @PrimaryKey(autoGenerate = true) val id: Int? = null
)
