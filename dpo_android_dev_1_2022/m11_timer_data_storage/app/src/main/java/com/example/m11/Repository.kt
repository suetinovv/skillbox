package com.example.m11

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences


private const val PREFERENCE_NAME = "prefs_name"

class Repository(private val context: Context) {

    private val prefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = prefs.edit()
    private var local_value: String? = null

    fun saveText(text: String) {
        local_value = text
        editor.putString(PREFERENCE_NAME, text)
        editor.apply()
    }

    fun clearText() {
        local_value = null
        editor.clear()
        editor.apply()
    }

    fun getText(): String {
        return if (getDataFromLocalVariable() != null) {
            getDataFromLocalVariable().toString()
        } else if (getDataFromSharedPreference() != null) {
            getDataFromSharedPreference().toString()
        } else {
            "No value"
        }
    }

    private fun getDataFromSharedPreference(): String? {
        return prefs.getString(PREFERENCE_NAME, null)
    }

    private fun getDataFromLocalVariable(): String? {
        return local_value
    }
}
