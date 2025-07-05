package com.example.a8th_hackathon_android.home


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a8th_hackathon_android.funding.FundingActivity
import com.example.a8th_hackathon_android.databinding.FragmentHomeAllBinding

class AllFragment : Fragment() {

    private var _binding: FragmentHomeAllBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeAllBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 여기서 binding 으로 뷰 연결
        // 예: binding.textView.text = "전체 탭!"

        // 버튼 클릭 시 FundingActivity로 이동
        binding.btnTogether.setOnClickListener {
            val intent = Intent(requireContext(), FundingActivity::class.java)
            startActivity(intent)
        }

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}