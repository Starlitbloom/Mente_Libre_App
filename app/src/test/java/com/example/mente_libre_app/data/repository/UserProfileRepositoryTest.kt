package data.repository

import com.example.mente_libre_app.data.remote.dto.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import org.mockito.kotlin.verify
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class UserProfileRepositoryTest {

    private lateinit var profileApi: UserProfileService
    private lateinit var authApi: AuthService
    private lateinit var repository: UserProfileRepository

    @Before
    fun setup() {
        profileApi = mock()
        authApi = mock()
        repository = UserProfileRepository(profileApi, authApi)
    }

    @Test
    fun `getFullUserProfile returns combined user correctly`() = runTest {
        val userId = 1L
        val profile = UserProfileDto(
            id = 100,
            userId = userId,
            direccion = "Calle Falsa 123",
            fechaNacimiento = "2000-01-01",
            notificaciones = true,
            generoId = 1,
            generoNombre = "Femenino",
            fotoPerfil = "foto.jpg"
        )
        val auth = AuthUserDto(
            id = userId,
            username = "Alice",
            email = "alice@example.com",
            phone = "123456789"
        )

        whenever(profileApi.getProfileByUserId(userId)).thenReturn(profile)
        whenever(authApi.getUserById(userId)).thenReturn(auth)

        val result = repository.getFullUserProfile(userId)

        assertTrue(result.isSuccess)
        val co
