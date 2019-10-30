package com.individual.rjpo.todolist.network

import com.individual.rjpo.todolist.models.Todo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

internal interface GetDataService {

    @GET("/todos")
    fun getTodoList(@Query("userId") user: String): Call<List<Todo>>

    @POST("/todos")
    fun createTodo(@Body todo: Todo): Call<Todo>
}