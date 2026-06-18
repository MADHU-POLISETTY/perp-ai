package com.example.perp_ai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.perp_ai.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val repository: AuthRepository
) : ViewModel() {
    val isUserLoggedIn: Boolean
        get() = repository.currentUser != null
}
