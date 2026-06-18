package com.example.perp_ai.domain.repository

import com.example.perp_ai.domain.models.*

interface InterviewRepository {
    suspend fun saveInterviewResult(result: InterviewResult): Resource<Unit>
    suspend fun getInterviewHistory(userId: String): Resource<List<InterviewResult>>
    suspend fun getQuestions(type: InterviewType, category: String): Resource<List<Question>>
}
