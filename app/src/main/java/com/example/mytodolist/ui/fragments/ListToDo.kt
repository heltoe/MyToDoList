package com.example.mytodolist.ui.fragments

import android.graphics.Canvas
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
        setupTouchHelper()

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

    private fun setupTouchHelper() {
        val touchHelperCB: ItemTouchHelper.Callback =
            object : ItemTouchHelper.Callback() {
                private val background = ColorDrawable(resources.getColor(R.color.white))
                override fun getMovementFlags(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder
                ): Int {
                    val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
                    val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
                    return makeMovementFlags(dragFlags, swipeFlags)
                }

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    todoAdapter.toggleShowMenu(viewHolder.adapterPosition)
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    super.onChildDraw(
                        c,
                        recyclerView,
                        viewHolder,
                        dX,
                        dY,
                        actionState,
                        isCurrentlyActive
                    )

                    val itemView = viewHolder.itemView
                    if (dX > 0) {
                        background.setBounds(
                            itemView.left,
                            itemView.top,
                            itemView.left + dX.toInt(),
                            itemView.bottom
                        )
                    } else if (dX < 0) {
                        background.setBounds(
                            itemView.right + dX.toInt(),
                            itemView.top,
                            itemView.right,
                            itemView.bottom
                        )
                    } else {
                        background.setBounds(0, 0, 0, 0)
                    }

                    background.draw(c)
                }
            }
        val itemTouchHelper = ItemTouchHelper(touchHelperCB)
        itemTouchHelper.attachToRecyclerView(mBinding.recyclerView)
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
        todoAdapter.setOnItemClickListener {
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