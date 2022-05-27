package com.example.mytodolist.ui.viewmodels

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.mytodolist.ui.repository.TodoRepository

class TodoViewModelProviderFactory(
    private val app: Application,
    private val todoRepository: TodoRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodoViewModel(app, todoRepository) as T
    }
}