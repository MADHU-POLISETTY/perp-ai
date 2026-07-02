package com.example.perp_ai.domain.repository

import com.example.perp_ai.domain.model.Question

interface QuizRepository {
    suspend fun getQuestions(category: String): Result<List<Question>>
}
