package com.example.perp_ai.data.repository

import com.example.perp_ai.domain.models.AtsAnalysisResult
import com.example.perp_ai.domain.models.Resource
import com.example.perp_ai.domain.repository.AiRepository
import com.example.perp_ai.domain.repository.ResumeRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class ResumeRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore,
    private val aiRepository: AiRepository
) : ResumeRepository {

    override suspend fun analyzeResume(
        resumeText: String,
        jobDescription: String
    ): Resource<AtsAnalysisResult> {
        return try {
            val prompt = """
                You are an expert ATS (Applicant Tracking System).
                Analyze the following resume against the job description.
                Resume Text: $resumeText
                Job Description: $jobDescription
                
                Provide:
                1. ATS Score (0-100)
                2. List of Matched Skills
                3. List of Missing Keywords
                4. Professional suggestions for improvement
                
                Format the response as:
                Score: [Value]
                Matched Skills: [Comma separated list]
                Missing Keywords: [Comma separated list]
                Suggestions: [Bullet points]
            """.trimIndent()

            val aiResponse = aiRepository.generateFeedback(
                question = "ATS Analysis",
                userAnswer = prompt
            )

            when (aiResponse) {
                is Resource.Success -> {
                    val data = aiResponse.data!!
                    // Since generateFeedback returns InterviewFeedback, we adapt it.
                    // Ideally, AiRepository would have a more generic method.
                    val result = AtsAnalysisResult(
                        score = data.score * 10, // Assuming AI score is 1-10
                        matchedSkills = data.suggestions.take(5), // Placeholder logic
                        missingKeywords = listOf("Keywords analysis required"),
                        suggestions = data.suggestions,
                        jobDescription = jobDescription
                    )
                    Resource.Success(result)
                }
                is Resource.Error -> Resource.Error(aiResponse.message ?: "AI Analysis Failed")
                else -> Resource.Error("Unknown error")
            }
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to analyze resume")
        }
    }

    override suspend fun saveAnalysisHistory(result: AtsAnalysisResult): Resource<Unit> {
        return try {
            firestore.collection("resume_analysis").document(result.id).set(result).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to save history")
        }
    }

    override suspend fun getAnalysisHistory(userId: String): Resource<List<AtsAnalysisResult>> {
        return try {
            val snapshot = firestore.collection("resume_analysis")
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get().await()
            val results = snapshot.toObjects(AtsAnalysisResult::class.java)
            Resource.Success(results)
        } catch (e: Exception) {
            Resource.Error(e.message ?: "Failed to fetch history")
        }
    }
}
