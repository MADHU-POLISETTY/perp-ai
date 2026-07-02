package com.example.perp_ai.presentation.viewmodel

import com.example.perp_ai.domain.model.InterviewFeedback
import com.example.perp_ai.domain.models.InterviewResult
import com.example.perp_ai.domain.models.Question

data class MockInterviewUiState(
    val isLoading: Boolean = false,
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val userAnswers: Map<String, String> = emptyMap(),
    val isSpeaking: Boolean = false,
    val spokenText: String = "",
    val error: String? = null,
    val interviewResult: InterviewResult? = null,
    val aiFeedback: InterviewFeedback? = null,
    val isInterviewFinished: Boolean = false
) {
    val currentQuestion: Question? get() = questions.getOrNull(currentQuestionIndex)
    val isLastQuestion: Boolean get() = currentQuestionIndex == questions.size - 1
}
