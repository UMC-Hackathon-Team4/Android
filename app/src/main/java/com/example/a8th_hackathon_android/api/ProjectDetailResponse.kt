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

data class IntroResponse(
    val isSuccess: Boolean,
    val result: IntroResult
)

data class IntroResult(
    val projectId: Long,
    val description: String
)

data class StoryResponse(
    val isSuccess: Boolean,
    val result: StoryResult
)

data class StoryResult(
    val projectId: Long,
    val story: String
)

data class RewardResponse(
    val isSuccess: Boolean,
    val result: RewardResult
)

data class RewardResult(
    val projectId: Long,
    val rewards: List<RewardItem>
)

data class RewardItem(
    val fundId: Long,
    val title: String,
    val description: String,
    val price: Int
)
