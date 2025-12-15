package data.repository

import com.example.mente_libre_app.data.remote.dto.*
import com.example.mente_libre_app.data.repository.UserProfileRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import com.example.mente_libre_app.data.local.TokenDataStore
import com.example.mente_libre_app.data.remote.api.AuthApi
import com.example.mente_libre_app.data.remote.dto.auth.*
import com.example.mente_libre_app.data.repository.UserRepository
import io.mockk.*
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody

import retrofit2.Response

class UserRepositoryTest {

    private lateinit var repository: UserRepository
    private val api: AuthApi = mockk()
    private val dataStore: TokenDataStore = mockk(relaxed = true)

    private val fakeLogin = LoginResponseDto(
        token = "TOKEN123",
        userId = 1L,
        username = "Maria",
        email = "correo@test.com",
        phone = "12345678",
        role = "USER"
    )

    private val fakeUser = UserResponseDto(
        id = 1L,
        username = "Maria",
        email = "correo@test.com",
        phone = "12345678",
        rol = "USER"
    )

    @Before
    fun setup() {
        repository = UserRepository(dataStore, api)
    }

    // ----------------------------------------------------
    // 1. LOGIN EXITOSO
    // ----------------------------------------------------
    @Test
    fun `login exitoso retorna Result success`() = runTest {

        coEvery { api.login(any()) } returns Response.success(fakeLogin)

        val result = repository.login("correo@test.com", "1234")

        assertTrue(result.isSuccess)
        assertEquals("TOKEN123", result.getOrNull()?.token)
        coVerify { dataStore.saveToken("TOKEN123") }
        coVerify { dataStore.saveUserId(1L) }
    }

    // ----------------------------------------------------
    // 2. LOGIN FALLIDO (API retorna error)
    // ----------------------------------------------------
    @Test
    fun `login fallido retorna Result failure`() = runTest {
        coEvery { api.login(any()) } returns Response.error(
            401,
            ResponseBody.create("application/json".toMediaTypeOrNull(), "Unauthorized")
        )

        val result = repository.login("correo@test.com", "mala")

        assertTrue(result.isFailure)
    }

    // ----------------------------------------------------
    // 3. REGISTER EXITOSO
    // ----------------------------------------------------
    @Test
    fun `register exitoso retorna usuario`() = runTest {
        coEvery { api.register(any()) } returns Response.success(fakeUser)

        val result = repository.register(
            RegisterRequestDto("Maria", "correo@test.com", "12345678", "1234")
        )

        assertTrue(result.isSuccess)
        assertEquals("Maria", result.getOrNull()?.username)
    }

    // ----------------------------------------------------
    // 4. REGISTER FALLIDO
    // ----------------------------------------------------
    @Test
    fun `register fallido retorna error`() = runTest {
        coEvery { api.register(any()) } returns Response.error(
            400,
            ResponseBody.create("application/json".toMediaTypeOrNull(), "Bad Request")
        )

        val result = repository.register(
            RegisterRequestDto("Maria", "correo@test.com", "12345678", "1234")
        )

        assertTrue(result.isFailure)
    }

    // ----------------------------------------------------
    // 5. GET PROFILE (token ok)
    // ----------------------------------------------------
    @Test
    fun `getProfile exitoso retorna usuario`() = runTest {
        coEvery { dataStore.tokenFlow } returns flowOf("TOKEN123")
        coEvery { api.getProfile(any()) } returns Response.success(fakeUser)

        val result = repository.getProfile()

        assertTrue(result.isSuccess)
        assertEquals("Maria", result.getOrNull()?.username)
    }

    // ----------------------------------------------------
    // 6. GET PROFILE SIN TOKEN
    // ----------------------------------------------------
    @Test
    fun `getProfile sin token retorna error`() = runTest {
        coEvery { dataStore.tokenFlow } returns flowOf(null)

        val result = repository.getProfile()

        assertTrue(result.isFailure)
    }

    // ----------------------------------------------------
    // 7. CHANGE PASSWORD EXITOSO
    // ----------------------------------------------------
    @Test
    fun `changePassword exitoso retorna success`() = runTest {
        coEvery { dataStore.tokenFlow } returns flowOf("TOKEN123")
        coEvery { api.changePassword(any(), any()) } returns Response.success(
            ResponseBody.create(null, "")
        )

        val result = repository.changePassword("actual", "nueva")

        assertTrue(result.isSuccess)
    }

    // ----------------------------------------------------
    // 8. CHANGE PASSWORD FALLIDO
    // ----------------------------------------------------
    @Test
    fun `changePassword fallido retorna mensaje de error`() = runTest {
        coEvery { dataStore.tokenFlow } returns flowOf("TOKEN123")
        coEvery { api.changePassword(any(), any()) } returns Response.error(
            400,
            ResponseBody.create("application/text".toMediaTypeOrNull(), "Incorrect password")
        )

        val result = repository.changePassword("actual", "mala")

        assertTrue(result.isFailure)
        assertEquals("Incorrect password", result.exceptionOrNull()?.message)
    }

    // ----------------------------------------------------
    // 9. DELETE ACCOUNT EXITOSO
    // ----------------------------------------------------
    @Test
    fun `deleteAccount exitoso retorna success`() = runTest {
        coEvery { dataStore.tokenFlow } returns flowOf("TOKEN123")
        coEvery { api.deleteMyAccount(any()) } returns Response.success(
            ResponseBody.create(null, "")
        )

        val result = repository.deleteAuthAccount()
        assertTrue(result.isSuccess)
    }

    // ----------------------------------------------------
    // 10. DELETE ACCOUNT FALLIDO
    // ----------------------------------------------------
    @Test
    fun `deleteAccount fallido retorna error`() = runTest {
        coEvery { dataStore.tokenFlow } returns flowOf("TOKEN123")
        coEvery { api.deleteMyAccount(any()) } returns Response.error(
            500,
            ResponseBody.create("application/json".toMediaTypeOrNull(), "Server error")
        )

        val result = repository.deleteAuthAccount()
        assertTrue(result.isFailure)
    }
}