package com.example.perp_ai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perp_ai.core.common.VoiceToTextParser
import com.example.perp_ai.domain.models.*
import com.example.perp_ai.domain.repository.AiRepository
import com.example.perp_ai.domain.repository.AuthRepository
import com.example.perp_ai.domain.repository.InterviewRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class MockInterviewViewModel @Inject constructor(
    private val interviewRepository: InterviewRepository,
    private val authRepository: AuthRepository,
    private val aiRepository: AiRepository,
    val voiceToTextParser: VoiceToTextParser
) : ViewModel() {

    private val _uiState = MutableStateFlow(MockInterviewUiState())
    val uiState: StateFlow<MockInterviewUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            voiceToTextParser.state.collect { voiceState ->
                _uiState.update { it.copy(
                    isSpeaking = voiceState.isSpeaking,
                    spokenText = voiceState.spokenText,
                    error = voiceState.error
                ) }
            }
        }
    }

    fun startInterview(type: String, category: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val interviewType = try { InterviewType.valueOf(type) } catch (e: Exception) { InterviewType.TECHNICAL }
            
            when (val result = interviewRepository.getQuestions(interviewType, category)) {
                is Resource.Success -> {
                    _uiState.update { it.copy(
                        questions = result.data ?: emptyList(),
                        isLoading = false
                    ) }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(
                        error = result.message,
                        isLoading = false
                    ) }
                }
                else -> {}
            }
        }
    }

    fun toggleListening() {
        if (_uiState.value.isSpeaking) {
            voiceToTextParser.stopListening()
        } else {
            voiceToTextParser.startListening()
        }
    }

    fun onAnswerChange(answer: String) {
        _uiState.update { it.copy(spokenText = answer) }
    }

    fun submitAnswer(questionId: String, answer: String) {
        _uiState.update { it.copy(
            userAnswers = it.userAnswers + (questionId to answer)
        ) }
    }

    fun finishInterview() {
        submitInterview(_uiState.value.userAnswers)
    }

    fun nextQuestion() {
        val currentState = _uiState.value
        val currentQuestion = currentState.currentQuestion ?: return
        
        val updatedAnswers = currentState.userAnswers.toMutableMap()
        updatedAnswers[currentQuestion.id] = currentState.spokenText

        if (currentState.isLastQuestion) {
            submitInterview(updatedAnswers)
        } else {
            _uiState.update { it.copy(
                userAnswers = updatedAnswers,
                currentQuestionIndex = currentState.currentQuestionIndex + 1,
                spokenText = ""
            ) }
        }
    }

    fun retrySubmit() {
        submitInterview(_uiState.value.userAnswers)
    }

    private fun submitInterview(answers: Map<String, String>) {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            
            val userId = authRepository.currentUser?.uid ?: ""
            val questionsAndAnswers = answers.entries.joinToString("\n") { "${it.key}: ${it.value}" }
            
            // Generate AI Feedback for the whole interview or last question
            val feedbackResult = aiRepository.generateFeedback(
                question = "Overall Interview Performance",
                userAnswer = questionsAndAnswers
            )

            when (feedbackResult) {
                is Resource.Success -> {
                    val feedback = feedbackResult.data!!
                    val result = InterviewResult(
                        id = UUID.randomUUID().toString(),
                        userId = userId,
                        score = feedback.score,
                        feedback = feedback.summary,
                        timestamp = System.currentTimeMillis()
                    )
                    
                    interviewRepository.saveInterviewResult(result)
                    _uiState.update { it.copy(
                        isLoading = false,
                        aiFeedback = feedback,
                        interviewResult = result,
                        isInterviewFinished = true
                    ) }
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(
                        isLoading = false,
                        error = feedbackResult.message
                    ) }
                }
                else -> {}
            }
        }
    }
}
