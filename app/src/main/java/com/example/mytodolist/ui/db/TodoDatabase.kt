package com.example.mytodolist.ui.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mytodolist.ui.models.Todo

@Database(
    entities = [Todo::class],
    version = 1
)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun getTodoDao(): TodoDao

    companion object {
        @Volatile
        private var instanse: TodoDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instanse ?: synchronized(LOCK) {
            instanse ?: createDataBase(context).also { instanse = it }
        }


        private fun createDataBase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                TodoDatabase::class.java,
                "todo_db.db"
            ).build()
    }
}