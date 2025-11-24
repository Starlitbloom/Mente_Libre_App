package com.example.mente_libre_app.data.remote.repository

import android.content.Context
import com.example.mente_libre_app.data.local.TokenManager
import com.example.mente_libre_app.data.remote.api.EvaluationApi
import com.example.mente_libre_app.data.remote.core.RetrofitInstance
import com.example.mente_libre_app.data.remote.dto.evaluation.*

class EvaluationRepository(private val context: Context) {

    private val api: EvaluationApi by lazy {
        RetrofitInstance.getEvaluationService(context)
    }

    private val tokenManager = TokenManager(context)

    // ---------------------------------------------
    //  GUARDAR EVALUACIÓN DIARIA (Bitácora)
    // ---------------------------------------------
    suspend fun saveDailyEvaluation(request: DailyEvaluationRequestDto): DailyEvaluationResponseDto {
        val userId = tokenManager.getUserId() ?: throw Exception("User ID no encontrado")
        return api.saveDailyEvaluation(userId, request)
    }

    // ---------------------------------------------
    //  GUARDAR ENTRADA DE GRATITUD (Diario)
    // ---------------------------------------------
    suspend fun saveGratitudeEntry(request: GratitudeEntryRequestDto): GratitudeEntryResponseDto {
        val userId = tokenManager.getUserId() ?: throw Exception("User ID no encontrado")
        return api.saveGratitudeEntry(userId, request)
    }
}
