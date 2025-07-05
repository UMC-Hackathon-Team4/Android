package com.example.a8th_hackathon_android.funding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.databinding.FragmentFundingInfoBinding
import com.example.a8th_hackathon_android.model.FundingViewModel

class FundingInfoFragment : Fragment() {
    private lateinit var binding: FragmentFundingInfoBinding
    private val fundingViewModel: FundingViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFundingInfoBinding.inflate(inflater, container, false)

        binding.btnNext.setOnClickListener {
            // 1. 카테고리
            fundingViewModel.category = binding.etCategory.text.toString()

            // 2. 목표 가격
            fundingViewModel.goalPrice = binding.goalPrice.text.toString().toIntOrNull() ?: 0

            // 3. 시작일 (형식: YYYY-MM-DD)
            val startYear = binding.startYearTv.text.toString().padStart(4, '0')
            val startMonth = binding.startMonthTv.text.toString().padStart(2, '0')
            val startDay = binding.startDayTv.text.toString().padStart(2, '0')
            fundingViewModel.startDate = "$startYear-$startMonth-$startDay"

            // 4. 종료일 (형식: YYYY-MM-DD)
            val endYear = binding.endYearTv.text.toString().padStart(4, '0')
            val endMonth = binding.endMonthTv.text.toString().padStart(2, '0')
            val endDay = binding.endDayTv.text.toString().padStart(2, '0')
            fundingViewModel.endDate = "$endYear-$endMonth-$endDay"

            // 다음 Fragment로 이동
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FundingStoryFragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }
}