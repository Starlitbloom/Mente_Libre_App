package com.example.mente_libre_app.data.repository

import com.example.mente_libre_app.data.remote.api.EmotionApi

class EmotionRepository(
    private val api: EmotionApi
) {

    suspend fun registerMood(
        userId: Long,
        label: String,
        context: String? = null
    ) = api.registerMood(userId, label, context)

    suspend fun getMonth(userId: Long, year: Int, month: Int) =
        api.getMonth(userId, year, month)

    suspend fun getYear(userId: Long, year: Int) =
        api.getYear(userId, year)

    suspend fun getRange(userId: Long, start: String, end: String) =
        api.getRange(userId, start, end)

    suspend fun getLastDays(userId: Long, days: Int = 30) =
        api.getLastDays(userId, days)
}