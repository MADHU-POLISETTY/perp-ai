package com.example.perp_ai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perp_ai.domain.models.InterviewResult
import com.example.perp_ai.domain.models.Resource
import com.example.perp_ai.domain.repository.AuthRepository
import com.example.perp_ai.domain.repository.InterviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class InterviewHistoryViewModel @Inject constructor(
    private val interviewRepository: InterviewRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _history = MutableStateFlow<List<InterviewResult>>(emptyList())
    val history: StateFlow<List<InterviewResult>> = _history

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    init {
        loadHistory()
    }

    private fun loadHistory() {
        viewModelScope.launch {
            val userId = authRepository.currentUser?.uid ?: return@launch
            _isLoading.value = true
            when (val result = interviewRepository.getInterviewHistory(userId)) {
                is Resource.Success -> {
                    _history.value = result.data ?: emptyList()
                }
                else -> {}
            }
            _isLoading.value = false
        }
    }
}
