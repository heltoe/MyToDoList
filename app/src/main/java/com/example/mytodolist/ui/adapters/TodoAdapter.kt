package com.example.mytodolist.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.databinding.TodoItemBinding
import com.example.mytodolist.ui.models.Todo

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    inner class TodoViewHolder(val binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val differCallBack = object : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            TodoItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val todoElement = differ.currentList[position]
        holder.itemView.apply {
            holder.binding.textToDo.text = todoElement.text
            holder.binding.editBtn.setOnClickListener {
                onEditClickListener?.let { it(todoElement) }
            }
            holder.binding.deleteBtn.setOnClickListener {
                onRemoveClickListener?.let { it(todoElement) }
            }
        }
    }

    private var onEditClickListener: ((Todo) -> Unit)? = null
    private var onRemoveClickListener: ((Todo) -> Unit)? = null

    fun setOnEditClickListener(listener: (Todo) -> Unit) {
        onEditClickListener = listener
    }

    fun setOnRemoveClickListener(listener: (Todo) -> Unit) {
        onRemoveClickListener = listener
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}