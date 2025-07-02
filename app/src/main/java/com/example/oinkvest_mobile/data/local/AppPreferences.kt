package com.example.oinkvest_mobile.data.local

import android.content.Context
import android.content.SharedPreferences

object AppPreferences {

    private const val PREFS_NAME = "app_general_preferences"
    private const val KEY_ONBOARDING_COMPLETED = "onboarding_completed"

    private fun getSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun isOnboardingCompleted(context: Context): Boolean {
        val prefs = getSharedPreferences(context)
        return prefs.getBoolean(KEY_ONBOARDING_COMPLETED, false)
    }

    fun setOnboardingCompleted(context: Context) {
        val prefs = getSharedPreferences(context)
        prefs.edit().putBoolean(KEY_ONBOARDING_COMPLETED, true).apply()
    }
}