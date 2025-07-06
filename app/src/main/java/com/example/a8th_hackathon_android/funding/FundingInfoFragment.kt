package com.example.a8th_hackathon_android.funding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.databinding.FragmentFundingInfoBinding
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.a8th_hackathon_android.model.ProjectViewModel
import java.time.LocalDate


class FundingInfoFragment : Fragment() {
    private lateinit var binding: FragmentFundingInfoBinding
    private lateinit var viewModel: ProjectViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFundingInfoBinding.inflate(inflater, container, false)

        // ViewModel 초기화 (Activity 범위)
        viewModel = ViewModelProvider(requireActivity())[ProjectViewModel::class.java]

        // "다음" 버튼 클릭
        binding.btnNext.setOnClickListener {
            val category = binding.etCategory.text.toString()
            if (category.isBlank()) {
                showToast("카테고리를 입력해주세요.")
                return@setOnClickListener
            }

            val targetAmount = binding.goalPrice.text.toString().toLongOrNull()
            if (targetAmount == null) {
                showToast("목표 금액을 입력해주세요.")
                return@setOnClickListener
            }

            val startDate = getDateFromInputs(
                binding.startYearTv, binding.startMonthTv, binding.startDayTv
            ) ?: return@setOnClickListener showToast("시작일을 정확히 입력해주세요.")

            val endDate = getDateFromInputs(
                binding.endYearTv, binding.endMonthTv, binding.endDayTv
            ) ?: return@setOnClickListener showToast("종료일을 정확히 입력해주세요.")

            viewModel.saveStepOneData(category, targetAmount, startDate, endDate)

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FundingStoryFragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }

    private fun getDateFromInputs(yearInput: EditText, monthInput: EditText, dayInput: EditText): String? {
        val year = yearInput.text.toString().toIntOrNull()
        val month = monthInput.text.toString().toIntOrNull()
        val day = dayInput.text.toString().toIntOrNull()
        return try {
            if (year != null && month != null && day != null) {
                LocalDate.of(year, month, day).toString()  // yyyy-MM-dd 형식
            } else null
        } catch (e: Exception) {
            null
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
