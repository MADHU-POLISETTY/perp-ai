package com.example.perp_ai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perp_ai.domain.models.DashboardStats
import com.example.perp_ai.domain.models.User
import com.example.perp_ai.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    private val _stats = MutableStateFlow(DashboardStats())
    val stats: StateFlow<DashboardStats> = _stats

    init {
        loadDashboardData()
    }

    private fun loadDashboardData() {
        viewModelScope.launch {
            _currentUser.value = authRepository.currentUser
            // Mocking stats for now as requested for professional project UI
            _stats.value = DashboardStats(
                totalInterviews = 12,
                averageScore = 84.5,
                completedInterviews = 10,
                recentPerformance = listOf(70, 85, 90, 82, 88)
            )
        }
    }
}
