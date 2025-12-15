package com.example.mente_libre_app.data.remote.api

import com.example.mente_libre_app.data.remote.dto.evaluation.DailyEvaluationRequestDto
import com.example.mente_libre_app.data.remote.dto.evaluation.DailyEvaluationResponseDto
import com.example.mente_libre_app.data.remote.dto.evaluation.GratitudeEntryRequestDto
import com.example.mente_libre_app.data.remote.dto.evaluation.GratitudeEntryResponseDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface EvaluationApi {

    // -------- DAILY EVALUATION (Bitácora) --------
    @POST("api/evaluations/daily")
    suspend fun saveDailyEvaluation(
        @Query("userId") userId: Long,
        @Body request: DailyEvaluationRequestDto
    ): DailyEvaluationResponseDto

    // -------- GRATITUDE ENTRY (Diario de Gratitud) --------
    @POST("api/evaluations/gratitude")
    suspend fun saveGratitudeEntry(
        @Query("userId") userId: Long,
        @Body request: GratitudeEntryRequestDto
    ): GratitudeEntryResponseDto

    @GET("api/evaluations/gratitude")
    suspend fun getGratitudeEntries(
        @Query("userId") userId: Long
    ): List<GratitudeEntryResponseDto>

}
