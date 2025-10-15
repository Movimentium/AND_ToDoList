package com.miguel_gallego.and_todolist.activities

import android.os.Bundle
import android.view.inputmethod.InputBinding
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.miguel_gallego.and_todolist.R
import com.miguel_gallego.and_todolist.data.Category
import com.miguel_gallego.and_todolist.data.CategoryDAO
import com.miguel_gallego.and_todolist.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {
    companion object {
        val kCategoryId = "CATEGORY_ID"
    }

    lateinit var binding: ActivityCategoryBinding
    lateinit var categoryDAO: CategoryDAO

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        categoryDAO = CategoryDAO(this)

        binding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnSave.setOnClickListener {
            val categoryName = binding.txtEditName.editText?.text.toString()
            if (categoryName != null) {
                val category = Category(-1, categoryName)
                categoryDAO.insert(category)
                finish()  // CategoryActivity disappears
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


}