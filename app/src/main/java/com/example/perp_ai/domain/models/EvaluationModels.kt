package com.example.perp_ai.domain.models

data class EvaluationResult(
    val score: Int,
    val feedback: String,
    val strengths: List<String>,
    val weaknesses: List<String>,
    val improvementSuggestions: List<String>
)

data class EvaluatedQuestion(
    val questionId: String,
    val questionText: String,
    val userAnswer: String,
    val score: Int,
    val feedback: String
)
