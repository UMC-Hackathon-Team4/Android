package com.example.a8th_hackathon_android.model

data class TabItem(
    val title: String,  // 탭 이름
    val icon: Int,      // drawable 리소스 ID
    var isSelected: Boolean = false // 선택 여부
)
