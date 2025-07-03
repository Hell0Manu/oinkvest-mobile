package com.example.oinkvest_mobile.data.remote

import com.example.oinkvest_mobile.data.remote.api.AuthApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {

    private const val BASE_URL = "http://oinkvest.ddns.net:8080"

    // 1. Cria o interceptor de logging
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        // Define o nível de detalhe do log. BODY é o mais completo.
        level = HttpLoggingInterceptor.Level.BODY
    }

    // 2. Cria um cliente OkHttp customizado e adiciona o interceptor a ele
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

    // 3. Constrói a instância do Retrofit usando o cliente customizado
    val authApi: AuthApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            // Adiciona o nosso cliente com o "espião" ao Retrofit
            .client(client)
            .build()
            .create(AuthApi::class.java)
    }
}

