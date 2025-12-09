package com.example.mente_libre_app.data.remote.api

import com.example.mente_libre_app.data.remote.dto.storage.FileResponseDto
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface StorageApi {

    @Multipart
    @POST("/api/v1/storage/upload")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("category") category: RequestBody
    ): FileResponseDto
}