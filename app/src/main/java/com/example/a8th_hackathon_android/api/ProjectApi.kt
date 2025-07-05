package com.example.a8th_hackathon_android.api


import com.example.a8th_hackathon_android.model.ProjectBestResponse
import com.example.a8th_hackathon_android.model.ProjectRegisterRequest
import com.example.a8th_hackathon_android.model.ProjectRegisterResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProjectApi {
    @POST("/projects")
    suspend fun registerProject(
        @Body request: ProjectRegisterRequest
    ): Response<ProjectRegisterResponse>
    @GET("/projects/best")
    suspend fun getBestProjects(): Response<ProjectBestResponse>

    @GET("/projects/{projectId}")
    suspend fun getProjectDetail(
        @Path("projectId") projectId: Long,
        @Query("type") type: String // detail, intro, story, reward 중 하나
    ): Response<ProjectDetailResponse>
}

