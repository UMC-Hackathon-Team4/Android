package com.example.a8th_hackathon_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.a8th_hackathon_android.databinding.ActivityMainBinding
import com.example.a8th_hackathon_android.FragmentSplash // 필요하면 Home 대신 Splash로 시작
// import com.example.a8th_hackathon_android.home.FragmentHome

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.fragmentContainerView.id, FragmentSplash())
                .commit()
        }
    }
}