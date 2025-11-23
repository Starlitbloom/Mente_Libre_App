package data.repository.fakes

import com.example.mente_libre_app.data.remote.api.UserProfileApi
import com.example.mente_libre_app.data.remote.dto.*

class FakeProfileApi : UserProfileApi {

    var profileResponse: UserProfileDto? = null
    var createdProfile: UserProfileDto? = null
    var updatedProfile: UserProfileDto? = null

    var shouldThrow = false

    override suspend fun getProfileById(id: Long): UserProfileDto {
        if (shouldThrow) throw Exception("Error getProfileById")
        return profileResponse ?: throw Exception("Profile is null")
    }

    override suspend fun getProfileByUserId(id: Long): UserProfileDto {
        if (shouldThrow) throw Exception("Error getProfileByUserId")
        return profileResponse ?: throw Exception("Profile is null")
    }

    override suspend fun createProfile(request: CreateUserProfileRequestDto): UserProfileDto {
        if (shouldThrow) throw Exception("Error createProfile")
        return createdProfile ?: throw Exception("CreateProfile null")
    }

    override suspend fun updateProfile(
        id: Long,
        request: CreateUserProfileRequestDto
    ): UserProfileDto {
        if (shouldThrow) throw Exception("Error updateProfile")
        return updatedProfile ?: throw Exception("UpdatedProfile null")
    }
}
