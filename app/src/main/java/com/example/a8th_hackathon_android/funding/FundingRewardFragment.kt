package com.example.a8th_hackathon_android.funding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.api.RetrofitClient
import com.example.a8th_hackathon_android.api.RetrofitClient.projectApi
import com.example.a8th_hackathon_android.databinding.FragmentFundingRewardBinding
import com.example.a8th_hackathon_android.home.AllFragment
import com.example.a8th_hackathon_android.model.ProjectViewModel
import com.example.a8th_hackathon_android.model.RewardDto
import com.example.a8th_hackathon_android.model.RewardRequest
import com.example.a8th_hackathon_android.model.RewardViewModel
import kotlinx.coroutines.launch

class FundingRewardFragment : Fragment() {
    private lateinit var binding: FragmentFundingRewardBinding
    private val rewardViewModel: RewardViewModel by activityViewModels()
    private val projectViewModel: ProjectViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFundingRewardBinding.inflate(inflater, container, false)

        binding.btnSave.setOnClickListener {
            addRewardCard()
        }

        binding.btnNext.setOnClickListener {
            submitProjectToServer()
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

    private fun submitProjectToServer() {
        val rewardDtos = rewardViewModel.getRewardList()

        if (rewardDtos.isEmpty()) {
            Toast.makeText(requireContext(), "최소 1개의 리워드를 추가해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        val rewards = rewardDtos.map {
            RewardRequest(
                title = it.title,
                stock = it.stock,
                price = it.price
            )
        }

        // ViewModel에 반영
        projectViewModel.updateRewards(rewards)

        val request = projectViewModel.request.value
        if (request == null) {
            Toast.makeText(requireContext(), "프로젝트 정보가 없습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        // ✅ 하드코딩 토큰 사용
        val hardcodedToken = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNzUxNzU1OTc4LCJleHAiOjE3NTIxMTU5Nzh9.EvPTfKYV6RIs96sxleyfkA8zZs-2RpTI6Ma9xzwcK6Q"

        val projectApi = RetrofitClient.projectApi

        lifecycleScope.launch {
            try {
                val response = projectApi.registerProject(
                    token = hardcodedToken,
                    request = request
                )

                if (response.isSuccessful) {
                    Toast.makeText(requireContext(), "등록 성공!", Toast.LENGTH_SHORT).show()

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container_view, AllFragment())
                        .addToBackStack(null)
                        .commit()
                } else {
                    Toast.makeText(requireContext(), "등록 실패 (${response.code()})", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                Toast.makeText(requireContext(), "서버 오류 발생: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}