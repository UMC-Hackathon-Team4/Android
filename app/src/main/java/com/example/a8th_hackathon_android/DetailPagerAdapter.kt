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

class DetailPagerAdapter(private val rewards: List<DetailActivity.RewardData>) : RecyclerView.Adapter<DetailPagerAdapter.DetailViewHolder>() {

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

        init {
            // 탭은 ViewHolder 생성 시 한 번만 초기화
            tabLayout.removeAllTabs()
            tabLayout.addTab(tabLayout.newTab().setText("소개"))
            tabLayout.addTab(tabLayout.newTab().setText("스토리"))
            tabLayout.addTab(tabLayout.newTab().setText("리워드"))

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    showTabContent(tab.position)
                }
                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }

        fun bind() {
            showTabContent(0) // 탭 초기 표시

            val rewardContainer = itemView.findViewById<LinearLayout>(R.id.layoutReward_item)
            rewardContainer.visibility = View.VISIBLE
            rewardContainer.removeAllViews()

            val inflater = LayoutInflater.from(itemView.context)
            for (reward in rewards) {
                val rewardView = inflater.inflate(R.layout.item_reward, rewardContainer, false)
                rewardView.findViewById<TextView>(R.id.tvRewardTitle).text = reward.title
                rewardView.findViewById<TextView>(R.id.tvRewardDesc).text = reward.description

                val tvLeftCount = rewardView.findViewById<TextView>(R.id.tvRewardLeftCount)
                if (reward.leftCount > 0) {
                    tvLeftCount.visibility = View.VISIBLE
                    tvLeftCount.text = "${reward.leftCount}개 남음"
                } else if (reward.leftCount != -1) {
                    tvLeftCount.visibility = View.VISIBLE
                    tvLeftCount.text = "품절"
                } else {
                    tvLeftCount.visibility = View.GONE // 후원하기 예외 처리
                }

                rewardContainer.addView(rewardView)
            }
        }

        private fun showTabContent(position: Int) {
            layoutIntro.visibility = View.GONE
            layoutStory.visibility = View.GONE
            layoutReward.visibility = View.GONE

            when (position) {
                0 -> layoutIntro.visibility = View.VISIBLE
                1 -> layoutStory.visibility = View.VISIBLE
                2 -> layoutReward.visibility = View.VISIBLE
            }
        }
    }

}
