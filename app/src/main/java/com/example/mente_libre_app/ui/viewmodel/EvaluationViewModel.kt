package com.example.mente_libre_app.data.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mente_libre_app.data.local.TokenDataStore
import com.example.mente_libre_app.data.remote.core.RetrofitInstance
import com.example.mente_libre_app.data.remote.dto.evaluation.DailyEvaluationRequestDto
import com.example.mente_libre_app.data.remote.dto.evaluation.GratitudeEntryRequestDto
import com.example.mente_libre_app.data.remote.dto.evaluation.GratitudeEntryResponseDto
import com.example.mente_libre_app.data.repository.EvaluationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class EvaluationViewModel(context: Context) : ViewModel() {

    private val tokenStore = TokenDataStore(context)
    private val api = RetrofitInstance.createEvaluationApi(context)
    private val repo = EvaluationRepository(api, tokenStore)

    // ---------------------------------------------------------
    // Estados generales del ViewModel
    // ---------------------------------------------------------
    var isLoading: Boolean = false
        private set

    var errorMessage: String? = null
        private set

    val saveResult = MutableStateFlow<String?>(null)


    // ---------------------------------------------------------
    // GUARDAR DAILY EVALUATION
    // ---------------------------------------------------------
    fun saveDaily(
        userId: Long,
        dto: DailyEvaluationRequestDto
    ) {
        viewModelScope.launch {
            repo.saveDailyEvaluation(userId, dto).fold(
                onSuccess = { saveResult.value = "OK" },
                onFailure = { saveResult.value = it.message }
            )
        }
    }

    // ---------------------------------------------------------
    // GUARDAR GRATITUDE ENTRY
    // ---------------------------------------------------------
    fun saveGratitude(
        userId: Long,
        dto: GratitudeEntryRequestDto
    ) {
        viewModelScope.launch {
            repo.saveGratitudeEntry(userId, dto).fold(
                onSuccess = { saveResult.value = "OK" },
                onFailure = { saveResult.value = it.message }
            )
        }
    }

    // ---------------------------------------------------------
    // LISTAR ENTRADAS DE GRATITUD
    // ---------------------------------------------------------
    private val _gratitudeList = MutableStateFlow<List<GratitudeEntryResponseDto>>(emptyList())
    val gratitudeList = _gratitudeList.asStateFlow()

    fun loadGratitudeEntries(userId: Long) {
        viewModelScope.launch {
            try {
                isLoading = true
                errorMessage = null

                val list = repo.getGratitudeEntries(userId)
                _gratitudeList.value = list

            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }
}