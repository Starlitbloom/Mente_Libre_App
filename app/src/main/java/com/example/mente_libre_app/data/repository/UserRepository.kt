package com.example.mente_libre_app.data.repository

import com.example.mente_libre_app.data.local.user.UserDao
import com.example.mente_libre_app.data.local.user.UserEntity

// Repositorio: maneja las operaciones de negocio para login y registro usando el DAO.
class UserRepository(
    private val userDao: UserDao
) {

    // ---------------- LOGIN ----------------
    suspend fun login(email: String, password: String): Result<UserEntity> {
        val user = userDao.getByEmail(email)
        return if (user != null && user.password == password) {
            Result.success(user)
        } else {
            Result.failure(IllegalArgumentException("Credenciales inválidas"))
        }
    }

    // ---------------- REGISTRO ----------------
    suspend fun register(name: String, email: String, phone: String, password: String): Result<Long> {
        val exists = userDao.getByEmail(email) != null
        if (exists) {
            return Result.failure(IllegalStateException("El correo ya está registrado"))
        }

        val id = userDao.insert(
            UserEntity(
                name = name,
                email = email,
                phone = phone,
                password = password
            )
        )
        return Result.success(id)
    }
}
