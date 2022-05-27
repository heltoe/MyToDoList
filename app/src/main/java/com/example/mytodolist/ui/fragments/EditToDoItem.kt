package com.example.mytodolist.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.mytodolist.R
import com.example.mytodolist.databinding.FragmentEditToDoItemBinding
import com.example.mytodolist.ui.MainActivity
import com.example.mytodolist.ui.models.Todo
import com.example.mytodolist.ui.viewmodels.TodoViewModel

class EditToDoItem : Fragment() {
    private var _binding: FragmentEditToDoItemBinding? = null
    private val mBinding get() = _binding!!
    private var idTodo: Int? = null
    lateinit var viewModel: TodoViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditToDoItemBinding.inflate(layoutInflater, container, false)
        return mBinding.root
    }

    override fun onStart() {
        super.onStart()
        viewModel = (activity as MainActivity).viewModel

        mBinding.saveButton.setOnClickListener {
            mBinding.todoTextInput.editText?.let { input ->
                if (input.text.isNotEmpty()) {
                    mBinding.todoTextInput.helperText = ""
                    val todoInstance = Todo(input.text.toString(), idTodo)
                    viewModel.updateTodo(todoInstance)
                    mBinding.todoTextInput.editText?.setText("")
                    findNavController().navigate(R.id.action_editToDoItem_to_listToDo)
                } else {
                    mBinding.todoTextInput.helperText = getString(R.string.require_descr)
                }
            }
        }
        viewModel.todoItemActive.value.let { todo ->
            todo?.id.let { id ->
                idTodo = id
                if (idTodo != null) {
                    viewModel.getTodo(idTodo!!).observe(viewLifecycleOwner, Observer {
                        mBinding.todoTextInput.editText?.setText(it.text)
                    })
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}