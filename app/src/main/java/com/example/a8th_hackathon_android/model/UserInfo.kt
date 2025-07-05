package com.example.a8th_hackathon_android.model

data class UserInfo(
    val userId: Long,
    val name: String,
    val nickname: String,
    val email: String,
    val detail: String,
    val role: String,
    val disabilityType: String,
    val coin: Long,
    val createdAt: String
)