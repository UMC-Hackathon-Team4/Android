package com.example.a8th_hackathon_android.home


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.funding.FundingActivity
import com.example.a8th_hackathon_android.databinding.FragmentHomeAllBinding
import com.example.a8th_hackathon_android.project.ProjectAdapter
import com.example.a8th_hackathon_android.project.ProjectItem

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


        //이후 정보 박아서 구현
        val dummyList = listOf(
            ProjectItem(R.drawable.dummy_image, "함께손잡기 프로젝트", "창작자들이 만든 손끝의 응원 팔찌", 28239),
            ProjectItem(R.drawable.dummy_image, "박석옥 작가", "수아에서 연결받은 감각 기반 회화 전시회", 20000),
            ProjectItem(R.drawable.dummy_image, "따숨협동조합", "정성이 담긴 손뜨개 힐링 담요 제작", 38030)
        )

        val adapter = ProjectAdapter(dummyList)
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(requireContext())
    }







    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}