package com.example.perp_ai.domain.model

data class Question(
    val id: String = "",
    val text: String = "",
    val optionA: String = "",
    val optionB: String = "",
    val optionC: String = "",
    val optionD: String = "",
    val correctAnswer: String = "",
    val category: String = ""
)
