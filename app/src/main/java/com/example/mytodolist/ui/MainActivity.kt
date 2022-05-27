package com.example.mytodolist.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.mytodolist.databinding.ActivityMainBinding
import com.example.mytodolist.ui.db.TodoDatabase
import com.example.mytodolist.ui.repository.TodoRepository
import com.example.mytodolist.ui.viewmodels.TodoViewModel
import com.example.mytodolist.ui.viewmodels.TodoViewModelProviderFactory

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val mBinding get() = _binding!!
    lateinit var viewModel: TodoViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        init()
    }

    private fun init () {
        val todoRepository = TodoRepository(TodoDatabase(this))
        val todoViewModelProviderFactory = TodoViewModelProviderFactory(application, todoRepository)
        viewModel = ViewModelProvider(this, todoViewModelProviderFactory).get(TodoViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}