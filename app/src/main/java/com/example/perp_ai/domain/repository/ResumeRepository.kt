package com.example.perp_ai.domain.repository

import com.example.perp_ai.domain.models.AtsAnalysisResult
import com.example.perp_ai.domain.models.Resource

interface ResumeRepository {
    suspend fun analyzeResume(
        resumeText: String,
        jobDescription: String
    ): Resource<AtsAnalysisResult>
    
    suspend fun saveAnalysisHistory(result: AtsAnalysisResult): Resource<Unit>
    
    suspend fun getAnalysisHistory(userId: String): Resource<List<AtsAnalysisResult>>
}
