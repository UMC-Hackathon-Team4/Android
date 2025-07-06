package com.example.a8th_hackathon_android.detail

import DetailPagerAdapter
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil.setContentView
import androidx.lifecycle.lifecycleScope
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.api.ProjectDetail
import com.example.a8th_hackathon_android.api.RetrofitClient
import com.example.a8th_hackathon_android.databinding.ActivityDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: DetailPagerAdapter

    private var currentProjectDetail: ProjectDetail? = null

    private val rewards = mutableListOf(
        RewardData("1,000원", "후원만 하기", -1),
        RewardData("10,000원", "[응원 펀딩] 감사카드+ 작가 엽서 1종", 36),
        RewardData("20,000원", "[기본 리워드] 제작 주머니 1개 + 감사카드", 12),
        RewardData("30,000원", "[응원 리워드] 주머니 1개 + 엽서 세트 (3종) + 감사카드", 5),
        RewardData("50,000원", "[스페셜 리워드] 주머니 2개(색상 랜덤) + 손글씨 메시지 카드 + 엽서 세트 + 제작 과정 미니 영상 링크", 2)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.setNavigationOnClickListener { finish() }

        val projectId = intent.getLongExtra("projectId", -1)
        if (projectId != -1L) {
            loadProjectDetail(projectId)
        }

        binding.btnJoin.setOnClickListener { showRewardBottomSheet() }
    }



    private fun loadProjectDetail(projectId: Long) {
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.projectApi.getProjectDetail(projectId, "detail")
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body?.isSuccess == true) {
                        val detail = body.result
                        currentProjectDetail = detail
                        adapter = DetailPagerAdapter(rewards, detail)
                        binding.viewPager.adapter = adapter

                    } else {
                        // 서버가 isSuccess=false로 응답했을 때
                        Log.e("DetailActivity", "서버 응답 실패: ${body?.message}")
                        showToast("프로젝트 정보를 불러오지 못했습니다: ${body?.message ?: "알 수 없음"}")
                    }
                } else {
                    // HTTP 오류 발생 시 (ex. 404, 500 등)
                    Log.e("DetailActivity", "HTTP 실패: code=${response.code()}, message=${response.message()}")
                    showToast("프로젝트 정보를 불러오지 못했습니다 (HTTP ${response.code()})")
                }
            } catch (e: Exception) {
                e.printStackTrace()
                showToast("네트워크 오류가 발생했습니다.")
            }
        }
    }


    private fun showRewardBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val sheetView = layoutInflater.inflate(R.layout.bottomsheet_reward, null)
        val layoutRewards = sheetView.findViewById<LinearLayout>(R.id.layoutRewards)
        val btnConfirm = sheetView.findViewById<Button>(R.id.btnBottomCheck)
        val selectedItems = linkedSetOf<View>()

        rewards.forEach { reward ->
            val itemView = layoutInflater.inflate(R.layout.item_reward_bottomsheet, layoutRewards, false)
            val tvTitle = itemView.findViewById<TextView>(R.id.tvRewardTitle)
            val tvDesc = itemView.findViewById<TextView>(R.id.tvRewardDesc)
            val ivCheck = itemView.findViewById<ImageView>(R.id.ivCheck)
            val tvLeftCount = itemView.findViewById<TextView>(R.id.tvRewardLeftCount)

            tvTitle.text = reward.title
            tvDesc.text = reward.description

            if (reward.leftCount > 0) {
                tvLeftCount.visibility = View.VISIBLE
                tvLeftCount.text = "${reward.leftCount}개 남음"
            } else {
                tvLeftCount.visibility = View.GONE
            }

            itemView.setOnClickListener {
                toggleSelection(itemView, selectedItems, ivCheck)
                updateConfirmButtonState(btnConfirm, selectedItems.isNotEmpty())
            }

            layoutRewards.addView(itemView)
        }

        updateConfirmButtonState(btnConfirm, false)
        btnConfirm.setOnClickListener {
            if (selectedItems.isNotEmpty()) {
                dialog.dismiss()
                val selectedRewards = rewards.filter { reward ->
                    selectedItems.any { item ->
                        item.findViewById<TextView>(R.id.tvRewardTitle)?.text?.toString() == reward.title
                    }
                }
                showQuantityBottomSheet(selectedRewards)
            }
        }

        dialog.setContentView(sheetView)
        dialog.show()
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    private fun toggleSelection(view: View, selected: MutableSet<View>, checkIcon: ImageView) {
        if (selected.contains(view)) {
            checkIcon.setImageResource(R.drawable.ic_check_circle_outline)
            selected.remove(view)
        } else {
            checkIcon.setImageResource(R.drawable.ic_check_circle_filled)
            selected.add(view)
        }
    }

    private fun showQuantityBottomSheet(selectedRewards: List<RewardData>) {
        val dialog = BottomSheetDialog(this)
        val sheetView = layoutInflater.inflate(R.layout.bottomsheet_quantity, null)
        val layoutQuantities = sheetView.findViewById<LinearLayout>(R.id.layoutQuantities)
        val btnPayment = sheetView.findViewById<Button>(R.id.btnProceedPayment)

        val quantities = mutableMapOf<RewardData, Int>()

        selectedRewards.forEach { reward ->
            val itemView = layoutInflater.inflate(R.layout.item_quantity_reward, layoutQuantities, false)
            val tvDesc = itemView.findViewById<TextView>(R.id.tvRewardDesc)
            val tvPrice = itemView.findViewById<TextView>(R.id.tvRewardPrice)
            val tvQuantity = itemView.findViewById<TextView>(R.id.tvQuantity)
            val btnMinus = itemView.findViewById<View>(R.id.btnMinus)
            val btnPlus = itemView.findViewById<View>(R.id.btnPlus)

            tvDesc.text = reward.description
            var quantity = 1
            tvQuantity.text = quantity.toString()
            quantities[reward] = quantity

            btnPlus.setOnClickListener {
                if (reward.leftCount == -1 || quantity < reward.leftCount) {
                    quantity++
                    tvQuantity.text = quantity.toString()
                    quantities[reward] = quantity
                    updateRewardPrice(tvPrice, reward, quantity)
                    updateTotalPrice(btnPayment, quantities)
                }
            }

            btnMinus.setOnClickListener {
                if (quantity > 1) {
                    quantity--
                    tvQuantity.text = quantity.toString()
                    quantities[reward] = quantity
                    updateRewardPrice(tvPrice, reward, quantity)
                    updateTotalPrice(btnPayment, quantities)
                }
            }

            updateRewardPrice(tvPrice, reward, quantity)
            layoutQuantities.addView(itemView)
        }

        updateTotalPrice(btnPayment, quantities)

        btnPayment.setOnClickListener {
            quantities.forEach { (reward, qty) ->
                if (reward.leftCount > 0) {
                    reward.leftCount = maxOf(0, reward.leftCount - qty)
                }
            }
            adapter.notifyDataSetChanged() // 리워드 남은 수량 UI 갱신
            dialog.dismiss()
            showCompletionDialog()
        }

        dialog.setContentView(sheetView)
        dialog.show()
        dialog.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    private fun updateTotalPrice(button: Button, quantities: Map<RewardData, Int>) {
        val total = quantities.entries.sumOf { (reward, qty) -> parseAmount(reward.title) * qty }
        button.text = "총 %,d원 함께하기".format(total)
    }

    private fun parseAmount(text: String): Int {
        return text.replace(",", "").replace("원", "").trim().toIntOrNull() ?: 0
    }

    private fun updateRewardPrice(tvPrice: TextView, reward: RewardData, quantity: Int) {
        val totalPrice = parseAmount(reward.title) * quantity
        tvPrice.text = "%,d원".format(totalPrice)
    }

    private fun showCompletionDialog() {
        val dialog = Dialog(this).apply {
            setContentView(layoutInflater.inflate(R.layout.dialog_completion, null))
            window?.apply {
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
                setGravity(Gravity.CENTER)
            }
            show()
        }
        Handler(Looper.getMainLooper()).postDelayed({ dialog.dismiss() }, 3000)
    }

    private fun updateConfirmButtonState(button: Button, hasSelection: Boolean) {
        button.apply {
            setBackgroundColor(if (hasSelection) Color.parseColor("#699DE5") else Color.parseColor("#D0D0D0"))
            isEnabled = hasSelection
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    data class RewardData(val title: String, val description: String, var leftCount: Int)
}
