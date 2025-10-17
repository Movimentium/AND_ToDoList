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

    private fun readFromCursor(cursor: Cursor): Task {
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
            // Insert new row, returns his primary key
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
            // Insert row, returns his primary key
            val updatedRows = db.update(Task.TABLE_NAME, values, "${Task.COLUMN_ID} = ${task.id}", null)
            L.log("$updatedRows rows updated in table ${Task.TABLE_NAME}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
    }

    fun delete(task: Task) {
        delete(task.id)
    }

    private fun delete(id: Int) {
        try {
            open()
            // Insert row, returns his primary key
            val deletedRows = db.delete(Task.TABLE_NAME, "${Task.COLUMN_ID} = $id", null)
            L.log("$deletedRows rows deleted in table ${Task.TABLE_NAME}")
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
    }

    fun getAllTasks(): List<Task> {
        return getTasksWhere(null)
    }

    fun getTasksOfCategory(category: Category): List<Task> {
        return getTasksWhere("${Task.COLUMN_CATEGORY} = ${category.id}")
    }

    fun getTasksOfCategoryOfDone(category: Category, done: Boolean): List<Task> {
        return getTasksWhere("${Task.COLUMN_CATEGORY} = ${category.id} AND ${Task.COLUMN_DONE} = $done")
    }

    fun getTasksWhere(selection: String?): List<Task> {
        val items = mutableListOf<Task>()
        try {
            open()
            val cursor = db.query(
                Task.TABLE_NAME,
                null,           // Array of columns to return (null to get all cols)
                selection,                // WHERE clause
                null,       // values for WHERE clause
                null,           // don't group the rows
                null,             // don't filter by row groups
                null             // sort order
            )
            while (cursor.moveToNext()) {
                val task = readFromCursor(cursor)
                items.add(task)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            close()
        }
        return items
    }
}