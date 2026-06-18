package com.example.perp_ai.domain.repository

import com.example.perp_ai.domain.models.Resource
import com.example.perp_ai.domain.models.User

interface AuthRepository {
    val currentUser: User?
    suspend fun login(email: String, pass: String): Resource<Unit>
    suspend fun register(user: User, pass: String): Resource<Unit>
    suspend fun logout()
    suspend fun resetPassword(email: String): Resource<Unit>
    suspend fun getUserDetails(uid: String): Resource<User>
}
