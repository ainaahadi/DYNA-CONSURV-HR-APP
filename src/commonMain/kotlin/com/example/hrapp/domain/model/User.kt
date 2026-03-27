package com.example.hrapp.domain.model

data class User(
    val id: String,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val email: String,
    val role: String,
    val avatar: String? = null,
    val department: String? = null
)
