package com.miguel_gallego.and_todolist.activities

import android.os.Bundle
import android.renderscript.ScriptGroup
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.KeyPosition
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.miguel_gallego.and_todolist.R
import com.miguel_gallego.and_todolist.adapters.HeaderAdapter
import com.miguel_gallego.and_todolist.adapters.TaskAdapter
import com.miguel_gallego.and_todolist.data.Category
import com.miguel_gallego.and_todolist.data.CategoryDAO
import com.miguel_gallego.and_todolist.data.Task
import com.miguel_gallego.and_todolist.data.TaskDAO
import com.miguel_gallego.and_todolist.databinding.ActivityTaskListBinding
import com.miguel_gallego.and_todolist.utils.K

class TaskListActivity : AppCompatActivity() {

    lateinit var binding: ActivityTaskListBinding

    lateinit var taskToDoAdapter: TaskAdapter
    lateinit var taskDoneAdapter: TaskAdapter
    lateinit var headToDoAdapter: HeaderAdapter
    lateinit var headDoneAdapter: HeaderAdapter

    var taskToDoList: List<Task> = emptyList()
    var taskDoneList: List<Task> = emptyList()

    lateinit var taskDAO: TaskDAO
    lateinit var categoryDAO: CategoryDAO
    lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTaskListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Model data
        taskDAO = TaskDAO(this)
        categoryDAO = CategoryDAO(this)
        val categoryId = intent.getIntExtra(K.categoryIdKey, -1)
        category = categoryDAO.getCategoryWithId(categoryId)!!

        // ActionBar
        supportActionBar?.title = category.name
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Adapters
        taskToDoAdapter = TaskAdapter(taskToDoList, ::onClickTask, ::onCheckTask, ::onDeleteTask)
        taskDoneAdapter = TaskAdapter(taskDoneList, ::onClickTask, ::onCheckTask, ::onDeleteTask)
        headToDoAdapter = HeaderAdapter("To Do List")
        headDoneAdapter = HeaderAdapter("Done List")

        // Binding
        binding.vwRecycler.adapter = ConcatAdapter(headToDoAdapter, taskToDoAdapter, headDoneAdapter, headDoneAdapter)
        binding.vwRecycler.layoutManager = LinearLayoutManager(this)
        binding.btnCreate.setOnClickListener {
            TODO()
        }
    }

    override fun onResume() {
        super.onResume()
        reloadData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }

    private fun reloadData() {
        taskToDoList = taskDAO.getTasksOfCategoryOfDone(category, false)
        taskDoneList = taskDAO.getTasksOfCategoryOfDone(category, true)
        taskToDoAdapter.updateItems(taskToDoList)
        taskDoneAdapter.updateItems(taskDoneList)
    }

    // TODO: SEGUIR AQUÍ
    private fun onClickTask(position: Int) {

    }

    private fun onCheckTask(position: Int) {

    }

    private fun onDeleteTask(position: Int) {

    }

}