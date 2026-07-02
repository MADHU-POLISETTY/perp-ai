package com.example.perp_ai.data.local

import com.example.perp_ai.domain.models.Question
import java.util.UUID

object QuestionProvider {

    fun getQuestionsForCategory(category: String): List<Question> {
        return when (category.lowercase()) {
            "java" -> javaQuestions
            "python" -> pythonQuestions
            "dbms" -> dbmsQuestions
            "operating systems" -> osQuestions
            "computer networks" -> networkQuestions
            "docker" -> dockerQuestions
            "kubernetes" -> k8sQuestions
            "aws" -> awsQuestions
            "aptitude" -> aptitudeQuestions
            else -> emptyList()
        }
    }

    private val javaQuestions = listOf(
        Question(
            id = UUID.randomUUID().toString(),
            text = "What is the difference between JDK, JRE, and JVM?",
            options = emptyList(),
            keywords = listOf("JVM", "JRE", "JDK"),
            correctAnswer = "JVM is the engine, JRE is the environment, JDK is the toolkit.",
            explanation = "Java Development Kit (JDK) is for development, JRE for execution, JVM for interpretation.",
            category = "Java"
        ),
        Question(
            id = UUID.randomUUID().toString(),
            text = "Explain the pillars of OOPS.",
            options = emptyList(),
            keywords = listOf("Abstraction", "Encapsulation", "Inheritance", "Polymorphism"),
            correctAnswer = "Abstraction, Encapsulation, Inheritance, Polymorphism.",
            explanation = "These 4 concepts form the basis of Object Oriented Programming.",
            category = "Java"
        )
    )

    private val pythonQuestions = listOf(
        Question(
            id = UUID.randomUUID().toString(),
            text = "What are decorators in Python?",
            keywords = listOf("decorator", "function"),
            category = "Python",
            correctAnswer = "Decorators allow you to modify the behavior of a function."
        )
    )

    private val dbmsQuestions = listOf(
        Question(
            id = UUID.randomUUID().toString(),
            text = "Explain ACID properties.",
            keywords = listOf("Atomicity", "Consistency", "Isolation", "Durability"),
            category = "DBMS",
            correctAnswer = "ACID stands for Atomicity, Consistency, Isolation, and Durability."
        )
    )
    
    // Similarly for others...
    private val osQuestions = emptyList<Question>()
    private val networkQuestions = emptyList<Question>()
    private val dockerQuestions = emptyList<Question>()
    private val k8sQuestions = emptyList<Question>()
    private val awsQuestions = emptyList<Question>()
    private val aptitudeQuestions = emptyList<Question>()
}
