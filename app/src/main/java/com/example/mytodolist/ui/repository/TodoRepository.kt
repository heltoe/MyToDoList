package com.example.mytodolist.ui.repository

import com.example.mytodolist.ui.db.TodoDatabase
import com.example.mytodolist.ui.models.Todo

class TodoRepository(private val db: TodoDatabase) {
    fun getAllTodos() = db.getTodoDao().getAllTodos()
    fun getTodo(id: Int) = db.getTodoDao().getTodo(id)
    suspend fun setTodo(todo: Todo) = db.getTodoDao().setTodo(todo)
    suspend fun updateTodo(todo: Todo) = db.getTodoDao().updateTodo(todo)
    suspend fun deleteTodo(todo: Todo) = db.getTodoDao().deleteTodo(todo)
}