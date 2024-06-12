package com.example.thecheesechaseapplication

import android.content.Context

class HighScoreManager(context: Context) {
    private val sharedPreferences = context.getSharedPreferences("HighScore", Context.MODE_PRIVATE)
    fun saveData(key1: String, value1: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key1, value1)
        editor.apply()
    }

    fun getData(key1: String, defaultValue1: String): String {
        return sharedPreferences.getString(key1, defaultValue1) ?: defaultValue1
    }
}