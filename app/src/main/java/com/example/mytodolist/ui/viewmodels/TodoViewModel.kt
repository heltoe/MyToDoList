package com.example.mytodolist.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mytodolist.ui.models.Todo
import com.example.mytodolist.ui.repository.TodoRepository
import kotlinx.coroutines.launch

class TodoViewModel(
    app: Application,
    private val todoRepository: TodoRepository
) : AndroidViewModel(app) {
    val todoList: MutableLiveData<List<Todo>> = MutableLiveData()
    var todoListPage = 1

    init {
        getAllTodos()
    }

    fun getAllTodos() = todoRepository.getAllTodos()

    //
    fun getTodo(id: Long) = todoRepository.getTodo(id)

    //
    fun setTodo(todo: Todo) = viewModelScope.launch {
        todoRepository.setTodo(todo)
    }

    //
    fun updateTodo(todo: Todo) = viewModelScope.launch {
        todoRepository.updateTodo(todo)
    }

    //
    fun deleteTodo(todo: Todo) = viewModelScope.launch {
        todoRepository.deleteTodo(todo)
    }
}