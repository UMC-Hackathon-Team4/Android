package com.example.a8th_hackathon_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a8th_hackathon_android.home.FragmentHome


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 처음 실행 시에만 Fragment를 추가
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, FragmentHome())
                .commit()
        }


    }
}