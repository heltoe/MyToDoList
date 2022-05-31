package com.example.mytodolist.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mytodolist.databinding.TodoItemBinding
import com.example.mytodolist.databinding.TodoMenuBinding
import com.example.mytodolist.ui.models.Todo


class TodoAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class TodoViewHolder(val binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    inner class TodoViewHolderMenu(val binding: TodoMenuBinding) :
        RecyclerView.ViewHolder(binding.root)

    private val SHOW_MENU = 1
    private val HIDE_MENU = 2

    override fun getItemViewType(position: Int): Int {
        return if (differ.currentList[position].isOpen) {
            SHOW_MENU;
        } else {
            HIDE_MENU;
        }
    }

    private val differCallBack = object : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == SHOW_MENU) {
            return TodoViewHolderMenu(
                TodoMenuBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        } else {
            return TodoViewHolder(
                TodoItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val todoElement = differ.currentList[position]
        if(holder is TodoViewHolder) {
            holder.binding.textToDo.text = todoElement.text
            holder.binding.todoItem.setOnClickListener {
                onItemClickListener?.let { it(todoElement) }
            }
        }
        if(holder is TodoViewHolderMenu) {
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
    private var onItemClickListener: ((Todo) -> Unit)? = null

    fun setOnEditClickListener(listener: (Todo) -> Unit) {
        onEditClickListener = listener
    }

    fun setOnRemoveClickListener(listener: (Todo) -> Unit) {
        onRemoveClickListener = listener
    }

    fun setOnItemClickListener(listener: (Todo) -> Unit) {
        onItemClickListener = listener
    }

    fun toggleShowMenu(position: Int) {
        closeMenu(false, position)
        differ.currentList[position].isOpen = !differ.currentList[position].isOpen
        notifyDataSetChanged();
    }

    fun closeMenu(isNotify: Boolean = true, position: Int = -1) {
        for (i in 0 until differ.currentList.size) {
            if (differ.currentList[i].isOpen && i != position) differ.currentList[i].isOpen = false
        }
        if (isNotify) notifyDataSetChanged();
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}
