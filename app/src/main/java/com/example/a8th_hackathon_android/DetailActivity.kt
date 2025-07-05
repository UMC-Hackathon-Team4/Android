package com.example.a8th_hackathon_android

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Looper
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.os.Handler
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.a8th_hackathon_android.databinding.ActivityDetailBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    private lateinit var adapter: DetailPagerAdapter

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

        adapter = DetailPagerAdapter(rewards) // 전역 adapter 초기화!
        binding.viewPager.adapter = adapter

        binding.btnJoin.setOnClickListener {
            showRewardBottomSheet()
        }
    }
    private fun showRewardBottomSheet() {
        val dialog = BottomSheetDialog(this)
        val sheetView = layoutInflater.inflate(R.layout.bottomsheet_reward, null)
        val layoutRewards = sheetView.findViewById<LinearLayout>(R.id.layoutRewards)
        val btnConfirm = sheetView.findViewById<Button>(R.id.btnBottomCheck)  // 확인 버튼
        val selectedItems = linkedSetOf<View>()  // 순서를 기억하는 LinkedHashSet로 변경

        rewards.forEach { reward ->
            val itemView = layoutInflater.inflate(R.layout.item_reward_bottomsheet, layoutRewards, false)
            val tvTitle = itemView.findViewById<TextView>(R.id.tvRewardTitle)
            val tvDesc = itemView.findViewById<TextView>(R.id.tvRewardDesc)
            val ivCheck = itemView.findViewById<ImageView>(R.id.ivCheck)

            val tvLeftCount = itemView.findViewById<TextView>(R.id.tvRewardLeftCount)
            if (reward.leftCount > 0) {
                tvLeftCount.visibility = View.VISIBLE
                tvLeftCount.text = "${reward.leftCount}개 남음"
            } else {
                tvLeftCount.visibility = View.GONE
            }


            tvTitle.text = reward.title
            tvDesc.text = reward.description
            ivCheck.setImageResource(R.drawable.ic_check_circle_outline)

            itemView.setOnClickListener {
                if (selectedItems.contains(itemView)) {
                    ivCheck.setImageResource(R.drawable.ic_check_circle_outline)
                    selectedItems.remove(itemView)
                } else {
                    ivCheck.setImageResource(R.drawable.ic_check_circle_filled)
                    selectedItems.add(itemView)
                }
                updateConfirmButtonState(btnConfirm, selectedItems.isNotEmpty())
            }

            layoutRewards.addView(itemView)
        }

        updateConfirmButtonState(btnConfirm, false)  // 처음엔 선택 없음

        btnConfirm.setOnClickListener {
            if (selectedItems.isNotEmpty()) {
                dialog.dismiss()  // 리워드 바텀시트 닫기

                // 리워드 리스트 순서(rewards)에 맞춰 선택된 아이템 정렬
                val selectedRewards = rewards.filter { reward ->
                    selectedItems.any { item ->
                        val title = item.findViewById<TextView>(R.id.tvRewardTitle)?.text?.toString()
                        title == reward.title
                    }
                }

                showQuantityBottomSheet(selectedRewards)
            }
        }

        dialog.setContentView(sheetView)
        dialog.show()
        dialog.window?.clearFlags(android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }


    private fun updateConfirmButtonState(button: Button, hasSelection: Boolean) {
        if (hasSelection) {
            button.setBackgroundColor(Color.parseColor("#699DE5"))
            button.isEnabled = true
        } else {
            button.setBackgroundColor(Color.parseColor("#D0D0D0"))
            button.isEnabled = false
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
            tvPrice.text = reward.title  // 금액

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

            layoutQuantities.addView(itemView)
        }

        updateTotalPrice(btnPayment, quantities)

        btnPayment.setOnClickListener {
            quantities.forEach { (reward, purchasedQuantity) ->
                if (reward.leftCount > 0) {
                    reward.leftCount -= purchasedQuantity
                    if (reward.leftCount < 0) reward.leftCount = 0
                }
            }

            dialog.dismiss()

            // 여기서 ViewPager의 어댑터를 교체해 강제로 리프레시
            adapter = DetailPagerAdapter(rewards)
            binding.viewPager.adapter = adapter

            showCompletionDialog()
        }

        dialog.setContentView(sheetView)
        dialog.show()
        dialog.window?.clearFlags(android.view.WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    private fun updateTotalPrice(button: Button, quantities: Map<RewardData, Int>) {
        var total = 0
        quantities.forEach { (reward, count) ->
            val price = parseAmount(reward.title)
            total += price * count
        }
        button.text = "총 %,d원 함께하기".format(total)
    }

    private fun parseAmount(text: String): Int {
        return text.replace(",", "")
            .replace("원", "")
            .replace("+", "")
            .trim()
            .toIntOrNull() ?: 0
    }

    private fun updateRewardPrice(tvPrice: TextView, reward: RewardData, quantity: Int) {
        val pricePerUnit = parseAmount(reward.title)
        val totalPrice = pricePerUnit * quantity
        tvPrice.text = "%,d원".format(totalPrice)
    }

    private fun showCompletionDialog() {
        val dialog = Dialog(this)
        val view = layoutInflater.inflate(R.layout.dialog_completion, null)
        dialog.setContentView(view)

        // Dialog의 배경을 둥근 모서리가 포함된 투명 배경으로 설정
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        // Dialog 크기를 wrap_content로 해서 중앙 배치되도록 설정
        dialog.window?.setLayout(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )

        // Dialog를 화면 중앙에 위치
        dialog.window?.setGravity(Gravity.CENTER)

        dialog.show()

        // 3초 후 자동 종료
        Handler(Looper.getMainLooper()).postDelayed({
            dialog.dismiss()
        }, 3000)
    }

    data class RewardData(val title: String, val description: String, var leftCount: Int)
}
