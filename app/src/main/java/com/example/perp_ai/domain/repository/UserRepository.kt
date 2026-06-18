package com.example.perp_ai.domain.repository

import com.example.perp_ai.domain.models.User
import com.example.perp_ai.domain.models.Resource
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserProfile(uid: String): Resource<User>
    suspend fun updateUserProfile(user: User): Resource<Unit>
    fun getUserFlow(uid: String): Flow<Resource<User>>
}
