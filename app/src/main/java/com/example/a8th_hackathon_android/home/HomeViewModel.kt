package com.example.a8th_hackathon_android.home

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a8th_hackathon_android.api.RetrofitClient
import com.example.a8th_hackathon_android.model.TokenRequest
import kotlinx.coroutines.launch

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    private val _userCoin = MutableLiveData<Long>()
    val userCoin: LiveData<Long> get() = _userCoin

    private val prefs = application.getSharedPreferences("auth", Context.MODE_PRIVATE)

    fun initUser() {
        viewModelScope.launch {
            try {
                // JWT 발급
                val tokenResponse = RetrofitClient.userApi.getToken(TokenRequest(userId = 1))
                if (tokenResponse.isSuccessful) {
                    val token = tokenResponse.body()?.token ?: ""
                    prefs.edit().putString("jwt_token", token).apply()

                    // 발급된 토큰으로 사용자 정보 호출
                    fetchUserInfo()
                } else {
                    // 실패 처리
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

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