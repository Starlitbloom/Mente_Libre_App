package data.repository

import com.example.mente_libre_app.data.remote.dto.*
import com.example.mente_libre_app.data.repository.UserProfileRepository
import data.repository.fakes.FakeAuthApi
import data.repository.fakes.FakeProfileApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class UserProfileRepositoryTest {

    private lateinit var fakeAuthApi: FakeAuthApi
    private lateinit var fakeProfileApi: FakeProfileApi
    private lateinit var repository: UserProfileRepository

    @Before
    fun setup() {
        fakeAuthApi = FakeAuthApi()
        fakeProfileApi = FakeProfileApi()

        repository = UserProfileRepository(context = null!!).also {
            val profileField = UserProfileRepository::class.java.getDeclaredField("profileApi")
            profileField.isAccessible = true
            profileField.set(it, fakeProfileApi)

            val authField = UserProfileRepository::class.java.getDeclaredField("authApi")
            authField.isAccessible = true
            authField.set(it, fakeAuthApi)
        }
    }

    @Test
    fun `getFullUserProfile combina datos correctamente`() = runTest {
        fakeProfileApi.profileResponse = UserProfileDto(
            id = 1L,
            userId = 10L,
            direccion = "Mi casa",
            fechaNacimiento = "2000-01-01",
            notificaciones = true,
            generoId = 1,
            generoNombre = "Femenino",
            fotoPerfil = "img.png"
        )

        fakeAuthApi.getUserResponse = AuthUserDto(
            id = 10L,
            username = "ma",
            email = "ma@mail.com",
            phone = "123123"
        )

        val result = repository.getFullUserProfile(10L)

        assertTrue(result.isSuccess)

        val combined = result.getOrNull()!!
        assertEquals("ma", combined.username)
        assertEquals("Mi casa", combined.direccion)
        assertEquals("img.png", combined.fotoPerfil)
    }

    @Test
    fun `getProfileById retorna success cuando profileApi responde`() = runTest {
        fakeProfileApi.profileResponse = UserProfileDto(
            id = 1L, userId = 10L,
            direccion = "ok",
            fechaNacimiento = "2000",
            notificaciones = true,
            generoId = 1, generoNombre = "F",
            fotoPerfil = ""
        )

        val result = repository.getProfileById(1)

        assertTrue(result.isSuccess)
    }

    @Test
    fun `createProfile retorna failure si api lanza error`() = runTest {
        fakeProfileApi.shouldThrow = true

        val result = repository.createProfile(
            CreateUserProfileRequestDto(
                userId = 10L,
                direccion = "a",
                fechaNacimiento = "b",
                notificaciones = true,
                generoId = 1L,
                fotoPerfil = ""
            )

        )
        assertTrue(result.isFailure)
    }
}
