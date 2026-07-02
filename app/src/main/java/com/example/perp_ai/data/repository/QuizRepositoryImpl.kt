package com.example.perp_ai.data.repository

import com.example.perp_ai.data.local.LocalQuestionProvider
import com.example.perp_ai.domain.model.Question
import com.example.perp_ai.domain.repository.QuizRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class QuizRepositoryImpl(
    private val firestore: FirebaseFirestore
) : QuizRepository {

    override suspend fun getQuestions(category: String): Result<List<Question>> {
        return try {
            val snapshot = firestore.collection("questions")
                .whereEqualTo("category", category)
                .get()
                .await()

            val questionsList = snapshot.documents.mapNotNull { document ->
                document.toObject(Question::class.java)
            }

            if (questionsList.isEmpty()) {
                // Fallback to local questions if none found in Firestore
                Result.success(LocalQuestionProvider.getQuestionsForCategory(category))
            } else {
                Result.success(questionsList.take(5))
            }
        } catch (e: Exception) {
            // Fallback to local questions on any error
            Result.success(LocalQuestionProvider.getQuestionsForCategory(category))
        }
    }
}
