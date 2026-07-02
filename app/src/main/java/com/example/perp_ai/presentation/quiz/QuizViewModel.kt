package com.example.perp_ai.presentation.quiz

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perp_ai.domain.model.Question
import com.example.perp_ai.domain.repository.QuizRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(
    private val repository: QuizRepository
) : ViewModel() {

    var questionsList by mutableStateOf<List<Question>>(emptyList())
        private set

    var currentQuestionIndex by mutableIntStateOf(0)
        private set

    var score by mutableIntStateOf(0)
        private set

    var isQuizFinished by mutableStateOf(false)
        private set

    var isLoading by mutableStateOf(false)
        private set

    val currentQuestion: Question?
        get() = questionsList.getOrNull(currentQuestionIndex)

    fun loadQuestions(category: String) {
        viewModelScope.launch {
            isLoading = true
            repository.getQuestions(category).onSuccess { questions ->
                questionsList = questions
            }.onFailure {
                // Fallback is handled in Repository, but we should clear loading
            }
            isLoading = false
        }
    }

    fun checkAnswer(selectedAnswer: String) {
        val question = currentQuestion ?: return
        
        if (selectedAnswer == question.correctAnswer) {
            score++
        }

        if (currentQuestionIndex < questionsList.size - 1) {
            currentQuestionIndex++
        } else {
            isQuizFinished = true
        }
    }
}
