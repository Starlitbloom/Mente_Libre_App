package com.example.mente_libre_app.viewmodel

import com.example.mente_libre_app.data.repository.UserProfileRepository
import com.example.mente_libre_app.ui.viewmodel.UsuarioViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class UsuarioViewModelTest {

    private lateinit var repository: UserProfileRepository
    private lateinit var viewModel: UsuarioViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())

        repository = mockk()
        viewModel = UsuarioViewModel(repository)
    }

    @Test
    fun `cargarPerfil llena los StateFlows correctamente`() = runTest {
        val fake = UserProfileCombinedDto(
            id = 10,
            userId = 1,
            username = "Mao",
            email = "mao@mail.com",
            phone = "12345",
            direccion = "Calle x",
            fechaNacimiento = "2000-01-01",
            notificaciones = true,
            generoId = 2,
            generoNombre = "Femenino",
            fotoPerfil = "url.png"
        )


        coEvery { repository.getFullUserProfile(1) } returns Result.success(fake)

        viewModel.cargarPerfil(1)
        advanceUntilIdle()

        assertEquals("Mao", viewModel.nombre.value)
        assertEquals("mao@mail.com", viewModel.email.value)
        assertEquals("12345", viewModel.telefono.value)
    }

    @Test
    fun `si cargarPerfil falla, errorMsg se actualiza`() = runTest {
        coEvery { repository.getFullUserProfile(any()) } returns Result.failure(Exception("Network error"))

        viewModel.cargarPerfil(1)
        advanceUntilIdle()

        assertEquals("Network error", viewModel.errorMsg.value)
    }
}
