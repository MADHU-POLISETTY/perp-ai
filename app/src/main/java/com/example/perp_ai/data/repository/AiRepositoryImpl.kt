package com.example.perp_ai.data.repository

import com.example.perp_ai.domain.model.InterviewFeedback
import com.example.perp_ai.domain.models.Resource
import com.example.perp_ai.domain.repository.AiRepository
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import javax.inject.Inject

class AiRepositoryImpl @Inject constructor(
    private val generativeModel: GenerativeModel
) : AiRepository {

    override suspend fun generateFeedback(
        question: String,
        userAnswer: String
    ): Resource<InterviewFeedback> {
        return try {
            val prompt = """
                You are an expert technical interviewer. 
                Analyze the following answer given by a candidate for the question: "$question".
                Candidate's Answer: "$userAnswer"
                
                Provide feedback in the following format:
                Score: (out of 10)
                Summary: (brief overall assessment)
                Suggestions: (bullet points for improvement)
                Corrected Answer: (how the answer should have been)
            """.trimIndent()

            val response = generativeModel.generateContent(prompt)
            val responseText = response.text ?: ""
            
            // Basic parsing logic (can be improved with JSON response if using a specific model version)
            val feedback = parseFeedback(responseText)
            Resource.Success(feedback)
        } catch (e: Exception) {
            val errorMessage = when {
                e.message?.contains("503") == true || e.message?.contains("UNAVAILABLE") == true -> {
                    "AI service is currently busy. Please try again in a few minutes."
                }
                e.message?.contains("429") == true || e.message?.contains("quota") == true || e.message?.contains("limit") == true -> {
                    "API quota exceeded. Please wait a moment or try again later."
                }
                else -> e.message ?: "An unknown error occurred"
            }
            Resource.Error(errorMessage)
        }
    }

    private fun parseFeedback(text: String): InterviewFeedback {
        // This is a simplified parser. For production, consider requesting JSON from Gemini.
        val lines = text.lines()
        val score = lines.find { it.startsWith("Score:") }?.substringAfter(":")?.trim()?.filter { it.isDigit() }?.toIntOrNull() ?: 0
        val summary = lines.find { it.startsWith("Summary:") }?.substringAfter(":")?.trim() ?: ""
        val suggestions = lines.filter { it.trim().startsWith("-") || it.trim().startsWith("*") }.map { it.trim().removePrefix("-").removePrefix("*").trim() }
        val correctedAnswer = lines.find { it.startsWith("Corrected Answer:") }?.substringAfter(":")?.trim() ?: ""

        return InterviewFeedback(
            score = score,
            summary = summary,
            suggestions = suggestions,
            correctedAnswers = mapOf("feedback" to correctedAnswer)
        )
    }
}
