package com.example.a8th_hackathon_android.home

data class CategoryProjectItem(
    val projectId: Long,
    val projectTitle: String,
    val imageUrl: String?,       // nullable 처리!
    val category: String,
    val currentAmount: Long,
    val targetAmount: Long,
    val percentage: String,
    val endDate: String
)

data class CategoryProjectResponse(
    val isSuccess: Boolean,
    val code: String,
    val message: String,
    val pageInfo: PageInfo,
    val result: List<CategoryProjectItem>
)

data class PageInfo(
    val page: Int,
    val size: Int,
    val hasNext: Boolean,
    val totalElements: Int,
    val totalPages: Int
)