package com.example.mente_libre_app.viewmodel

import android.content.Context
import com.example.mente_libre_app.data.remote.dto.LoginResponseDto
import com.example.mente_libre_app.data.remote.dto.RegisterRequestDto
import com.example.mente_libre_app.data.repository.UserRepository
import com.example.mente_libre_app.ui.viewmodel.AuthViewModel
import io.mockk.*
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    private lateinit var viewModel: AuthViewModel
    private lateinit var repository: UserRepository
    private lateinit var context: Context

    private val dispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(dispatcher)

        repository = mockk()
        context = mockk()

        coEvery { repository.getCurrentUserId() } returns null

        viewModel = AuthViewModel(repository, context)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `cuando cambia el email, el state se actualiza`() = runTest {
        viewModel.onLoginEmailChange("test@mail.com")

        val state = viewModel.login.first()
        assertEquals("test@mail.com", state.email)
    }

    @Test
    fun `cuando login es exitoso, success debe ser true`() = runTest {
        coEvery { repository.login("mail@mail.com", "1234") } returns Result.success(
            LoginResponseDto(
                token = "abc123",
                userId = 10L
            )
        )


        viewModel.onLoginEmailChange("mail@mail.com")
        viewModel.onLoginPassChange("1234")

        viewModel.submitLogin()
        advanceUntilIdle()

        val state = viewModel.login.first()

        assertEquals(true, state.success)
        assertEquals(10L, viewModel.currentUserId.value)
    }

    @Test
    fun `cuando login falla, success = false y errorMsg contiene mensaje`() = runTest {
        coEvery { repository.login(any(), any()) } returns Result.failure(Exception("Credenciales inválidas"))

        viewModel.onLoginEmailChange("mail@mail.com")
        viewModel.onLoginPassChange("1234")

        viewModel.submitLogin()
        advanceUntilIdle()

        val state = viewModel.login.first()

        assertEquals(false, state.success)
        assertEquals("Credenciales inválidas", state.errorMsg)
    }

    @Test
    fun `register exitoso actualiza success en true`() = runTest {
        coEvery { repository.register(any()) } returns Result.success(Unit)

        viewModel.onNameChange("Juan")
        viewModel.onEmailChange("email@mail.com")
        viewModel.onPhoneChange("12345")
        viewModel.onPassChange("1234")
        viewModel.onConfirmChange("1234")

        viewModel.submitRegister()
        advanceUntilIdle()

        val state = viewModel.register.first()
        assertEquals(true, state.success)
    }

    @Test
    fun `register fallido retorna mensaje de error`() = runTest {
        coEvery { repository.register(any()) } returns Result.failure(Exception("Error de servidor"))

        viewModel.onNameChange("Juan")
        viewModel.onEmailChange("email@mail.com")
        viewModel.onPhoneChange("12345")
        viewModel.onPassChange("1234")
        viewModel.onConfirmChange("1234")

        viewModel.submitRegister()
        advanceUntilIdle()

        val state = viewModel.register.first()
        assertEquals("Error de servidor", state.errorMsg)
    }
}
