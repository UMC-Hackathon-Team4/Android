package com.example.a8th_hackathon_android

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
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
            showTabContent(0)

            // 탭 설정
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

            // 리워드 데이터 추가
            val rewardContainer = itemView.findViewById<LinearLayout>(R.id.layoutReward_item)
            rewardContainer.visibility = View.VISIBLE
            rewardContainer.removeAllViews()

            val rewards = listOf(
                RewardData("1,000원", "후원만 하기", ""),
                RewardData("10,000원", "[응원 펀딩] 감사카드+ 작가 엽서 1종", "36개 남음"),
                RewardData("20,000원", "[기본 리워드] 제작 주머니 1개 + 감사카드", "12개 남음"),
                RewardData("30,000원", "[응원 리워드] 주머니 1개 + 엽서 세트 (3종) + 감사카드", "5개 남음"),
                RewardData("50,000원", "[스페셜 리워드] 주머니 2개(색상 랜덤) + 손글씨 메시지 카드 + 엽서 세트 + 제작 과정 미니 영상 링크", "2개 남음")
            )

            val inflater = LayoutInflater.from(itemView.context)
            for (reward in rewards) {
                val rewardView = inflater.inflate(R.layout.item_reward, rewardContainer, false)
                rewardView.findViewById<TextView>(R.id.tvRewardTitle).text = reward.title
                rewardView.findViewById<TextView>(R.id.tvRewardDesc).text = reward.description

                val tvLeftCount = rewardView.findViewById<TextView>(R.id.tvRewardLeftCount)
                if (reward.leftCount.isNotEmpty()) {
                    tvLeftCount.visibility = View.VISIBLE
                    tvLeftCount.text = reward.leftCount
                } else {
                    tvLeftCount.visibility = View.GONE
                }

                rewardContainer.addView(rewardView)
            }
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
