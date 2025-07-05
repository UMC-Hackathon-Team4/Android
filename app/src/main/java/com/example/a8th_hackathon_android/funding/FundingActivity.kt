package com.example.a8th_hackathon_android.funding

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a8th_hackathon_android.MainActivity
import com.example.a8th_hackathon_android.databinding.ActivityFundingBinding

class FundingActivity : AppCompatActivity() {
    // 바인딩 변수
    private lateinit var binding: ActivityFundingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 바인딩 초기화
        binding = ActivityFundingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 상단 도움말 버튼 클릭 시 Notion URL 열기
        binding.btnHelp.setOnClickListener {
            val notionUrl = "https://peat-question-790.notion.site/227e48b7f631807c9b1dd74b0ef9165b?source=copy_link"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(notionUrl)
            }
            startActivity(intent)
        }

        // "함께해요" 버튼 클릭 → FundingActivity로 이동
        binding.btnJoin.setOnClickListener {
            val intent = Intent(this, FundingDetailActivity::class.java)
            startActivity(intent)
        }

        binding.btnBack.setOnClickListener {
            finish()
        }
    }

}