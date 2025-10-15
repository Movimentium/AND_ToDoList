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

        categoryDAO = CategoryDAO(this)
        val categoryId = intent.getIntExtra(kCategoryId,-1)
        if (categoryId == -1) {  // create new category
            category = categoryDAO.getCategoryWithId(categoryId)!!  //WARNING DANGEROUS
        } else {  // edit existing category
            category = Category(-1, "")
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
            }
        }

    }


}