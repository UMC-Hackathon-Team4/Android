package com.example.a8th_hackathon_android.funding

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.databinding.ActivityFundingBinding
import com.example.a8th_hackathon_android.databinding.ActivityFundingDetailBinding

class FundingDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFundingDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFundingDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 처음엔 1단계 프래그먼트로 시작
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FundingInfoFragment())
            .commit()

        // 뒤로가기 버튼
        binding.btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        // 도움말 버튼
        binding.btnHelp.setOnClickListener {
            Toast.makeText(this, "도움말 기능은 준비 중입니다.", Toast.LENGTH_SHORT).show()
        }

        // ViewModel에서 단계 이동 감지해서 ProgressBar 갱신하는 것도 가능
        // 또는 각 Fragment에서 단계 수동 갱신
    }
}
