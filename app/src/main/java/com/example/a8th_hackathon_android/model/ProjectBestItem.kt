package com.example.a8th_hackathon_android.model

data class ProjectBestItem(
    val projectId: Long,
    val title: String,
    val imageUrl: String,
    val category: String,
    val currentAmount: Long,
    val targetAmount: Long,
    val endDate: String,
    val supportenseCount: Long
)

data class ProjectBestResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: List<ProjectBestItem>
)
