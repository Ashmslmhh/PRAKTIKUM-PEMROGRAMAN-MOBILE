package com.example.composeModul5.data

import android.content.Context
import android.content.SharedPreferences

class LanguagePreferences(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("language_prefs", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_LANGUAGE = "selected_language"
        const val LANG_ENGLISH = "en-US"
        const val LANG_INDONESIAN = "id-ID"
    }

    fun getLanguage(): String {
        return prefs.getString(KEY_LANGUAGE, LANG_ENGLISH) ?: LANG_ENGLISH
    }

    fun setLanguage(language: String) {
        prefs.edit().putString(KEY_LANGUAGE, language).apply()
    }
}