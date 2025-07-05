package com.example.a8th_hackathon_android

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2

class DetailActivity : AppCompatActivity() {

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        viewPager = findViewById(R.id.viewPager)

        val adapter = DetailPagerAdapter()
        viewPager.adapter = adapter
    }
}
