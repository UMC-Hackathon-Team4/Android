package com.example.a8th_hackathon_android.api

import com.example.a8th_hackathon_android.model.ApiResponse
import com.example.a8th_hackathon_android.model.UserInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface UserApi {
    //유저 정보 조회
    @GET("/users/info/{userId}")
    suspend fun getUserInfo(
        @Path("userId") userId: Long
    ): Response<ApiResponse<UserInfo>>
}