package com.example.a8th_hackathon_android.funding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.databinding.FragmentFundingStoryBinding

class FundingStoryFragment : Fragment() {
    private lateinit var binding: FragmentFundingStoryBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFundingStoryBinding.inflate(inflater, container, false)

        // "다음" 버튼 클릭 시 FundingStoryFragment로 이동
        binding.btnNext.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FundingRewardFragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }
}