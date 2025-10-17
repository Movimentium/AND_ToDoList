package com.miguel_gallego.and_todolist.activities

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.miguel_gallego.and_todolist.R
import com.miguel_gallego.and_todolist.data.Category
import com.miguel_gallego.and_todolist.data.CategoryDAO
import com.miguel_gallego.and_todolist.data.Task
import com.miguel_gallego.and_todolist.data.TaskDAO
import com.miguel_gallego.and_todolist.databinding.ActivityTaskBinding
import com.miguel_gallego.and_todolist.utils.K

class TaskActivity : AppCompatActivity() {

    lateinit var binding: ActivityTaskBinding
    lateinit var categoryDAO: CategoryDAO
    lateinit var category: Category
    lateinit var taskDAO: TaskDAO
    lateinit var task: Task

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.outline_close_24)

        val categoryId = intent.getIntExtra(K.categoryIdKey, -1)
        val taskId = intent.getIntExtra(K.taskIdKey, -1)
        categoryDAO = CategoryDAO(this)
        taskDAO = TaskDAO(this)
        category = categoryDAO.getCategoryWithId(categoryId)!!
        if (taskId == -1) {
            supportActionBar?.title = "New Task"
            task = Task(-1,"",false, category)
        } else {
            supportActionBar?.title = "Edit Task"
            task = taskDAO.getTaskWithId(taskId)!! //WARNING: dangerous, but never happen
        }

        binding.txtIn.editText?.setText(task.title)
        binding.btnSave.setOnClickListener {
            task.title = binding.txtIn.editText?.text.toString()
            if (!task.title.isEmpty()) {
                if (task.id == -1) {
                    taskDAO.insert(task)
                } else {
                    taskDAO.update(task)
                }
                finish()
            } else {
                // TODO: Show msg to user
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // There is only one item: 'Home' (close)
        finish()
        return true
    }
}