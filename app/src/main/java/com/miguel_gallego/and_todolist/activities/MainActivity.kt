package com.miguel_gallego.and_todolist.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
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
        supportActionBar?.setTitle("To Do List")
        supportActionBar?.setSubtitle("Categories")

        categoryDAO = CategoryDAO(this)
        adapter = CategoryAdapter(catetegoryList,
            onCategoryClick, onEditCategory, ::deleteCategoryAt)
        binding.vwRecycler.adapter = adapter
        binding.vwRecycler.layoutManager = LinearLayoutManager(this)
        binding.btnCreate.setOnClickListener {
            val intent = Intent(this, CategoryActivity::class.java)
            startActivity(intent)
        }
        // debug_db_test_add_two_categories()

    }

    override fun onResume() {
        super.onResume()
        reloadData()
    }

    private fun reloadData() {
        catetegoryList = categoryDAO.getAllCategories()
        adapter.updateItems(catetegoryList)
    }

    private val onCategoryClick: (Int) -> Unit = {

    }

    private val onEditCategory: (Int) -> Unit = {
        val category = catetegoryList[it]
        val intent = Intent(this, CategoryActivity::class.java)
        intent.putExtra(CategoryActivity.kCategoryId, category.id)
        startActivity(intent)
    }

    private fun deleteCategoryAt(position: Int) {
        val category = catetegoryList[position]
        val alertSureDelete = getAlertSureDeleteCategory(category)
        alertSureDelete.show()
    }

    private fun getAlertSureDeleteCategory(category: Category): AlertDialog {
        return AlertDialog.Builder(this)
            .setTitle("Delete category")
            .setMessage("Are you sure you want to delete the category '${category.name}'?")
            .setNegativeButton("No",null)
            .setPositiveButton("Yes") { _, _ ->
                categoryDAO.delete(category.id)
                reloadData()
                Snackbar.make(binding.root, "Category '${category.name}' deleted", Snackbar.LENGTH_LONG)
                    .show()
            }
            .create()
    }


    fun debug_db_test_add_two_categories() {
        val categoryDAO = CategoryDAO(this)

        val category1 = Category(-1, "Coche")
        val category2 = Category(-1, "Jardín")

        categoryDAO.insert(category1)
        categoryDAO.insert(category2)

        val allCategories = categoryDAO.getAllCategories()

        L.log(allCategories.toString())
    }

}