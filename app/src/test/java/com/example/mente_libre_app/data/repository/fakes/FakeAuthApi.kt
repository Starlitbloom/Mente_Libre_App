package data.repository.fakes

import com.example.mente_libre_app.data.remote.api.AuthApi
import com.example.mente_libre_app.data.remote.dto.*

class FakeAuthApi : AuthApi {

    var loginResponse: LoginResponseDto? = null
    var getUserResponse: AuthUserDto? = null
    var shouldThrow = false

    override suspend fun login(request: LoginRequestDto): LoginResponseDto {
        if (shouldThrow) throw Exception("Error login")
        return loginResponse ?: throw Exception("LoginResponse is null")
    }

    override suspend fun register(request: RegisterRequestDto) {
        if (shouldThrow) throw Exception("Error register")
    }

    override suspend fun getUserById(id: Long): AuthUserDto {
        if (shouldThrow) throw Exception("Error getUser")
        return getUserResponse ?: throw Exception("UserResponse is null")
    }
}
