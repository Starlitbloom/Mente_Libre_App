package com.example.mente_libre_app.data.remote.api

import com.example.mente_libre_app.data.remote.dto.emotion.MoodRecordDto
import com.example.mente_libre_app.data.remote.dto.emotion.MoodScoreSummaryDto
import retrofit2.Response
import retrofit2.http.*

interface EmotionApi {

    @POST("emotion/api/moods/register")
    suspend fun registerMood(
        @Query("userId") userId: Long,
        @Query("emotionLabel") emotionLabel: String,
        @Query("context") context: String? = null,
        @Query("date") date: String? = null
    ): Response<MoodRecordDto>

    @GET("emotion/api/moods/range")
    suspend fun getRange(
        @Query("userId") userId: Long,
        @Query("start") start: String,
        @Query("end") end: String
    ): Response<List<MoodRecordDto>>

    @GET("emotion/api/moods/month")
    suspend fun getMonth(
        @Query("userId") userId: Long,
        @Query("year") year: Int,
        @Query("month") month: Int
    ): Response<List<MoodRecordDto>>

    @GET("emotion/api/moods/year")
    suspend fun getYear(
        @Query("userId") userId: Long,
        @Query("year") year: Int
    ): Response<List<MoodRecordDto>>

    @GET("emotion/api/moods/last-days")
    suspend fun getLastDays(
        @Query("userId") userId: Long,
        @Query("days") days: Int
    ): Response<MoodScoreSummaryDto>

}