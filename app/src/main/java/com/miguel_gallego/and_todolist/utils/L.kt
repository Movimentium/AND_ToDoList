package com.miguel_gallego.and_todolist.utils

import android.util.Log

class L {
    companion object {
        const val kDB = "DB"

        fun log(msg: String) {
            Log.i(kDB,msg)
        }
    }
}