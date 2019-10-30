package com.individual.rjpo.todolist

import android.graphics.Rect
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.individual.rjpo.todolist.adapters.TodoListAdapter
import com.individual.rjpo.todolist.databinding.ActivityMainBinding
import com.individual.rjpo.todolist.models.Todo
import com.individual.rjpo.todolist.viewmodels.TodoListViewModel
import com.individual.rjpo.todolist.viewmodels.TodoListViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: TodoListViewModel
    private lateinit var activityBinding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
            TodoCreation.newInstance().show(supportFragmentManager, "new_todo")
        }

        val mViewModel: TodoListViewModel by lazy {
            ViewModelProviders.of(this, TodoListViewModelFactory { TodoListViewModel(application) })
                .get(TodoListViewModel::class.java)
        }

        this.viewModel = mViewModel

        observeViewModel(viewModel)
    }

    private fun observeViewModel(viewModel: TodoListViewModel) {

        activityBinding.recyclerView.addItemDecoration(MarginItemDecoration(18))
        val adapter = TodoListAdapter(mutableListOf<Todo>(), viewModel, this)
        activityBinding.recyclerView.adapter = adapter
        viewModel.todoListObservable.observe(this,
            Observer<List<Todo>> { todos ->
                if (todos != null) {
                    adapter.updateList(todos)
                } else {
                    adapter.updateList(mutableListOf())
                }
            })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_reset -> {
                viewModel.resetState()
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

class MarginItemDecoration(private val spaceHeight: Int) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View,
        parent: RecyclerView, state: RecyclerView.State
    ) {
        with(outRect) {
            if (parent.getChildAdapterPosition(view) == 0) {
                top = spaceHeight
            }
            left = spaceHeight
            right = spaceHeight
            bottom = spaceHeight
        }
    }
}
