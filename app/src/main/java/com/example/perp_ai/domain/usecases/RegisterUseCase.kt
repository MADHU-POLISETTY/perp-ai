package com.example.perp_ai.domain.usecases

import android.util.Patterns
import com.example.perp_ai.domain.models.Resource
import com.example.perp_ai.domain.models.User
import com.example.perp_ai.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(name: String, email: String, pass: String): Resource<Unit> {
        if (name.length < 3) {
            return Resource.Error("Name must be at least 3 characters")
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Resource.Error("Invalid email address")
        }
        if (pass.length < 6) {
            return Resource.Error("Password must be at least 6 characters")
        }
        
        val user = User(
            name = name,
            email = email
        )
        return repository.register(user, pass)
    }
}
