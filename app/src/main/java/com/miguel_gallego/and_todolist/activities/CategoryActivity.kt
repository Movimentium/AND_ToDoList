package com.miguel_gallego.and_todolist.activities

import android.os.Bundle
import android.view.MenuItem
import android.view.inputmethod.InputBinding
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.miguel_gallego.and_todolist.R
import com.miguel_gallego.and_todolist.data.Category
import com.miguel_gallego.and_todolist.data.CategoryDAO
import com.miguel_gallego.and_todolist.databinding.ActivityCategoryBinding
import com.miguel_gallego.and_todolist.utils.K

class CategoryActivity : AppCompatActivity() {

    lateinit var binding: ActivityCategoryBinding
    lateinit var categoryDAO: CategoryDAO
    lateinit var category: Category

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeAsUpIndicator(R.drawable.outline_close_24)

        categoryDAO = CategoryDAO(this)
        val categoryId = intent.getIntExtra(K.categoryIdKey,-1)
        if (categoryId == -1) {
            category = Category(-1, "")
            supportActionBar?.setTitle("New Category")
        } else {
            category = categoryDAO.getCategoryWithId(categoryId)!!
            supportActionBar?.setTitle("Edit Category")
        }

        binding.txtEditName.editText?.setText(category.name)
        binding.btnSave.setOnClickListener {
            category.name = binding.txtEditName.editText?.text.toString()  // TODO trimming, validate
            if (!category.name.isEmpty()) {
                if (category.id == -1) {
                    categoryDAO.insert(category)
                } else {
                    categoryDAO.update(category)
                }
                finish()  // CategoryActivity disappears
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