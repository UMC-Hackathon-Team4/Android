package com.example.a8th_hackathon_android.funding

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import com.example.a8th_hackathon_android.MainActivity
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.databinding.FragmentFundingAddBinding

class FundingAddFragment : Fragment() {
    private lateinit var binding: FragmentFundingAddBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFundingAddBinding.inflate(inflater, container, false)

        binding.btnNext.setOnClickListener {
            showCompleteDialogAndReturn()
        }

        return binding.root
    }

    private fun showCompleteDialogAndReturn() {
        val dialog = Dialog(requireContext())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_submission_complete)
        dialog.setCancelable(false) // 버튼 누르기 전까지 닫히지 않도록 설정

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.show()

        // 1초 후 메인 액티비티로 전환
        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()

            val intent = Intent(requireContext(), MainActivity::class.java)
            intent.putExtra("start_splash", false) // 스플래시 띄우지 않도록 설정
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
            requireActivity().finish()
        }, 1000)
    }
}