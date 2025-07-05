package com.example.a8th_hackathon_android.model

data class DeadlineProjectItem(
    val projectId: Long,
    val projectTitle: String,
    val imageUrl: String,
    val endDate: String,  // LocalDate는 보통 String으로 받아서 포맷
    val currentAmount: Long,
    val targetAmount: Long,
    val category: String  // ENUM 처리도 String
)

data class DeadlineProjectResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: List<DeadlineProjectItem>
)