package com.individual.rjpo.todolist.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.individual.rjpo.todolist.R
import com.individual.rjpo.todolist.TodoDetails
import com.individual.rjpo.todolist.databinding.TodoItemBinding
import com.individual.rjpo.todolist.models.Todo
import com.individual.rjpo.todolist.viewmodels.TodoListViewModel


class TodoListAdapter(
    private val todoList: MutableList<Todo>,
    private val viewModel: TodoListViewModel,
    private val context: FragmentActivity
) : RecyclerView.Adapter<TodoListAdapter.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val listItemBinding = DataBindingUtil.inflate<TodoItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.todo_item, parent, false
        )

        // Return a new holder instance
        return MyViewHolder(listItemBinding)
    }

    override fun getItemCount(): Int = todoList.size

    fun updateList(newTodoList: List<Todo>) {
        val result = DiffUtil.calculateDiff(TodoDiffCallback(todoList, newTodoList))
        todoList.clear()
        todoList.addAll(newTodoList)
        result.dispatchUpdatesTo(this)
    }

    private fun onLongClick(position: Int): Boolean {
        viewModel.removeTodo(position)
        return true
    }

    private fun onClick(position: Int) {
        val dialog = TodoDetails.newInstance(todoList[position])
        dialog.show(context.supportFragmentManager, "dialog")
    }

    override fun getItemViewType(position: Int): Int {
        return (position)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val todo = todoList[position]
        holder.binding.todo = todo
        holder.itemView.setOnLongClickListener { onLongClick(todo.id!!) }
        holder.itemView.setOnClickListener { onClick(position) }
        holder.binding.checkBox.isChecked = todo.completed
        holder.binding.checkBox.setOnClickListener {
            if (holder.binding.checkBox.isChecked != todo.completed) {
                todo.completed = !todo.completed
                viewModel.updateTodo(todo)
            }
        }
    }

    class MyViewHolder(itemView: TodoItemBinding) : RecyclerView.ViewHolder(itemView.root) {

        var binding: TodoItemBinding = itemView

    }
}