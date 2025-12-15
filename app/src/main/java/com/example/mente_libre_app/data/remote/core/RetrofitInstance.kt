package com.example.mente_libre_app.data.remote.core

import android.content.Context
import com.example.mente_libre_app.data.local.TokenDataStore
import com.example.mente_libre_app.data.remote.api.AdminApi
import com.example.mente_libre_app.data.remote.api.AuthApi
import com.example.mente_libre_app.data.remote.api.ChatApi
import com.example.mente_libre_app.data.remote.api.EmotionApi
import com.example.mente_libre_app.data.remote.api.EvaluationApi
import com.example.mente_libre_app.data.remote.api.StorageApi
import com.example.mente_libre_app.data.remote.api.UserProfileApi
import com.example.mente_libre_app.data.remote.api.VirtualPetApi
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

    // ------------------------------------------------------
    // AUTH API
    // ------------------------------------------------------
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

    // ------------------------------------------------------
    // USERPROFILE API
    // ------------------------------------------------------
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
    // STORAGE API
    // ------------------------------------------------------
    fun createStorageApi(context: Context): StorageApi {
        val client = createClient(context)

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(StorageApi::class.java)
    }

    // ------------------------------------------------------
    // VIRTUALPET API
    // ------------------------------------------------------
    fun createVirtualPetApi(context: Context): VirtualPetApi {
        val client = createClient(context)

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(VirtualPetApi::class.java)
    }

    // ------------------------------------------------------
    // EVALUATION API  (Diario + Gratitud)
    // ------------------------------------------------------
    fun createEvaluationApi(context: Context): EvaluationApi {
        val client = createClient(context)

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // Gateway
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EvaluationApi::class.java)
    }

    //------------------------------------------------------
    // EMOTION API
    // ------------------------------------------------------
    fun createEmotionApi(context: Context): EmotionApi {
        val client = createClient(context)

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // Gateway
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(EmotionApi::class.java)
    }

    //------------------------------------------------------
    // ADMIN API
    // ------------------------------------------------------
    fun createAdminApi(context: Context): AdminApi {
        val client = createClient(context)

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // gateway
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AdminApi::class.java)
    }

    //------------------------------------------------------
    // CHAT API
    // ------------------------------------------------------
    fun createChatApi(context: Context): ChatApi {
        val client = createClient(context)

        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/") // Gateway
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatApi::class.java)
    }

}
