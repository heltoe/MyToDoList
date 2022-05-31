package com.example.mytodolist.ui.models

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class Todo(
    var text: String = "",
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @Ignore var isOpen: Boolean = false
)
