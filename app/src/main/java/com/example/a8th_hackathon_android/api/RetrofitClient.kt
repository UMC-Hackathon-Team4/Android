package com.example.a8th_hackathon_android.api


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitClient {


    //private const val BASE_URL =  "http://43.202.202.54:8080"
    private const val BASE_URL = "https://togetherumc.kro.kr"


    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    // API들 싱글톤
    val userApi: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }

    val projectApi: ProjectApi by lazy {
        retrofit.create(ProjectApi::class.java)
    }







}