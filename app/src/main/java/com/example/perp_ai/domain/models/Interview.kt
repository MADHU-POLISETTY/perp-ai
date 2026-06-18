package com.example.perp_ai.domain.models

data class Interview(
    val interviewId: String = "",
    val userId: String = "",
    val score: Int = 0,
    val feedback: String = "",
    val date: Long = System.currentTimeMillis()
)
