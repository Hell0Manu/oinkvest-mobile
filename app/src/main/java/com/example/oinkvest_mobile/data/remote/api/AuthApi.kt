package com.example.oinkvest_mobile.data.remote.api

import com.google.gson.annotations.SerializedName
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response

data class LoginRequest(val email: String, @SerializedName("senha") val password: String)
data class LoginResponse(val id: Long, val nome: String, val email: String, val token: String)
data class RegisterRequest(val name: String, val email: String, val password: String)
data class RegisterResponse(val status: String, val message: String)

interface AuthApi {

    @POST("/api/mobile/login")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @POST("/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

}