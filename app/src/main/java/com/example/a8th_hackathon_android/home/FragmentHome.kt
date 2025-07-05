package com.example.a8th_hackathon_android.home


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.databinding.FragmentHomeBinding
import com.example.a8th_hackathon_android.model.TabItem
import com.example.a8th_hackathon_android.ui.TabAdapter
import android.content.Intent
import com.example.a8th_hackathon_android.FundingActivity

class FragmentHome : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    // 탭 데이터 리스트
    private val tabList = mutableListOf(
        TabItem("전체", R.drawable.ic_all, true),
        TabItem("마감임박", R.drawable.ic_favor),
        TabItem("예술", R.drawable.ic_art),
        TabItem("출판", R.drawable.ic_book),
        TabItem("잡화", R.drawable.ic_cart)
    )

    private lateinit var tabAdapter: TabAdapter
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupTabs()
        setupViewPager()
        setupTabPagerSync()


        // 버튼 클릭 시 FundingActivity로 이동
        binding.btnTogether.setOnClickListener {
            val intent = Intent(requireContext(), FundingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupTabs() {
        tabAdapter = TabAdapter(tabList) { position ->
            // 선택된 탭만 true, 나머지는 false
            tabList.forEachIndexed { index, tab ->
                tab.isSelected = index == position
            }
            tabAdapter.notifyDataSetChanged()

            // ViewPager 이동
            binding.viewPager.currentItem = position
        }

        binding.rvTabs.apply {
            adapter = tabAdapter
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun setupViewPager() {
        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewPager.adapter = viewPagerAdapter
    }

    private fun setupTabPagerSync() {
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                tabList.forEachIndexed { index, tab ->
                    tab.isSelected = index == position
                }
                tabAdapter.notifyDataSetChanged()
            }
        })
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}