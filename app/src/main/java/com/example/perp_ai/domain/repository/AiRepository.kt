package com.example.perp_ai.domain.repository

import com.example.perp_ai.domain.models.Resource
import com.example.perp_ai.domain.model.InterviewFeedback
import kotlinx.coroutines.flow.Flow

interface AiRepository {
    suspend fun generateFeedback(
        question: String,
        userAnswer: String
    ): Resource<InterviewFeedback>
}
