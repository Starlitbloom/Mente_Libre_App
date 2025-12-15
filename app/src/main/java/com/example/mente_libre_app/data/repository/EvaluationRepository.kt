package com.example.mente_libre_app.data.repository

import com.example.mente_libre_app.data.local.TokenDataStore
import com.example.mente_libre_app.data.remote.api.EvaluationApi
import com.example.mente_libre_app.data.remote.dto.evaluation.*
import kotlinx.coroutines.flow.first

class EvaluationRepository(
    private val api: EvaluationApi,
    private val tokenStore: TokenDataStore
) {

    suspend fun saveDailyEvaluation(
        userId: Long,
        request: DailyEvaluationRequestDto
    ): Result<DailyEvaluationResponseDto> = try {

        val response = api.saveDailyEvaluation(userId, request)
        Result.success(response)

    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun saveGratitudeEntry(
        userId: Long,
        request: GratitudeEntryRequestDto
    ): Result<GratitudeEntryResponseDto> = try {

        val response = api.saveGratitudeEntry(userId, request)
        Result.success(response)

    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun getGratitudeEntries(userId: Long): List<GratitudeEntryResponseDto> {
        return api.getGratitudeEntries(userId)
    }

}