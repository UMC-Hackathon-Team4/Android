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
            // 대표 이미지 (imageMain)
            val imageMain = itemView.findViewById<ImageView>(R.id.imageMain)
            Glide.with(itemView.context)
                .load(detail.imageUrl) // 서버에서 준 sample.png 등
                .placeholder(R.drawable.dummy_art) // 대체 이미지
                .into(imageMain)

            // 카테고리 (tv_category)
            itemView.findViewById<TextView>(R.id.tv_category).text = detail.category

            // 제목 (tv_title)
            itemView.findViewById<TextView>(R.id.tv_title).text = detail.projectTitle

            // 설명 (tv_description)
            itemView.findViewById<TextView>(R.id.tv_description).text = detail.summary

            // 참여 수 (tv_participants)
            itemView.findViewById<TextView>(R.id.tv_participants).text = "${detail.supportersCount}명이 함께해요"

            // 달성 퍼센트 (tv_amount)
            itemView.findViewById<TextView>(R.id.tv_amount).text = "${detail.percentage} 달성"

            // 팀 이름 (tv_team_name)
            itemView.findViewById<TextView>(R.id.tv_team_name).text = detail.creatorNickname

            // 팀 소개 (tv_team_intro)
            itemView.findViewById<TextView>(R.id.tv_team_intro).text = detail.creatorDetail

            // 소개 탭 제목 (layoutIntro_title)
            itemView.findViewById<TextView>(R.id.layoutIntro_title).text = detail.projectTitle

            // 소개 탭 내용 (layoutIntro_content)
            itemView.findViewById<TextView>(R.id.layoutIntro_content).text = detail.summary

            // 소개 탭 이미지 (layoutIntro_image)
            val introImage = itemView.findViewById<ImageView>(R.id.layoutIntro_image)
            Glide.with(itemView.context)
                .load(detail.imageUrl)
                .placeholder(R.drawable.dummy_art)
                .into(introImage)
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
