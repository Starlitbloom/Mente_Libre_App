package com.example.mente_libre_app.data.remote.core

import android.content.Context
import com.example.mente_libre_app.data.local.TokenDataStore
import com.example.mente_libre_app.data.remote.api.AuthApi
import com.example.mente_libre_app.data.remote.api.StorageApi
import com.example.mente_libre_app.data.remote.api.UserProfileApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private fun createClient(context: Context): OkHttpClient {
        val tokenStore = TokenDataStore(context)

        return OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenStore))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()
    }

    fun createAuthApi(context: Context): AuthApi {
        val tokenStore = TokenDataStore(context)

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenStore))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    fun createUserProfileApi(context: Context): UserProfileApi {
        val tokenStore = TokenDataStore(context)

        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(tokenStore))
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .build()

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserProfileApi::class.java)
    }

    // ------------------------------------------------------
    // STORAGE API (El nuevo)
    // ------------------------------------------------------
    fun createStorageApi(context: Context): StorageApi {
        val client = createClient(context)

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")  // TU STORAGE SERVICE VA AQUÍ
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StorageApi::class.java)
    }
}
