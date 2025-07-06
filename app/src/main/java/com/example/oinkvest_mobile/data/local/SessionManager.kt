package com.example.oinkvest_mobile.data.local

import android.content.Context
import android.content.SharedPreferences

object SessionManager {
    private const val PREFS_NAME = "session_prefs"
    private const val SESSION_ID_KEY = "jsessionid"

    private fun getPrefs(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveSessionId(context: Context, sessionId: String) {
        getPrefs(context).edit().putString(SESSION_ID_KEY, sessionId).apply()
    }

    fun getSessionId(context: Context): String? {
        return getPrefs(context).getString(SESSION_ID_KEY, null)
    }

    fun clearSession(context: Context) {
        getPrefs(context).edit().remove(SESSION_ID_KEY).apply()
    }
}