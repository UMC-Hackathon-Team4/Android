package com.example.a8th_hackathon_android.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProjectViewModel : ViewModel() {
    private val _request = MutableLiveData<ProjectRegisterRequest>()
    val request: LiveData<ProjectRegisterRequest> get() = _request

    fun saveStepOneData(
        category: String,
        targetAmount: Long,
        startDate: String,
        endDate: String
    ) {
        _request.value = ProjectRegisterRequest(
            category = category,
            targetAmount = targetAmount,
            startDate = startDate,
            endDate = endDate,
            title = null,
            summary = null,
            description = null,
            story = null,
            imageUrl = null,
            rewards = emptyList()
        )
    }

    fun updateStepTwoData(
        title: String,
        summary: String,
        description: String,
        story: String,
        imageUrl: String? = null
    ) {
        val current = _request.value ?: return
        _request.value = current.copy(
            title = title,
            summary = summary,
            description = description,
            story = story,
            imageUrl = imageUrl
        )
    }

    fun updateRewards(rewards: List<RewardRequest>) {
        val current = _request.value ?: return
        _request.value = current.copy(rewards = rewards)
    }

    fun buildProjectRegisterRequest(): ProjectRegisterRequest? {
        return _request.value
    }

}
