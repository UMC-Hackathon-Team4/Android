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

    override fun getItemCount(): Int = 1

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind()
    }

    inner class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tabLayout: TabLayout = itemView.findViewById(R.id.tabLayout)
        private val layoutIntro: View = itemView.findViewById(R.id.layoutIntro)
        private val layoutStory: View = itemView.findViewById(R.id.layoutStory)
        private val layoutReward: View = itemView.findViewById(R.id.layoutReward)

        fun bind() {
            showTabContent(0) // 기본은 첫 번째 탭

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    showTabContent(tab.position)
                }
                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }

        private fun showTabContent(position: Int) {
            layoutIntro.visibility = if (position == 0) View.VISIBLE else View.GONE
            layoutStory.visibility = if (position == 1) View.VISIBLE else View.GONE
            layoutReward.visibility = if (position == 2) View.VISIBLE else View.GONE
        }
    }
}
