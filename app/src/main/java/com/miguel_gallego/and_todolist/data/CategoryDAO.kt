package com.miguel_gallego.and_todolist.data

import android.content.Context
import android.database.sqlite.SQLiteDatabase

class CategoryDAO(
    val context: Context
) {
    private lateinit var db: SQLiteDatabase

    private fun open() {

    }
}