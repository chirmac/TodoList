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
import com.individual.rjpo.todolist.viewmodels.TodoListViewModel

class TodoCreation : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.fragment_todo_creation, null)

        val et1 = view.findViewById(R.id.newTitle) as TextInputEditText

        return activity?.let {
            // Use the Builder class for convenient dialog construction
            val builder = AlertDialog.Builder(it)
            builder.setView(view).setTitle("Criação")
                .setPositiveButton(
                    "Criar"
                ) { _, _ ->
                    val s1 = et1.text
                    val model = activity?.run {
                        ViewModelProviders.of(this)[TodoListViewModel::class.java]
                    } ?: throw Exception("Invalid Activity")
                    model.addNewTodo(s1.toString())
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
        fun newInstance(): TodoCreation {
            return TodoCreation()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_todo_creation, container, false)
    }


}
