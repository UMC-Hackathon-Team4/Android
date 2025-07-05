package com.example.a8th_hackathon_android.funding

import android.content.Intent
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.databinding.ActivityFundingBinding
import com.example.a8th_hackathon_android.databinding.ActivityFundingDetailBinding

class FundingDetailActivity : AppCompatActivity() {
    // 바인딩 변수
    private lateinit var binding: ActivityFundingDetailBinding

    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 뷰 바인딩으로 레이아웃 설정 (이것만 남긴다)
        binding = ActivityFundingDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // FundingInfoFragment 초기 설정
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, FundingInfoFragment())
            .commit()

        // 뒤로 가기 버튼 클릭 시 FundingActivity로 이동
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, FundingActivity::class.java)
            startActivity(intent)
        }

    }
}