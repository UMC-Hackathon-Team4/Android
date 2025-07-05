package com.example.a8th_hackathon_android.funding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.databinding.FragmentFundingRewardBinding
import com.example.a8th_hackathon_android.model.RewardDto
import com.example.a8th_hackathon_android.model.RewardViewModel

class FundingRewardFragment : Fragment() {
    private lateinit var binding: FragmentFundingRewardBinding
    private val rewardViewModel: RewardViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFundingRewardBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener {
            addRewardCard()
        }

        binding.btnNext.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FundingAddFragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }

    private fun addRewardCard() {
        val title = binding.rewardName.text.toString()
        val quantity = binding.rewardNumber.text.toString()
        val price = binding.rewardPrice.text.toString()

        if (title.isBlank() || quantity.isBlank() || price.isBlank()) {
            Toast.makeText(requireContext(), "모든 항목을 입력해 주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val reward = RewardDto(title, price.toLong(), quantity.toLong())
        rewardViewModel.addReward(reward)

        val rewardCard = layoutInflater.inflate(R.layout.item_reward_card, binding.rewardCardContainer, false)
        rewardCard.findViewById<TextView>(R.id.reward_name).text = title
        rewardCard.findViewById<TextView>(R.id.reward_quantity).text = "${quantity}개"
        rewardCard.findViewById<TextView>(R.id.reward_price).text = "${price}원"

        rewardCard.findViewById<TextView>(R.id.btn_delete).setOnClickListener {
            binding.rewardCardContainer.removeView(rewardCard)
        }

        binding.rewardCardContainer.addView(rewardCard)

        binding.rewardName.setText("")
        binding.rewardNumber.setText("")
        binding.rewardPrice.setText("")
    }
}