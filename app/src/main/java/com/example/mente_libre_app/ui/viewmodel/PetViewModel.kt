package com.example.mente_libre_app.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mente_libre_app.data.remote.dto.virtualpet.CreatePetRequestDto
import com.example.mente_libre_app.data.remote.dto.virtualpet.PetResponseDto
import com.example.mente_libre_app.data.repository.VirtualPetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PetViewModel(
    private val repository: VirtualPetRepository
) : ViewModel() {

    private val _pet = MutableStateFlow<PetResponseDto?>(null)
    val pet: StateFlow<PetResponseDto?> = _pet

    private val _loading = MutableStateFlow(false)
    val loading = _loading

    fun createPet(name: String, type: String, avatarKey: String) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.createPet(CreatePetRequestDto(name, type, avatarKey))
            result.onSuccess { _pet.value = it }
            _loading.value = false
        }
    }

    fun loadMyPet() {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.getMyPet()
            result.onSuccess { _pet.value = it }
            _loading.value = false
        }
    }

    fun addPoints(points: Int, onResult: (Boolean) -> Unit = {}) {
        viewModelScope.launch {
            _loading.value = true
            val result = repository.addPoints(points)

            result.onSuccess { pet ->
                _pet.value = pet
                onResult(true)
            }.onFailure {
                onResult(false)
            }

            _loading.value = false
        }
    }

}