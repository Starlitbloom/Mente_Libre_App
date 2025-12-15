package com.example.mente_libre_app.ui.viewmodel

import com.example.mente_libre_app.data.remote.dto.auth.LoginResponseDto
import com.example.mente_libre_app.data.remote.dto.auth.UserResponseDto
import com.example.mente_libre_app.data.repository.UserRepository
import com.example.mente_libre_app.ui.viewmodel.AuthViewModel
import io.mockk.coEvery
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class AuthViewModelTest {

    private lateinit var viewModel: AuthViewModel
    private val repo: UserRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        viewModel = AuthViewModel(repo)
    }

    // --------------------------------------------------------
    // 1. VALIDACIÓN DE CAMPOS LOGIN
    @Test
    fun `login no permite enviar si campos estan vacios`() = runTest {
        viewModel.onLoginEmailChange("")
        viewModel.onLoginPassChange("")

        val state = viewModel.login.value
        assertFalse(state.canSubmit)
    }

    @Test
    fun `login permite enviar cuando email y pass estan completos`() = runTest {
        viewModel.onLoginEmailChange("correo@test.com")
        viewModel.onLoginPassChange("123456")

        val state = viewModel.login.value
        assertTrue(state.canSubmit)
    }

    // --------------------------------------------------------
    // 2. LOGIN EXITOSO
    @Test
    fun `login exitoso actualiza success en true`() = runTest {
        val fakeResponse = LoginResponseDto(
            token = "ABC123",
            userId = 10L,
            username = "Maria",
            email = "correo@test.com",
            phone = "123456",
            role = "USER"
        )

        coEvery { repo.login(any(), any()) } returns Result.success(fakeResponse)

        viewModel.onLoginEmailChange("correo@test.com")
        viewModel.onLoginPassChange("123456")
        viewModel.submitLogin()

        val state = viewModel.login.value
        assertTrue(state.success)
        assertNull(state.errorMsg)
    }

    // --------------------------------------------------------
    // 3. LOGIN FALLIDO
    // --------------------------------------------------------
    @Test
    fun `login fallido muestra mensaje de error`() = runTest {
        coEvery { repo.login(any(), any()) } returns Result.failure(Exception("Credenciales incorrectas"))

        viewModel.onLoginEmailChange("correo@test.com")
        viewModel.onLoginPassChange("mala")
        viewModel.submitLogin()

        val state = viewModel.login.value
        assertEquals("Credenciales incorrectas", state.errorMsg)
        assertFalse(state.success)
    }

    // --------------------------------------------------------
    // 4. CAMBIO DE CONTRASEÑA (error en confirmar)
    // --------------------------------------------------------
    @Test
    fun `changePassword falla si las contrasenas no coinciden`() = runTest {
        var resultMessage = ""
        var resultSuccess = true

        viewModel.changePassword(
            actual = "1234",
            nueva = "abcd",
            confirmar = "noCoincide"
        ) {
            resultSuccess = it.success
            resultMessage = it.errorMsg ?: ""
        }

        assertFalse(resultSuccess)
        assertEquals("Las contraseñas nuevas no coinciden", resultMessage)
    }

    // --------------------------------------------------------
    // 5. CAMBIO DE CONTRASEÑA EXITOSO
    // --------------------------------------------------------
    @Test
    fun `changePassword exitoso devuelve success true`() = runTest {
        coEvery { repo.changePassword(any(), any()) } returns Result.success(Unit)

        var success = false

        viewModel.changePassword(
            actual = "1234",
            nueva = "abcd",
            confirmar = "abcd"
        ) {
            success = it.success
        }

        assertTrue(success)
    }

    // --------------------------------------------------------
// 6. VALIDACIÓN DE CAMPOS REGISTRO
// --------------------------------------------------------
    @Test
    fun `registro no permite enviar si hay campos vacios`() = runTest {
        viewModel.onNameChange("")
        viewModel.onEmailChange("")
        viewModel.onPhoneChange("")
        viewModel.onPassChange("")
        viewModel.onConfirmChange("")

        val state = viewModel.register.value

        assertFalse(state.canSubmit)
    }

    @Test
    fun `registro permite enviar cuando todos los campos tienen datos`() = runTest {
        viewModel.onNameChange("Maria")
        viewModel.onEmailChange("correo@test.com")
        viewModel.onPhoneChange("123456789")
        viewModel.onPassChange("1234")
        viewModel.onConfirmChange("1234")

        val state = viewModel.register.value

        assertTrue(state.canSubmit)
    }

    // --------------------------------------------------------
