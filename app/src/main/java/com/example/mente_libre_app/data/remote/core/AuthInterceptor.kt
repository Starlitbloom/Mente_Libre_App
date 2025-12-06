package com.example.mente_libre_app.data.remote.core

import com.example.mente_libre_app.data.local.TokenDataStore
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import kotlinx.coroutines.flow.firstOrNull

class AuthInterceptor(
    private val tokenDataStore: TokenDataStore
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        val token = runBlocking { tokenDataStore.tokenFlow.firstOrNull() }

        if (!token.isNullOrEmpty()) {
            request = request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        }

        return chain.proceed(request)
    }
}
