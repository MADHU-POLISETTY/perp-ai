package com.example.perp_ai.domain.usecases

import android.util.Patterns
import com.example.perp_ai.domain.models.Resource
import com.example.perp_ai.domain.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, pass: String): Resource<Unit> {
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            return Resource.Error("Invalid email address")
        }
        if (pass.isBlank()) {
            return Resource.Error("Password cannot be empty")
        }
        return repository.login(email, pass)
    }
}
