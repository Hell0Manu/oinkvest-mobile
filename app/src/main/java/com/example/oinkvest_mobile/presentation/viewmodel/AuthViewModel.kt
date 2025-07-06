package com.example.oinkvest_mobile.presentation.viewmodel

import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.oinkvest_mobile.data.local.SessionManager
import com.example.oinkvest_mobile.data.local.TokenManager
import com.example.oinkvest_mobile.data.remote.RetrofitClient
import com.example.oinkvest_mobile.data.remote.api.LoginRequest
import com.example.oinkvest_mobile.data.remote.api.RegisterRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {

    private val _loginResult = MutableStateFlow<String?>(null)
    val loginResult: StateFlow<String?> = _loginResult

    private val _showBiometricDialog = MutableStateFlow(false)
    val showBiometricDialog: StateFlow<Boolean> = _showBiometricDialog

    private val _navigateToHome = MutableStateFlow(false)
    val navigateToHome: StateFlow<Boolean> = _navigateToHome

    private var pendingToken: String? = null

    private val _registerResult = MutableStateFlow<String?>(null)
    val registerResult: StateFlow<String?> = _registerResult

    fun attemptBiometricLogin(activity: FragmentActivity) {
        viewModelScope.launch {

            val executor = ContextCompat.getMainExecutor(activity)

            // Cria o callback com as ações para cada resultado da autenticação
            val callback = object : BiometricPrompt.AuthenticationCallback() {

                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    Log.i("AuthViewModel", "Biometria autenticada com sucesso!")
                    val token = TokenManager.getToken(activity)
                    if (token != null) {
                        _navigateToHome.value = true
                    } else {
                        _loginResult.value = "Token não encontrado. Faça login com a senha."
                    }
                }

                // Chamado quando o usuário cancela ou ocorre um erro recuperável
                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                    Log.e("AuthViewModel", "Erro de autenticação: $errorCode - $errString")
                }

                // Chamado quando a digital não é reconhecida
                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    Log.e("AuthViewModel", "Biometria falhou. Digital não reconhecida.")
                }
            }


            val biometricPrompt = BiometricPrompt(activity, executor, callback)

            val promptInfo = BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login Oinkvest")
                .setSubtitle("Use sua digital para entrar")
                .setNegativeButtonText("Usar senha")
                .build()

            biometricPrompt.authenticate(promptInfo)
        }
    }

    fun login(email: String, password: String, context: Context) {

        viewModelScope.launch {
            try {
                val loginRequest = LoginRequest(email = email, password = password)
                val response = RetrofitClient.authApi.login(loginRequest)

                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    //val result = response.body()
                    //val finalUrl = response.raw().request.url.toString()
                    if (loginResponse != null) {

                        //pendingToken = result.token

                        //SessionManager.saveSessionId(context, loginResponse.token)

                        // Verifica se o dispositivo suporta biometria
                        //val biometricManager = BiometricManager.from(context)
                        //val canAuthenticate = biometricManager.canAuthenticate(BIOMETRIC_STRONG) == BiometricManager.BIOMETRIC_SUCCESS
                        /*
                        if (canAuthenticate && !TokenManager.hasUserRespondedToPrompt(context)) {

                            // Se suporta, mostra o diálogo
                            _showBiometricDialog.value = true
                        } else {
                        */
                        // Se não suporta, apenas navega para a home
                        _navigateToHome.value = true
                        //}
                    } else {

                        _loginResult.value = "Erro no servidor."
                        Log.e("AuthViewModel", "Resposta inválida do servidor.")
                    }
                } else {
                    if (response.code() == 401) { // 401 Unauthorized
                        _loginResult.value = "Email ou senha inválidos."
                        Log.w("AuthViewModel", "Falha na autenticação (401).")
                    } else {
                        _loginResult.value = "Erro no servidor: Código ${response.code()}"
                        Log.e("AuthViewModel", "Erro inesperado do servidor: ${response.code()}")
                    }
                }
            } catch (e: Exception) {
                _loginResult.value = "Erro de rede: ${e.localizedMessage}"
                Log.e("AuthViewModel", "Exceção na chamada de rede", e)
            }
        }
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            try {
                val response =
                    RetrofitClient.authApi.register(RegisterRequest(name, email, password))
                if (response.isSuccessful) {
                    Log.i("AuthViewModel", "resposta: $response")
                    Log.i("AuthViewModel", "resposta: ${response.body()}")
                    val result = response.body()
                    if (result?.status == "success") {
                        Log.i("AuthViewModel", "cadastrado")
                        _registerResult.value = "success"
                    } else {
                        Log.e("AuthViewModel", "campo invalido")
                        _registerResult.value = result?.message ?: "algum campo inválido"
                    }
                } else {
                    _registerResult.value = "Erro no servidor"
                }
            } catch (e: Exception) {
                _registerResult.value = "Erro de rede: ${e.localizedMessage}"
            }
        }
    }

    fun onBiometricPromptConfirmed(context: Context) {
        pendingToken?.let { token ->
            TokenManager.saveToken(context, token)
            TokenManager.setBiometricEnabled(context, true)
        }

        TokenManager.setUserHasRespondedToPrompt(context)
        _showBiometricDialog.value = false
        _navigateToHome.value = true
    }

    fun onBiometricPromptDismissed(context: Context) {
        TokenManager.setBiometricEnabled(context, false)

        TokenManager.setUserHasRespondedToPrompt(context)
        _showBiometricDialog.value = false
        _navigateToHome.value = true
    }

    fun onNavigationComplete() {
        _navigateToHome.value = false
        _loginResult.value = null
    }


}