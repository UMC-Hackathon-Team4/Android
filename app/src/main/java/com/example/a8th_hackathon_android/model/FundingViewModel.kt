package com.example.a8th_hackathon_android.model

import android.net.Uri
import androidx.lifecycle.ViewModel


class FundingViewModel : ViewModel() {
    var category: String = ""
    var goalPrice: Int = 0
    var startDate: String = ""  // 예: "2025-07-06"
    var endDate: String = ""

}