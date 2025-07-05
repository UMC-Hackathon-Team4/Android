package com.example.a8th_hackathon_android.home


import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 5

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> AllFragment()
            1 -> FavorFragment()
            2 -> ArtFragment()
            3 -> BookFragment()
            else -> GoodsFragment()
        }
    }
}