package com.example.mytodolist.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mytodolist.R
import com.example.mytodolist.databinding.FragmentEditToDoItemBinding
import com.example.mytodolist.ui.MainActivity
import com.example.mytodolist.ui.viewmodels.TodoViewModel

class EditToDoItem : Fragment() {
    private var _binding: FragmentEditToDoItemBinding? = null
    private val mBinding get() = _binding!!
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
            val text = mBinding.todoTextInput.editText?.text

            if (text != null && text.isNotEmpty()) {
                mBinding.todoTextInput.helperText = ""
//                viewModel.setTodoHandler(text.toString())
                mBinding.todoTextInput.editText?.setText("")
            } else {
                mBinding.todoTextInput.helperText = getString(R.string.require_descr)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}