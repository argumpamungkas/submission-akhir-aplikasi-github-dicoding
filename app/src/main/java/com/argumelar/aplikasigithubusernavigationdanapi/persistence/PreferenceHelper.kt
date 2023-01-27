package com.argumelar.aplikasigithubusernavigationdanapi.persistence

import android.content.Context
import android.content.SharedPreferences

class PreferenceHelper(var context: Context) {

    private val PREF_NAME = "theme_apps"
    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor

    init {
        editor = sharedPref.edit()
    }

    fun putTheme( key: String, value: Int){
        editor.putInt(key, value)
            .apply()
    }

    fun getTheme(key: String): Int? {
        return sharedPref.getInt(key, 0)
    }

    fun clear(){
        editor.clear()
            .apply()
    }

    companion object {
        const val DARK_MODE = "dark_mode"
    }
}