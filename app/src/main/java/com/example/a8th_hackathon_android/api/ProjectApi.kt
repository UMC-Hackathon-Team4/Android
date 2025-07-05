package com.example.a8th_hackathon_android.api


import com.example.a8th_hackathon_android.home.CategoryProjectResponse
import com.example.a8th_hackathon_android.model.DeadlineProjectResponse
import com.example.a8th_hackathon_android.model.ProjectBestResponse
import com.example.a8th_hackathon_android.model.ProjectRegisterRequest
import com.example.a8th_hackathon_android.model.ProjectRegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProjectApi {
    //프로젝트 등록하기
    @POST("/projects")
    suspend fun registerProject(
        @Body request: ProjectRegisterRequest
    ): Response<ProjectRegisterResponse>

    //프로젝트 전체 조회
    @GET("/projects/best")
    suspend fun getBestProjects(): Response<ProjectBestResponse>

    // 마감임박 프로젝트 조회
    @GET("/projects/deadline")
    suspend fun getDeadlineProjects(): Response<DeadlineProjectResponse>

    @GET("/projects/lists")
    suspend fun getProjectsByCategory(
        @Query("category") category: String
    ): Response<CategoryProjectResponse>
}

