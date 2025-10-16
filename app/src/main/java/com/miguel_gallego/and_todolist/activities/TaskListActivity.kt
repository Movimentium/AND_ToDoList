package com.miguel_gallego.and_todolist.activities

import android.os.Bundle
import android.renderscript.ScriptGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.recyclerview.widget.LinearLayoutManager
import com.miguel_gallego.and_todolist.R
import com.miguel_gallego.and_todolist.adapters.TaskAdapter
import com.miguel_gallego.and_todolist.data.CategoryDAO
import com.miguel_gallego.and_todolist.data.Task
import com.miguel_gallego.and_todolist.databinding.ActivityTaskListBinding

class TaskListActivity : AppCompatActivity() {

    lateinit var binding: ActivityTaskListBinding
    lateinit var adapter: TaskAdapter
    var taskList: List<Task> = emptyList()
    lateinit var taskDAO: CategoryDAO // TODO CHANGE

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

        //taskDAO = ...
        adapter = TaskAdapter(taskList, {},{}, {})
        binding.vwRecycler.adapter = adapter
        binding.vwRecycler.layoutManager = LinearLayoutManager(this)
        binding,
    }


    override fun onResume() {
        super.onResume()

    }
}