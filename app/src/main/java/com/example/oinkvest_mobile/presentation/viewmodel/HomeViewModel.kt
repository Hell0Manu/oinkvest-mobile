package com.example.oinkvest_mobile.presentation.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.oinkvest_mobile.data.local.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _showDialog = MutableStateFlow(false)
    val showDialog: StateFlow<Boolean> = _showDialog

    private val _isBiometricEnabled = MutableStateFlow(false)
    val isBiometricEnabled: StateFlow<Boolean> = _isBiometricEnabled

    fun onFingerprintIconClicked(context: Context) {
        _isBiometricEnabled.value = TokenManager.isBiometricEnabled(context)
        _showDialog.value = true
    }

    fun confirmBiometricChange(context: Context) {

        val newState = !_isBiometricEnabled.value
        TokenManager.setBiometricEnabled(context, newState)

        if (!newState) {
            TokenManager.clearToken(context)
        }

        _showDialog.value = false
    }

    fun dismissDialog() {
        _showDialog.value = false
    }
}