package com.example.a8th_hackathon_android.funding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.databinding.FragmentFundingInfoBinding
import androidx.lifecycle.ViewModel


class FundingInfoFragment : Fragment() {
    private lateinit var binding: FragmentFundingInfoBinding
    private var selectedCategory: String? = null  // 선택된 카테고리 저장용

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFundingInfoBinding.inflate(inflater, container, false)

        // Spinner 이벤트 처리
        binding.spinnerCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectedCategory = parent.getItemAtPosition(position).toString()
                Toast.makeText(requireContext(), "선택된 카테고리: $selectedCategory", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedCategory = null
            }
        }

        // "다음" 버튼 클릭 시 FundingStoryFragment로 이동
        binding.btnNext.setOnClickListener {


            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FundingStoryFragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }
}