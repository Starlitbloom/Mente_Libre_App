package data.repository.fakes

class FakeTokenManager {

    var savedToken: String? = null
    var savedUserId: Long? = null

    fun saveToken(token: String, id: Long) {
        savedToken = token
        savedUserId = id
    }

    fun getUserId(): Long? = savedUserId

    fun clear() {
        savedToken = null
        savedUserId = null
    }
}
