package com.example.perp_ai.domain.models

import java.util.UUID

enum class InterviewType {
    HR, TECHNICAL, APTITUDE, AI_MOCK, GROUP_DISCUSSION, CODING
}

enum class TechnicalCategory {
    JAVA, PYTHON, CPP, AWS, DEVOPS, SQL, MACHINE_LEARNING, SYSTEM_DESIGN
}

data class Question(
    val id: String = UUID.randomUUID().toString(),
    val text: String = "",
    val options: List<String> = emptyList(), // For Aptitude/MCQ
    val keywords: List<String> = emptyList(), // For Descriptive Evaluation
    val correctAnswer: String = "",
    val explanation: String = "",
    val category: String = ""
)

data class InterviewSession(
    val id: String = UUID.randomUUID().toString(),
    val type: InterviewType = InterviewType.TECHNICAL,
    val category: String = "",
    val startTime: Long = System.currentTimeMillis(),
    val questions: List<Question> = emptyList(),
    val userAnswers: Map<String, String> = emptyMap(),
    val status: SessionStatus = SessionStatus.IN_PROGRESS
)

enum class SessionStatus {
    IN_PROGRESS, COMPLETED, ABANDONED
}

data class InterviewResult(
    val id: String = UUID.randomUUID().toString(),
    val sessionId: String = "",
    val userId: String = "",
    val type: InterviewType = InterviewType.TECHNICAL,
    val score: Int = 0,
    val totalQuestions: Int = 0,
    val weakAreas: List<String> = emptyList(),
    val strongAreas: List<String> = emptyList(),
    val suggestions: List<String> = emptyList(),
    val feedback: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
