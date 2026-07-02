package com.example.perp_ai.domain.model

data class InterviewFeedback(
    val score: Int,
    val summary: String,
    val suggestions: List<String>,
    val correctedAnswers: Map<String, String> // Question to Corrected Answer mapping
)
