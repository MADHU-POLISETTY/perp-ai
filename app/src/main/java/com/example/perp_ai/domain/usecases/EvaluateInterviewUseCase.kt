package com.example.perp_ai.domain.usecases

import com.example.perp_ai.domain.models.*
import java.util.*
import javax.inject.Inject

class EvaluateInterviewUseCase @Inject constructor() {
    
    operator fun invoke(session: InterviewSession): InterviewResult {
        var totalPossibleScore = 0
        var userEarnedScore = 0
        
        val strongAreas = mutableListOf<String>()
        val weakAreas = mutableListOf<String>()
        val suggestions = mutableListOf<String>()

        session.questions.forEach { question ->
            val userAnswer = session.userAnswers[question.id] ?: ""
            val scoreForQuestion = calculateQuestionScore(question, userAnswer)
            
            totalPossibleScore += 100
            userEarnedScore += scoreForQuestion

            if (scoreForQuestion >= 80) {
                strongAreas.add(question.category)
            } else if (scoreForQuestion <= 40) {
                weakAreas.add(question.category)
                suggestions.add("Improve your knowledge in ${question.category} by reviewing ${question.explanation.take(30)}...")
            }
        }

        val finalScore = if (totalPossibleScore > 0) (userEarnedScore.toFloat() / totalPossibleScore * 100).toInt() else 0
        
        return InterviewResult(
            sessionId = session.id,
            type = session.type,
            score = finalScore,
            totalQuestions = session.questions.size,
            weakAreas = weakAreas.distinct(),
            strongAreas = strongAreas.distinct(),
            suggestions = suggestions.take(3),
            feedback = generateGeneralFeedback(finalScore),
            timestamp = System.currentTimeMillis()
        )
    }

    private fun calculateQuestionScore(question: Question, userAnswer: String): Int {
        if (userAnswer.isBlank()) return 0
        
        // For MCQ types (Aptitude)
        if (question.options.isNotEmpty()) {
            return if (userAnswer.trim().lowercase() == question.correctAnswer.trim().lowercase()) 100 else 0
        }

        // For Descriptive types (Technical/HR) - Keyword Based Evaluation
        if (question.keywords.isEmpty()) return 50 // Default medium score if no keywords defined
        
        val foundKeywords = question.keywords.count { keyword ->
            userAnswer.contains(keyword, ignoreCase = true)
        }

        val matchPercentage = foundKeywords.toFloat() / question.keywords.size
        
        return when {
            matchPercentage >= 0.8f -> 100 // High score for accurate answers
            matchPercentage >= 0.5f -> 70  // Medium score for partially correct
            matchPercentage >= 0.2f -> 40  // Low score for irrelevant/incomplete
            else -> 10 // Irrelevant or incorrect
        }
    }

    private fun generateGeneralFeedback(score: Int): String {
        return when {
            score >= 80 -> "Excellent performance! You demonstrate a strong command over the subject matter."
            score >= 60 -> "Good effort. You have a solid foundation but could benefit from more in-depth preparation."
            score >= 40 -> "Average performance. Focus on understanding core concepts and practice more descriptive answers."
            else -> "Needs significant improvement. Review the weak areas and try again with more preparation."
        }
    }
}
