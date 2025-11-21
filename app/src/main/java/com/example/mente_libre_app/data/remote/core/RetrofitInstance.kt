package com.example.mente_libre_app.data.remote.core

import android.content.Context
import com.example.mente_libre_app.data.local.TokenManager
import com.example.mente_libre_app.data.local.network.AuthInterceptor
import com.example.mente_libre_app.data.remote.api.AuthApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    fun getAuthService(context: Context): AuthApi {
        val tokenManager = TokenManager(context)

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenManager))
            .build()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // importante para Android
            .baseUrl("http://192.168.1.105:8080/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }
}
