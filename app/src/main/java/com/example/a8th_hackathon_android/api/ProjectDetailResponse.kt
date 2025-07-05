package com.example.a8th_hackathon_android.api

data class ProjectDetailResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val result: ProjectDetail
)

data class ProjectDetail(
    val projectId: Long,
    val projectTitle: String,
    val summary: String,
    val imageUrl: String,
    val category: String,
    val targetAmount: Long,
    val currentAmount: Long,
    val creatorImageUrl: String?,
    val creatorDetail: String,
    val creatorNickname: String,
    val supportersCount: Long,
    val percentage: String
)
