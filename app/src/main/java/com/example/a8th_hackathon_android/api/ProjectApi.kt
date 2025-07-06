package com.example.a8th_hackathon_android.api


import com.example.a8th_hackathon_android.home.CategoryProjectResponse
import com.example.a8th_hackathon_android.model.DeadlineProjectResponse
import com.example.a8th_hackathon_android.model.ProjectBestResponse
import com.example.a8th_hackathon_android.model.ProjectRegisterRequest
import com.example.a8th_hackathon_android.model.ProjectRegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectApi {
    @POST("/projects")
    suspend fun registerProject(
        @Header("Authorization") token: String,
        @Body request: ProjectRegisterRequest
    ): Response<ProjectRegisterResponse>
    @GET("/projects/best")
    suspend fun getBestProjects(): Response<ProjectBestResponse>
    @GET("/projects/{projectId}")
    suspend fun getProjectDetail(
        @Path("projectId") projectId: Long,
        @Query("type") view: String
    ): Response<ProjectDetailResponse>

    @GET("/projects/deadline")
    suspend fun getDeadlineProjects(): Response<DeadlineProjectResponse>

    @GET("/projects/lists")
    suspend fun getProjectsByCategory(
        @Query("category") category: String
    ): Response<CategoryProjectResponse>

    @GET("/projects/{projectId}/intro")
    suspend fun getProjectIntro(
        @Path("projectId") projectId: Long
    ): Response<IntroResponse>

    @GET("/projects/{projectId}/story")
    suspend fun getProjectStory(
        @Path("projectId") projectId: Long
    ): Response<StoryResponse>

    @GET("/projects/{projectId}/rewards")
    suspend fun getProjectRewards(
        @Path("projectId") projectId: Long
    ): Response<RewardResponse>

}

