package com.example.perp_ai.data.repository

import com.example.perp_ai.domain.models.*
import com.example.perp_ai.domain.repository.InterviewRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class InterviewRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore
) : InterviewRepository {

    override suspend fun saveInterviewResult(result: InterviewResult): Resource<Unit> {
        return try {
            firestore.collection("results").document(result.id).set(result).await()
            Resource.Success(Unit)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to save result")
        }
    }

    override suspend fun getInterviewHistory(userId: String): Resource<List<InterviewResult>> {
        return try {
            val snapshot = firestore.collection("results")
                .whereEqualTo("userId", userId)
                .orderBy("timestamp", Query.Direction.DESCENDING)
                .get().await()
            val results = snapshot.toObjects(InterviewResult::class.java)
            Resource.Success(results)
        } catch (e: Exception) {
            Resource.Error(e.localizedMessage ?: "Failed to fetch history")
        }
    }

    override suspend fun getQuestions(type: InterviewType, category: String): Resource<List<Question>> {
        return try {
            val snapshot = firestore.collection("questions")
                .whereEqualTo("type", type.name)
                .whereEqualTo("category", category)
                .limit(10)
                .get().await()
            val questions = snapshot.toObjects(Question::class.java)
            
            if (questions.isEmpty()) {
                // Fallback to static mock questions if Firestore is empty for this category
                Resource.Success(generateMockQuestions(category))
            } else {
                Resource.Success(questions)
            }
        } catch (e: Exception) {
            Resource.Success(generateMockQuestions(category)) // Fallback on error
        }
    }

    private fun generateMockQuestions(category: String): List<Question> {
        return listOf(
            Question(
                text = "Explain the core concepts of $category.",
                keywords = listOf("architecture", "design", "component", "lifecycle"),
                category = category,
                explanation = "Review the basic building blocks and how they interact."
            ),
            Question(
                text = "How do you handle security in $category applications?",
                keywords = listOf("encryption", "authentication", "authorization", "protocol"),
                category = category,
                explanation = "Focus on OWASP principles and specific security APIs."
            )
        )
    }
}
