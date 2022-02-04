package com.example.attendancetracker.preference

import android.content.Context
import android.content.SharedPreferences


class PreferenceHelper private constructor(context: Context) {
    private val preferences: SharedPreferences
    private val editor: SharedPreferences.Editor
    var isSetup: Boolean
        get() = preferences.getBoolean(SETUP, false)
        set(isSetup) {
            editor.putBoolean(SETUP, isSetup).commit()
        }

    companion object {
        private const val SETUP = "Setup"
        private const val PREF_NAME = "Prefs"
        private val instance: PreferenceHelper? = null
        fun getInstance(context: Context): PreferenceHelper {
            return instance ?: PreferenceHelper(context)
        }
    }

    init {
        preferences = context.getSharedPreferences(PREF_NAME, 0)
        editor = preferences.edit()
    }
}