package com.example.a8th_hackathon_android.funding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.a8th_hackathon_android.databinding.FragmentFundingAddBinding

class FundingAddFragment : Fragment() {
    private lateinit var binding: FragmentFundingAddBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFundingAddBinding.inflate(inflater, container, false)

        binding.btnAdd.setOnClickListener {
            addRewardCard()
        }

        return binding.root
    }

    private fun addRewardCard() {
        val rewardCard = binding.rewardContainer

        // 원하는 값들 동적으로 설정 가능
        // rewardCard.findViewById<TextView>(R.id.reward_price).text = "20,000원"

        // btn_add 위에 항상 추가되도록
        val index = binding.rewardContainer.indexOfChild(binding.btnAdd)
        binding.rewardContainer.addView(rewardCard, index)
    }
}