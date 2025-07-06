package com.example.a8th_hackathon_android.funding

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.databinding.FragmentFundingStoryBinding
import com.example.a8th_hackathon_android.model.ProjectViewModel

class FundingStoryFragment : Fragment() {
    private lateinit var binding: FragmentFundingStoryBinding
    private lateinit var viewModel: ProjectViewModel

    private var selectedImageViewId: Int = 0
    private var coverImageUrl: String? = null  // 실제 URL은 나중에 S3 등과 연동할 때 설정

    companion object {
        const val REQUEST_IMAGE_PICK = 1001
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentFundingStoryBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity())[ProjectViewModel::class.java]

        // 이미지 선택 처리
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

        // "다음" 버튼 클릭 시 데이터 저장 후 이동
        binding.btnNext.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val summary = binding.etSummary.text.toString()
            val description = binding.etIntro.text.toString()
            val story = binding.etStory.text.toString()

            if (title.isBlank() || summary.isBlank() || description.isBlank() || story.isBlank()) {
                showToast("모든 항목을 입력해주세요.")
                return@setOnClickListener
            }

            // ViewModel에 데이터 저장
            viewModel.updateStepTwoData(
                title = title,
                summary = summary,
                description = description,
                story = story,
                imageUrl = coverImageUrl  // 현재는 null
            )

            // 다음 Fragment로 이동
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
                    R.id.iv_cover_image -> {
                        binding.ivCoverImage.setImageURI(it)
                        coverImageUrl = it.toString()  // 임시로 URI를 문자열로 저장 (S3 업로드 후 변경 필요)
                    }
                    R.id.iv_intro_image1 -> binding.ivIntroImage1.setImageURI(it)
                    R.id.iv_intro_image2 -> binding.ivIntroImage2.setImageURI(it)
                    R.id.iv_intro_image3 -> binding.ivIntroImage3.setImageURI(it)
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
}
