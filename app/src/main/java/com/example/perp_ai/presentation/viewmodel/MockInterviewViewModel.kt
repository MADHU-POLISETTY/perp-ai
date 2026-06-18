package com.example.perp_ai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perp_ai.domain.models.*
import com.example.perp_ai.domain.repository.AuthRepository
import com.example.perp_ai.domain.repository.InterviewRepository
import com.example.perp_ai.domain.usecases.EvaluateInterviewUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MockInterviewViewModel @Inject constructor(
    private val interviewRepository: InterviewRepository,
    private val authRepository: AuthRepository,
    private val evaluateInterviewUseCase: EvaluateInterviewUseCase
) : ViewModel() {

    private val _session = MutableStateFlow<InterviewSession?>(null)
    val session: StateFlow<InterviewSession?> = _session

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _evaluationResult = MutableStateFlow<InterviewResult?>(null)
    val evaluationResult: StateFlow<InterviewResult?> = _evaluationResult

    fun startInterview(type: String, category: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val interviewType = try { InterviewType.valueOf(type) } catch (e: Exception) { InterviewType.TECHNICAL }
            
            when (val result = interviewRepository.getQuestions(interviewType, category)) {
                is Resource.Success -> {
                    _session.value = InterviewSession(
                        type = interviewType,
                        category = category,
                        questions = result.data ?: emptyList()
                    )
                }
                is Resource.Error -> {
                    _error.value = result.message
                }
                else -> {}
            }
            _isLoading.value = false
        }
    }

    fun submitAnswer(questionId: String, answer: String) {
        val currentSession = _session.value ?: return
        val updatedAnswers = currentSession.userAnswers.toMutableMap()
        updatedAnswers[questionId] = answer
        _session.value = currentSession.copy(userAnswers = updatedAnswers)
    }

    fun finishInterview() {
        val currentSession = _session.value ?: return
        viewModelScope.launch {
            _isLoading.value = true
            val result = evaluateInterviewUseCase(currentSession)
            val userId = authRepository.currentUser?.uid ?: ""
            val finalResult = result.copy(userId = userId)
            
            interviewRepository.saveInterviewResult(finalResult)
            _evaluationResult.value = finalResult
            _isLoading.value = false
        }
    }
}
