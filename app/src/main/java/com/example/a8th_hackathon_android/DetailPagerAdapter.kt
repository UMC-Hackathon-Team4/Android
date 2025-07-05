package com.example.a8th_hackathon_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout

class DetailPagerAdapter : RecyclerView.Adapter<DetailPagerAdapter.DetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_viewpager_detail, parent, false)
        return DetailViewHolder(view)
    }

    override fun getItemCount(): Int = 1  // 페이지가 하나라면 1로 고정

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind()
    }

    inner class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tabLayout: TabLayout = itemView.findViewById(R.id.tabLayout)
        private val layoutIntro: View = itemView.findViewById(R.id.layoutIntro)
        private val layoutStory: View = itemView.findViewById(R.id.layoutStory)
        private val layoutReward: View = itemView.findViewById(R.id.layoutReward)

        fun bind() {
            // 초기 상태: 첫 번째 탭(소개) 표시
            showTabContent(0)

            // 탭 추가
            tabLayout.addTab(tabLayout.newTab().setText("소개"))
            tabLayout.addTab(tabLayout.newTab().setText("스토리"))
            tabLayout.addTab(tabLayout.newTab().setText("리워드"))

            // 탭 선택 리스너
            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    showTabContent(tab.position)
                }
                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }

        private fun showTabContent(position: Int) {
            // 모든 레이아웃 숨기기
            layoutIntro.visibility = View.GONE
            layoutStory.visibility = View.GONE
            layoutReward.visibility = View.GONE

            // 선택된 탭에 맞게 표시
            when (position) {
                0 -> layoutIntro.visibility = View.VISIBLE
                1 -> layoutStory.visibility = View.VISIBLE
                2 -> layoutReward.visibility = View.VISIBLE
            }
        }
    }
}
