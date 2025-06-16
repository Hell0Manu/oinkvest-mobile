// Em data/local/TokenManager.kt
package com.example.oinkvest_mobile.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys

object TokenManager {

    private const val PREFS_NAME = "auth_prefs_secure"
    private const val TOKEN_KEY = "auth_token"
    private const val BIOMETRIC_ENABLED_KEY = "biometric_enabled"

    private const val HAS_RESPONDED_TO_PROMPT_KEY = "has_responded_to_biometric_prompt"

    private fun getEncryptedSharedPreferences(context: Context): SharedPreferences {
        val masterKeyAlias = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)

        // Ela exige que a gente especifique os esquemas de criptografia para as chaves e os valores.
        return EncryptedSharedPreferences.create(
            PREFS_NAME,
            masterKeyAlias,
            context,
            // Esquema de criptografia para os NOMES das chaves (ex: "auth_token")
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            // Esquema de criptografia para os VALORES (ex: o token "eyJ...")
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    fun saveToken(context: Context, token: String) {
        val prefs = getEncryptedSharedPreferences(context)
        prefs.edit().putString(TOKEN_KEY, token).apply()
    }

    fun clearToken(context: Context) {
        val prefs = getEncryptedSharedPreferences(context)
        prefs.edit().putString(TOKEN_KEY, "").apply()
    }

    fun setBiometricEnabled(context: Context, enabled: Boolean) {
        val prefs = getEncryptedSharedPreferences(context)
        prefs.edit().putBoolean(BIOMETRIC_ENABLED_KEY, enabled).apply()
    }

    fun getToken(context: Context): String? {
        val prefs = getEncryptedSharedPreferences(context)
        return prefs.getString(TOKEN_KEY, null)
    }

    fun isBiometricEnabled(context: Context): Boolean {
        val prefs = getEncryptedSharedPreferences(context)
        return prefs.getBoolean(BIOMETRIC_ENABLED_KEY, false)
    }

    fun setUserHasRespondedToPrompt(context: Context) {
        val prefs = getEncryptedSharedPreferences(context)
        prefs.edit().putBoolean(HAS_RESPONDED_TO_PROMPT_KEY, true).apply()
    }

    fun hasUserRespondedToPrompt(context: Context): Boolean {
        val prefs = getEncryptedSharedPreferences(context)
        return prefs.getBoolean(HAS_RESPONDED_TO_PROMPT_KEY, false)
    }

}