import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.api.ProjectDetail
import com.example.a8th_hackathon_android.detail.DetailActivity
import com.google.android.material.tabs.TabLayout

class DetailPagerAdapter(
    private var rewards: List<DetailActivity.RewardData>,
    private var detail: ProjectDetail
) : RecyclerView.Adapter<DetailPagerAdapter.DetailViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_viewpager_detail, parent, false)
        return DetailViewHolder(view)
    }

    override fun getItemCount(): Int = 1  // 페이지는 하나만 사용

    override fun onBindViewHolder(holder: DetailViewHolder, position: Int) {
        holder.bind()
    }

    fun updateData(newRewards: List<DetailActivity.RewardData>, newDetail: ProjectDetail) {
        rewards = newRewards
        detail = newDetail
        notifyDataSetChanged() // RecyclerView에 데이터 변경 알림
    }

    inner class DetailViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tabLayout: TabLayout = itemView.findViewById(R.id.tabLayout)
        private val layoutIntro: View = itemView.findViewById(R.id.layoutIntro)
        private val layoutStory: View = itemView.findViewById(R.id.layoutStory)
        private val layoutReward: View = itemView.findViewById(R.id.layoutReward)

        init {
            tabLayout.removeAllTabs()
            tabLayout.addTab(tabLayout.newTab().setText("소개"))
            tabLayout.addTab(tabLayout.newTab().setText("스토리"))
            tabLayout.addTab(tabLayout.newTab().setText("리워드"))

            tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab) {
                    showTabContent(tab.position)
                }
                override fun onTabUnselected(tab: TabLayout.Tab) {}
                override fun onTabReselected(tab: TabLayout.Tab) {}
            })
        }

        fun bind() {
            showTabContent(0)
            bindProjectDetail()
            bindRewards()
        }

        private fun bindProjectDetail() {
            // 카테고리별 기본 이미지 리소스
            val defaultImageRes = when (detail.category.uppercase()) {
                "ART" -> R.drawable.dummy_art
                "PUBLISHING" -> R.drawable.dummy_book
                "GOODS" -> R.drawable.dummy_goods
                else -> R.drawable.dummy_art // 기본값
            }

            // 대표 이미지에 적용
            val imageMain = itemView.findViewById<ImageView>(R.id.imageMain)
            imageMain.setImageResource(defaultImageRes)

            // 텍스트 뷰들 적용
            itemView.findViewById<TextView>(R.id.tv_title).text = detail.projectTitle
            itemView.findViewById<TextView>(R.id.tv_description).text = detail.summary
            itemView.findViewById<TextView>(R.id.tv_participants).text = "${detail.supportersCount}명이 함께해요"
            itemView.findViewById<TextView>(R.id.tv_amount).text = "${detail.percentage} 달성"
            itemView.findViewById<TextView>(R.id.tv_category).text = detail.category
        }

        private fun bindRewards() {
            val rewardContainer = itemView.findViewById<LinearLayout>(R.id.layoutReward_item)
            rewardContainer.visibility = View.VISIBLE
            rewardContainer.removeAllViews()

            val inflater = LayoutInflater.from(itemView.context)
            rewards.forEach { reward ->
                val rewardView = inflater.inflate(R.layout.item_reward, rewardContainer, false)
                rewardView.findViewById<TextView>(R.id.tvRewardTitle).text = reward.title
                rewardView.findViewById<TextView>(R.id.tvRewardDesc).text = reward.description

                val tvLeftCount = rewardView.findViewById<TextView>(R.id.tvRewardLeftCount)
                when {
                    reward.leftCount > 0 -> {
                        tvLeftCount.visibility = View.VISIBLE
                        tvLeftCount.text = "${reward.leftCount}개 남음"
                    }
                    reward.leftCount == 0 -> {
                        tvLeftCount.visibility = View.VISIBLE
                        tvLeftCount.text = "품절"
                    }
                    else -> {
                        tvLeftCount.visibility = View.GONE
                    }
                }

                rewardContainer.addView(rewardView)
            }
        }

        private fun showTabContent(position: Int) {
            layoutIntro.visibility = View.GONE
            layoutStory.visibility = View.GONE
            layoutReward.visibility = View.GONE

            when (position) {
                0 -> layoutIntro.visibility = View.VISIBLE
                1 -> layoutStory.visibility = View.VISIBLE
                2 -> layoutReward.visibility = View.VISIBLE
            }
        }
    }
}
