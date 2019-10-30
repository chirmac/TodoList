package com.individual.rjpo.todolist

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.material.textfield.TextInputEditText
import com.individual.rjpo.todolist.models.Todo
import com.individual.rjpo.todolist.viewmodels.TodoListViewModel

class TodoDetails : DialogFragment() {

    var todo: Todo? = null
    var et1: TextInputEditText? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.todo_details_fragment, null)

        et1 = view.findViewById(R.id.updateTitle) as TextInputEditText
        et1?.setText(todo?.title)

        return activity?.let {
            val builder = AlertDialog.Builder(it)
            builder.setView(view).setTitle("Edição")
                .setPositiveButton(
                    "Atualizar"
                ) { _, _ ->
                    val s1 = et1?.text
                    val model = activity?.run {
                        ViewModelProviders.of(this)[TodoListViewModel::class.java]
                    } ?: throw Exception("Invalid Activity")
                    todo?.title = s1.toString()
                    todo?.let { it1 -> model.updateTodo(it1) }
                }
                .setNegativeButton(
                    "Cancelar"
                ) { _, _ ->
                    // User cancelled the dialog
                }
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }

    companion object {
        fun newInstance(todo: Todo?): TodoDetails {
            val dialog = TodoDetails()
            val args = Bundle().apply {
                todo?.let { putSerializable("todo", it) }
            }
            dialog.arguments = args
            return dialog
        }
    }

    var onResult: ((todo: Todo) -> Unit)? = null

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        todo = arguments?.get("todo") as Todo?
        et1?.setText(todo?.title)
        onResult?.invoke(todo!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.todo_details_fragment, container, false)
    }

}
