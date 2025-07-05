package com.example.a8th_hackathon_android.home


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.a8th_hackathon_android.api.RetrofitClient
import com.example.a8th_hackathon_android.model.DeadlineProjectItem
import com.example.a8th_hackathon_android.model.ProjectBestItem
import kotlinx.coroutines.launch


class ProjectViewModel : ViewModel() {

    // 마감임박 프로젝트 리스트 LiveData
    private val _deadlineProjects = MutableLiveData<List<DeadlineProjectItem>>()
    val deadlineProjects: LiveData<List<DeadlineProjectItem>> = _deadlineProjects

    // 베스트 프로젝트용!
    private val _bestProjects = MutableLiveData<List<ProjectBestItem>>()
    val bestProjects: LiveData<List<ProjectBestItem>> = _bestProjects

    //카테고리 보기!
    private val _categoryProjects = MutableLiveData<List<CategoryProjectItem>>()
    val categoryProjects: LiveData<List<CategoryProjectItem>> = _categoryProjects

    // API 호출 함수
    fun getDeadlineProjects() {
        viewModelScope.launch {
            val response = RetrofitClient.projectApi.getDeadlineProjects()
            if (response.isSuccessful) {
                response.body()?.result?.let { result ->
                    // 정렬도 여기서 할 수 있어요!
                    val sortedList = result.sortedBy { it.endDate }
                    _deadlineProjects.value = sortedList
                }
            } else {
                println("API 실패: ${response.code()}")
            }
        }
    }

    fun getBestProjects() {
        viewModelScope.launch {
            val response = RetrofitClient.projectApi.getBestProjects()
            if (response.isSuccessful) {
                response.body()?.result?.let {
                    _bestProjects.value = it
                }
            } else {
                println("API 실패: ${response.code()}")
            }
        }
    }

    fun getProjectsByCategory(category: String) {
        viewModelScope.launch {
            val response = RetrofitClient.projectApi.getProjectsByCategory(category)
            if (response.isSuccessful) {
                response.body()?.result?.let {
                    _categoryProjects.value = it
                }
            } else {
                println("API 실패: ${response.code()}")
            }
        }
}
}