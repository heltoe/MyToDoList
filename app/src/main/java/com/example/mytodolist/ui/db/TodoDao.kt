package com.example.mytodolist.ui.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mytodolist.ui.models.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun getAllTodos(): LiveData<List<Todo>>

    @Query("SELECT * FROM todo WHERE id = :id")
    fun getTodo(id: Long): LiveData<Todo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setTodo(todo: Todo): Long

    @Update()
    suspend fun updateTodo(todo: Todo)

    @Delete
    suspend fun deleteTodo(todo: Todo)
}