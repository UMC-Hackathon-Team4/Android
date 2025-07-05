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

        // 작품 정보 확장/축소 토글 처리
        binding.btnToggleInfo.setOnClickListener {
            val isVisible = binding.workInfoDetail.visibility == View.VISIBLE
            binding.workInfoDetail.visibility = if (isVisible) View.GONE else View.VISIBLE
            binding.btnToggleInfo.setImageResource(
                if (isVisible) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up
            )
        }

        // 스토리 작성 확장/축소 토글 처리
        binding.btnToggleStory.setOnClickListener{
            val isVisible = binding.storyDetail.visibility == View.VISIBLE
            binding.storyDetail.visibility = if (isVisible) View.GONE else View.VISIBLE
            binding.btnToggleStory.setImageResource(
                if (isVisible) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up
            )
        }

        // 리워드 작성 확장/축소 토클 처리
        binding.btnToggleReward.setOnClickListener{
            val isVisible = binding.rewardDetail.visibility == View.VISIBLE
            binding.rewardDetail.visibility = if (isVisible) View.GONE else View.VISIBLE
            binding.btnToggleReward.setImageResource(
                if (isVisible) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up
            )
        }
    }

}