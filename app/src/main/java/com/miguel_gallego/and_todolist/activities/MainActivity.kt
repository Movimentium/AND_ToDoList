package com.miguel_gallego.and_todolist.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.miguel_gallego.and_todolist.R
import com.miguel_gallego.and_todolist.data.Category
import com.miguel_gallego.and_todolist.data.CategoryAdapter
import com.miguel_gallego.and_todolist.data.CategoryDAO
import com.miguel_gallego.and_todolist.databinding.ActivityMainBinding
import com.miguel_gallego.and_todolist.utils.L

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    lateinit var adapter: CategoryAdapter
    lateinit var categoryDAO: CategoryDAO
    var catetegoryList: List<Category> = emptyList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.vwRecycler.adapter = adapter
        binding.vwRecycler.layoutManager = LinearLayoutManager(this)
        binding.btnCreate.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }

        categoryDAO = CategoryDAO(this)
        adapter = CategoryAdapter(catetegoryList, {

        })
    }

    override fun onResume() {
        super.onResume()
        catetegoryList = categoryDAO.getAllCategories()
        adapter.updateItems(catetegoryList)
    }


    fun debug_db_test() {
        val categoryDAO = CategoryDAO(this)

        val category1 = Category(-1, "Compra")
        val category2 = Category(-1, "Casa")

        categoryDAO.insert(category1)
        categoryDAO.insert(category2)

        val allCategories = categoryDAO.getAllCategories()

        L.log(allCategories.toString())
    }
}