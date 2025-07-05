package com.example.a8th_hackathon_android.model

data class ProjectRegisterRequest(
    val userId: Long,
    val category: String,
    val targetAmount: Long,
    val startDate: String,
    val endDate: String,
    val title: String,
    val summary: String,
    val description: String,
    val imageUrl: String,
    val rewards: List<RewardDto>
)

data class RewardDto(
    val title: String,
    val price: Long,
    val stock: Long
)

data class ProjectRegisterResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val projectId: Long,
    val title: String,
    val startDate: String,
    val endDate: String
)
