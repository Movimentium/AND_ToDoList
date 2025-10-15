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
            val updatedRows = db.update(Category.TABLE_NAME, values, "${Category.COLUMN_ID} = ${category.id}", null)
            L.log("Updated: $updatedRows in table ${Category.TABLE_NAME}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
    }
}