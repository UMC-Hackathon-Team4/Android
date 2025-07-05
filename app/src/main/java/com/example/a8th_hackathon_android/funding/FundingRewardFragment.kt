package com.example.a8th_hackathon_android.funding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.databinding.FragmentFundingInfoBinding
import com.example.a8th_hackathon_android.databinding.FragmentFundingRewardBinding

class FundingRewardFragment : Fragment() {
    private lateinit var binding: FragmentFundingRewardBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFundingRewardBinding.inflate(inflater, container, false)

        binding.btnNext.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FundingAddFragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }
}