// 7. REGISTRO EXITOSO (incluye login automático)
// --------------------------------------------------------
    @Test
    fun `registro exitoso actualiza success en true`() = runTest {

        // Fake respuesta del register
        val fakeUser = UserResponseDto(
            id = 1L,
            username = "Maria",
            email = "correo@test.com",
            phone = "12345678",
            rol = "USER"
        )


        // Fake respuesta del login automático
        val fakeLogin = LoginResponseDto(
            token = "TOKEN123",
            userId = 1L,
            username = "Maria",
            email = "correo@test.com",
            phone = "123456",
            role = "USER"
        )

        coEvery { repo.register(any()) } returns Result.success(fakeUser)
        coEvery { repo.login(fakeUser.email, any()) } returns Result.success(fakeLogin)

        // Llenamos campos
        viewModel.onNameChange("Maria")
        viewModel.onEmailChange("correo@test.com")
        viewModel.onPhoneChange("123456")
        viewModel.onPassChange("abcd")
        viewModel.onConfirmChange("abcd")

        viewModel.submitRegister()

        val state = viewModel.register.value

        assertTrue(state.success)
        assertNull(state.errorMsg)
    }

    // --------------------------------------------------------
// 8. REGISTRO FALLIDO (API retorna error)
// --------------------------------------------------------
    @Test
    fun `registro fallido muestra errorMsg`() = runTest {
        coEvery { repo.register(any()) } returns Result.failure(Exception("Email ya registrado"))

        // Llenamos campos
        viewModel.onNameChange("Maria")
        viewModel.onEmailChange("correo@test.com")
        viewModel.onPhoneChange("123456")
        viewModel.onPassChange("abcd")
        viewModel.onConfirmChange("abcd")

        viewModel.submitRegister()

        val state = viewModel.register.value

        assertEquals("Email ya registrado", state.errorMsg)
        assertFalse(state.success)
    }

    // --------------------------------------------------------
// 9. ERROR EN LOGIN AUTOMÁTICO DESPUÉS DEL REGISTRO
// --------------------------------------------------------
    @Test
    fun `registro exitoso pero login automatico falla`() = runTest {
        val fakeUser = UserResponseDto(
            id = 1L,
            username = "Maria",
            email = "correo@test.com",
            phone = "12345678",
            rol = "USER"
        )


        coEvery { repo.register(any()) } returns Result.success(fakeUser)
        coEvery { repo.login(fakeUser.email, any()) } returns
                Result.failure(Exception("Credenciales incorrectas"))

        // Llenar campos
        viewModel.onNameChange("Maria")
        viewModel.onEmailChange("correo@test.com")
        viewModel.onPhoneChange("123456")
        viewModel.onPassChange("abcd")
        viewModel.onConfirmChange("abcd")

        viewModel.submitRegister()

        val state = viewModel.register.value

        assertEquals(
            "Error al iniciar sesión automáticamente: Credenciales incorrectas",
            state.errorMsg
        )
        assertFalse(state.success)
    }

    // --------------------------------------------------------
// 10. CLEAR REGISTER RESULT
// --------------------------------------------------------
    @Test
    fun `clearRegisterResult resetea success y error`() = runTest {
        val fakeUser = UserResponseDto(
            id = 1L,
            username = "Maria",
            email = "correo@test.com",
            phone = "12345678",
            rol = "USER"
        )


        val fakeLogin = LoginResponseDto(
            token = "TOKEN123",
            userId = 1L,
            username = "Maria",
            email = "correo@test.com",
            phone = "123456",
            role = "USER"
        )

        coEvery { repo.register(any()) } returns Result.success(fakeUser)
        coEvery { repo.login(fakeUser.email, any()) } returns Result.success(fakeLogin)

        // Simulamos registro exitoso
        viewModel.onNameChange("Maria")
        viewModel.onEmailChange("correo@test.com")
        viewModel.onPhoneChange("123456")
        viewModel.onPassChange("abcd")
        viewModel.onConfirmChange("abcd")
        viewModel.submitRegister()

        // limpiar estado
        viewModel.clearRegisterResult()

        val state = viewModel.register.value

        assertFalse(state.success)
        assertNull(state.errorMsg)
    }

}