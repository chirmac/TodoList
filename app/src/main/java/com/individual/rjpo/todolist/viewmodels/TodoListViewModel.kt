package com.individual.rjpo.todolist.viewmodels

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.individual.rjpo.todolist.models.Todo
import com.individual.rjpo.todolist.repositories.TodoRepository

class TodoListViewModel constructor(application: Application) : ViewModel() {

    private val repository = TodoRepository(application)

    val todoListObservable: LiveData<List<Todo>> = repository.data
    fun addNewTodo(title: String) {
        repository.addTodo(title)
    }

    fun removeTodo(position: Int) {
        repository.removeTodo(position)
    }

    fun resetState() {
        repository.reset()
    }

    fun updateTodo(todo: Todo) {
        repository.updateTodo(todo)
    }
}