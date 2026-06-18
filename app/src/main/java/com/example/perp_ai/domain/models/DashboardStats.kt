package com.example.perp_ai.domain.models

data class DashboardStats(
    val totalInterviews: Int = 0,
    val averageScore: Double = 0.0,
    val completedInterviews: Int = 0,
    val recentPerformance: List<Int> = emptyList()
)
