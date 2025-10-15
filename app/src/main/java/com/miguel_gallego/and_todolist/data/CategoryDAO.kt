package com.miguel_gallego.and_todolist.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.miguel_gallego.and_todolist.utils.DatabaseManager
import com.miguel_gallego.and_todolist.utils.L

class CategoryDAO(
    val context: Context
) {
    private lateinit var db: SQLiteDatabase

    private fun open() {
        db = DatabaseManager(context).writableDatabase
    }

    private fun close() {
        db.close()
    }

    fun insert(category: Category) {  // For the new version: "add(category)"
        // Create a new map of values
        val values = ContentValues()
        values.put(Category.COLUMN_NAME, category.name)
        try {
            open()
            // Insert a new row, and returns his primary key
            val newRowId = db.insert(Category.TABLE_NAME, null, values)
            L.log("Inserted: id: $newRowId in table ${Category.TABLE_NAME}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
    }

    fun update(category: Category) {
        val values = ContentValues()
        values.put(Category.COLUMN_NAME, category.name)
        try {
            open()
            val updatedRows = db.update(Category.TABLE_NAME, values, "${Category.COLUMN_ID} = ${category.id}", null)
            L.log("Updated: $updatedRows in table ${Category.TABLE_NAME}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
    }

    fun delete(id: Int) {
        try {
            open()
            val deteledRows = db.delete(Category.TABLE_NAME, "${Category.COLUMN_ID} = $id", null)
            L.log("Deleted: $deteledRows in table ${Category.TABLE_NAME}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
    }

    fun getCategoryWithId(id: Int): Category? {
        var category: Category? = null
        // Define a projection that specifies which columns from the database
        // you will actually use after this query
        var projection: Array<String> = arrayOf(Category.COLUMN_ID, Category.COLUMN_NAME)

        // Filter results WHERE "id" = 'myId'
        val selection = "${Category.COLUMN_ID} = $id"
        val selectionArgs = null
        val sortOrder = null
        try {
            open()
            val cursor = db.query(
                Category.TABLE_NAME,       // table name
                projection,             // array of cols to return (write null to get all)
                selection,                        // cols for the WHERE clause
                selectionArgs,                    // vals for the WHERE clause
                null,                   // don't group the rows
                null,                    // don't filter by row groups
                sortOrder,
            )
            // Read the cursor data
            if (cursor.moveToNext()) {
                val id = cursor.getInt(cursor.getColumnIndexOrThrow(Category.COLUMN_ID))
                val name = cursor.getString(cursor.getColumnIndexOrThrow(Category.COLUMN_NAME))
                category = Category(id, name)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
        return  category
    }

    fun getAllCategories(): List<Category> {
        val items: MutableList<Category> = mutableListOf()
        val projection: Array<String> = arrayOf(Category.COLUMN_ID, Category.COLUMN_NAME)
        try {
            open()
            val cursor = db.query(
                Category.TABLE_NAME,       // table name
                projection,             // array of cols to return (write null to get all)
                null,                  // cols for the WHERE clause
                null,               // vals for the WHERE clause
                null,                   // don't group the rows
                null,                    // don't filter by row groups
                null,
            )
            // Read the cursor data
            while (cursor.moveToNext()) {
                val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(Category.COLUMN_ID))
                val categoryName = cursor.getString(cursor.getColumnIndexOrThrow(Category.COLUMN_NAME))
                val category = Category(categoryId, categoryName)
                items.add(category)
            }
        } catch (e: Exception) {
            L.log("ERROR")
            e.printStackTrace()
        } finally {
            close()
        }
        return items
    }


}