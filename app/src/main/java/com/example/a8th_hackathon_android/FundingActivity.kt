package com.example.a8th_hackathon_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.a8th_hackathon_android.databinding.ActivityFundingBinding

class FundingActivity : AppCompatActivity() {
    // 바인딩 변수
    private lateinit var binding: ActivityFundingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 바인딩 초기화
        binding = ActivityFundingBinding.inflate(layoutInflater)
        setContentView(binding.root)


    }

}