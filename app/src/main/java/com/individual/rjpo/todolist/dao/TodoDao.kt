package com.individual.rjpo.todolist.dao

import androidx.room.*
import com.individual.rjpo.todolist.models.Todo

@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    fun selectAll(): Array<Todo>

    @Query("SELECT * FROM todo WHERE completed IN (:completed)")
    fun selectByStatus(completed: Boolean): List<Todo>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(todos: Array<Todo>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOne(todo: Todo): Long

    @Query("DELETE FROM todo WHERE id IN (:id)")
    fun deleteOne(id: Int)

    @Query("DELETE FROM todo")
    fun deleteAll()

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateOne(todo: Todo)
}