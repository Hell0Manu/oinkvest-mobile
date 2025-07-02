package com.example.oinkvest_mobile.data.remote.api

import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded

data class LoginRequest(val email: String, val password: String)
//data class LoginResponse(val status: String, val token: String?, val message: String)
data class RegisterRequest(val name: String, val email: String, val password: String)
data class RegisterResponse(val status: String, val message: String)

interface AuthApi {
    @FormUrlEncoded
    @POST("/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Response<ResponseBody>

    @POST("/register")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>

}