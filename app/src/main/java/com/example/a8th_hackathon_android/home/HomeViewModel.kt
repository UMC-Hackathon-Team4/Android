package com.example.a8th_hackathon_android.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a8th_hackathon_android.api.RetrofitClient
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {

    private val _userCoin = MutableLiveData<Long>()
    val userCoin: LiveData<Long> get() = _userCoin

    fun fetchUserInfo() {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.userApi.getUserInfo(
                    "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzUxNzU1OTc4LCJleHAiOjE3NTIxMTU5Nzh9.EvPTfKYV6RIs96sxleyfkA8zZs-2RpTI6Ma9xzwcK6Q"
                )
                if (response.isSuccessful && response.body()?.isSuccess == true) {
                    val coin = response.body()?.result?.coin ?: 0L
                    _userCoin.value = coin
                } else {
                    // 실패 로그
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}