package com.example.a8th_hackathon_android

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.databinding.FragmentSplashBinding
import com.example.a8th_hackathon_android.home.FragmentHome

class FragmentSplash : Fragment() {

    private var _binding: FragmentSplashBinding? = null
    private val binding get() = _binding!!  // 안전 접근

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSplashBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 3초 후 홈 화면으로 이동
        Handler(Looper.getMainLooper()).postDelayed({
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_view, FragmentHome())
                .commit()
        }, 3000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}