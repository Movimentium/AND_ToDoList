package com.miguel_gallego.and_todolist.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.miguel_gallego.and_todolist.utils.DatabaseManager
import com.miguel_gallego.and_todolist.utils.L

class TaskDAO(
    val context: Context
) {
    val categoryDAO = CategoryDAO(context)
    private lateinit var db: SQLiteDatabase

    private fun open() {
        db = DatabaseManager(context).writableDatabase
    }

    private fun close() {
        db.close()
    }

    // WHY this fun??
    private fun getContentValues(task: Task): ContentValues {
        val values = ContentValues()
        values.put(Task.COLUMN_TITLE, task.title)
        values.put(Task.COLUMN_DONE, task.done)
        values.put(Task.COLUMN_CATEGORY, task.category.id)
        return values
    }

    private fun readFromCurso(cursor: Cursor): Task {
        val id = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_ID))
        val title = cursor.getString(cursor.getColumnIndexOrThrow(Task.COLUMN_TITLE)) // String
        val done = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_DONE)) != 0 // Boolean
        val categoryId = cursor.getInt(cursor.getColumnIndexOrThrow(Task.COLUMN_CATEGORY))

        val category = categoryDAO.getCategoryWithId(categoryId) !!
        return Task(id, title, done, category)
    }

    fun insert(task: Task) {
        // Map of values, where columns' names are the keys
        val values = getContentValues(task)
        try {
            open()
            // Insert new row, and returns his primary key
            val newRowId = db.insert(Task.TABLE_NAME, null, values)
            L.log("New task in table ${Task.TABLE_NAME} with task.id: $newRowId")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
    }

    fun update(task: Task) {
        // Map of values, where columns' names are the keys
        val values = getContentValues(task)
        try {
            open()
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
    }
}