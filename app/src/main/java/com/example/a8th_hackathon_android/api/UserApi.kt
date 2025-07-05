package com.example.a8th_hackathon_android.api

import com.example.a8th_hackathon_android.model.ApiResponse
import com.example.a8th_hackathon_android.model.UserInfo
import com.example.a8th_hackathon_android.model.UserInfoResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface UserApi {
    //유저 정보 조회
    @GET("/users/info")
    suspend fun getUserInfo(
        @Header("Authorization") token: String
    ): Response<UserInfoResponse>
}