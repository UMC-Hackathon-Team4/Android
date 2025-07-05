package com.example.a8th_hackathon_android.funding

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import androidx.fragment.app.Fragment
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.databinding.FragmentFundingStoryBinding

class FundingStoryFragment : Fragment() {
    private lateinit var binding: FragmentFundingStoryBinding

    private var selectedImageViewId: Int = 0 // 클릭된 ImageView의 ID 저장용

    companion object {
        const val REQUEST_IMAGE_PICK = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFundingStoryBinding.inflate(inflater, container, false)

        // 각 이미지뷰 클릭 시 이미지 선택 인텐트 실행
        binding.ivCoverImage.setOnClickListener {
            selectedImageViewId = R.id.iv_cover_image
            openGallery()
        }

        binding.ivIntroImage1.setOnClickListener {
            selectedImageViewId = R.id.iv_intro_image1
            openGallery()
        }

        binding.ivIntroImage2.setOnClickListener {
            selectedImageViewId = R.id.iv_intro_image2
            openGallery()
        }

        binding.ivIntroImage3.setOnClickListener {
            selectedImageViewId = R.id.iv_intro_image3
            openGallery()
        }

        // 다음 버튼 → 리워드 Fragment로
        binding.btnNext.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, FundingRewardFragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.type = "image/*"
        startActivityForResult(intent, REQUEST_IMAGE_PICK)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_PICK && resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = data?.data

            imageUri?.let {
                when (selectedImageViewId) {
                    R.id.iv_cover_image -> binding.ivCoverImage.setImageURI(it)
                    R.id.iv_intro_image1 -> binding.ivIntroImage1.setImageURI(it)
                    R.id.iv_intro_image2 -> binding.ivIntroImage2.setImageURI(it)
                    R.id.iv_intro_image3 -> binding.ivIntroImage3.setImageURI(it)
                }
            }
        }
    }
}