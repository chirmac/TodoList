package com.individual.rjpo.todolist.adapters

import androidx.recyclerview.widget.DiffUtil
import com.individual.rjpo.todolist.models.Todo

class TodoDiffCallback(val oldTodos: List<Todo>, val newTodos: List<Todo>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTodos[oldItemPosition].id == newTodos[newItemPosition].id
    }

    override fun getOldListSize(): Int {
        return oldTodos.size
    }

    override fun getNewListSize(): Int {
        return newTodos.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldTodos[oldItemPosition] == newTodos[newItemPosition]
    }
}