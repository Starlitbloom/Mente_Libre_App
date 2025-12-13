package com.example.mente_libre_app.data.repository

import android.content.Context
import android.net.Uri
import com.example.mente_libre_app.data.remote.api.StorageApi
import com.example.mente_libre_app.data.remote.dto.storage.FileResponseDto
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class StorageRepository(private val api: StorageApi) {

    private fun uriToFile(context: Context, uri: Uri): File {
        val inputStream = context.contentResolver.openInputStream(uri)
        val file = File.createTempFile("upload_", ".jpg", context.cacheDir)
        file.outputStream().use { output ->
            inputStream?.copyTo(output)
        }
        return file
    }

    suspend fun uploadProfileImage(context: Context, uri: Uri, token: String): FileResponseDto {
        val file = uriToFile(context, uri)

        // 🔥 Obtener MIME real desde el URI
        val mimeType = context.contentResolver.getType(uri) ?: "image/jpeg"

        // 🔥 Usar MIME correcto, NO "image/*"
        val requestFile = file.asRequestBody(mimeType.toMediaTypeOrNull())

        val multipart = MultipartBody.Part.createFormData(
            "file",
            file.name,
            requestFile
        )

        val category = "PROFILE".toRequestBody("text/plain".toMediaTypeOrNull())

        return api.uploadImage(
            multipart,
            category
        )
    }
}
