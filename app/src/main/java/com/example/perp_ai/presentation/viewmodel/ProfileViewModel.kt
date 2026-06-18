package com.example.perp_ai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perp_ai.domain.models.User
import com.example.perp_ai.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user

    // Mock additional details for professional UI
    private val _college = MutableStateFlow("Indian Institute of Technology")
    val college: StateFlow<String> = _college

    private val _department = MutableStateFlow("Computer Science and Engineering")
    val department: StateFlow<String> = _department

    init {
        _user.value = authRepository.currentUser
    }

    fun logout() {
        viewModelScope.launch {
            authRepository.logout()
        }
    }
}
