package com.example.a8th_hackathon_android.model

data class UserInfoResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: UserInfoResult
)

data class UserInfoResult(
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
