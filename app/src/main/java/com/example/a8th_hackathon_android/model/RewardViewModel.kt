package com.example.a8th_hackathon_android.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RewardViewModel : ViewModel() {
    private val _rewards = MutableLiveData<MutableList<RewardDto>>(mutableListOf())
    val rewards: LiveData<MutableList<RewardDto>> = _rewards

    fun addReward(reward: RewardDto) {
        _rewards.value?.add(reward)
        _rewards.value = _rewards.value // LiveData 갱신
    }
}