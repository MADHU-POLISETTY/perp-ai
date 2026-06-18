package com.example.perp_ai.domain.models

data class User(
    val uid: String = "",
    val name: String = "",
    val email: String = "",
    val college: String = "Not Specified",
    val department: String = "Not Specified",
    val yearOfStudy: String = "Not Specified",
    val skills: List<String> = emptyList(),
    val photoUrl: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
