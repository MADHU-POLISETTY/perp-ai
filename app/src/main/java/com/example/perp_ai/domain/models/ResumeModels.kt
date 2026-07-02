package com.example.perp_ai.domain.models

import java.util.UUID

data class AtsAnalysisResult(
    val id: String = UUID.randomUUID().toString(),
    val userId: String = "",
    val score: Int = 0,
    val matchedSkills: List<String> = emptyList(),
    val missingKeywords: List<String> = emptyList(),
    val suggestions: List<String> = emptyList(),
    val jobDescription: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
