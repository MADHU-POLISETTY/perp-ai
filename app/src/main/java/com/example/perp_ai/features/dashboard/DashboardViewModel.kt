package com.example.perp_ai.features.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perp_ai.domain.models.DashboardStats
import com.example.perp_ai.domain.models.User
import com.example.perp_ai.domain.repository.AuthRepository
import com.example.perp_ai.domain.repository.InterviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class DashboardUiState(
    val user: User? = null,
    val stats: DashboardStats = DashboardStats(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val interviewRepository: InterviewRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState: StateFlow<DashboardUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val user = authRepository.currentUser
            _uiState.update { it.copy(user = user) }
            
            // In a real app, calculate stats from interview history
            // For now, providing professional mock data
            _uiState.update { it.copy(
                stats = DashboardStats(
                    totalInterviews = 15,
                    averageScore = 78.0,
                    completedInterviews = 12,
                    recentPerformance = listOf(60, 75, 80, 70, 85)
                ),
                isLoading = false
            ) }
        }
    }
}
