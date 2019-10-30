package com.individual.rjpo.todolist.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
class Todo(
    @ColumnInfo(name = "userId") @SerializedName("userId") val userId: Int,
    @PrimaryKey(autoGenerate = true) @SerializedName("id") val id: Int?,
    @ColumnInfo(name = "title") @SerializedName("title") var title: String,
    @ColumnInfo(name = "completed") @SerializedName("completed") var completed: Boolean
) : Serializable