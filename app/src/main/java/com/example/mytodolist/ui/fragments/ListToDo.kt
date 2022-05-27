package com.example.mytodolist.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mytodolist.R
import com.example.mytodolist.databinding.FragmentListToDoBinding
import com.example.mytodolist.ui.MainActivity
import com.example.mytodolist.ui.adapters.TodoAdapter
import com.example.mytodolist.ui.models.Todo
import com.example.mytodolist.ui.viewmodels.TodoViewModel

class ListToDo : Fragment() {
    private var _binding: FragmentListToDoBinding? = null
    private val mBinding get() = _binding!!
    lateinit var viewModel: TodoViewModel
    lateinit var todoAdapter: TodoAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListToDoBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel = (activity as MainActivity).viewModel
        viewModel.setActiveToDoItem()
        setupRecyclerView()

        mBinding.saveButton.setOnClickListener {
            mBinding.todoTextInput.editText?.let { input ->
                if (input.text.isNotEmpty()) {
                    mBinding.todoTextInput.helperText = ""
                    val todoInstance = Todo(input.text.toString())
                    viewModel.setTodo(todoInstance)
                    mBinding.todoTextInput.editText?.setText("")
                } else {
                    mBinding.todoTextInput.helperText = getString(R.string.require_descr)
                }
            }
        }
        viewModel.getAllTodos().observe(viewLifecycleOwner, Observer {
            todoAdapter.differ.submitList(it)
        })
    }

    private fun setupRecyclerView() {
        todoAdapter = TodoAdapter()
        mBinding.recyclerView.apply {
            adapter = todoAdapter
            layoutManager = LinearLayoutManager(activity)
        }
        todoAdapter.setOnEditClickListener {
            viewModel.setActiveToDoItem(it)
            findNavController().navigate(R.id.action_listToDo_to_editToDoItem)
        }

        todoAdapter.setOnRemoveClickListener {
            viewModel.deleteTodo(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}