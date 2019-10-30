package com.individual.rjpo.todolist.repositories

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.individual.rjpo.todolist.dao.AppDatabase
import com.individual.rjpo.todolist.dao.TodoDao
import com.individual.rjpo.todolist.models.Todo
import com.individual.rjpo.todolist.network.GetDataService
import com.individual.rjpo.todolist.network.RetrofitClientInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors


open class TodoRepository(
    val application: Application
) {
    private var todoDao: TodoDao = AppDatabase.getInstance(application).todoDao()
    private var executorService: ExecutorService = Executors.newSingleThreadExecutor()

    private val getDataService: GetDataService =
        RetrofitClientInstance.retrofitInstance.create(GetDataService::class.java)
    val data = MutableLiveData<List<Todo>>()

    fun addTodo(title: String) {
        executorService.execute {
            val id = todoDao.insertOne(Todo(1, null, title, false))
            data.postValue(data.value?.plus(Todo(1, id.toInt(), title, false)))
        }
    }

    fun removeTodo(position: Int) {
        executorService.execute {
            todoDao.deleteOne(position)
            data.postValue(data.value!!.minus(data.value!!.find { todo -> todo.id == position }!!))
        }
    }

    fun reset() {
        executorService.execute { todoDao.deleteAll(); getTodoList() }
    }

    private fun getTodoList() {
        val call = getDataService.getTodoList("1")

        call.enqueue(object : Callback<List<Todo>> {
            override fun onFailure(call: Call<List<Todo>>, t: Throwable) {
                Toast.makeText(application, "Erro a resetar!", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<List<Todo>>, response: Response<List<Todo>>) {
                data.value = response.body()
                executorService.execute { todoDao.insertAll(response.body()!!.toTypedArray()) }
            }
        })
    }

    private fun loadTodoListFromCache() {
        executorService.execute { data.postValue(todoDao.selectAll().toMutableList()) }
    }

    fun updateTodo(todo: Todo) {
        val array = data.value!!
        val newList = mutableListOf<Todo>()
        newList.addAll(array)
        if ((newList.find { mTodo -> mTodo.id == todo.id }) != null) {
            newList[array.indexOf((newList.find { mTodo -> mTodo.id == todo.id }!!))] = todo
            data.postValue(newList)
            executorService.execute { todoDao.updateOne(todo) }
        }
    }

    init {
        loadTodoListFromCache()
    }
}