package com.example.a8th_hackathon_android.project



import android.content.Intent

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.databinding.ItemProjectBinding

import com.example.a8th_hackathon_android.model.DeadlineProjectItem



class ProjectAdapter :
    ListAdapter<DeadlineProjectItem, ProjectAdapter.ProjectViewHolder>(DiffCallback()) {

    inner class ProjectViewHolder(private val binding: ItemProjectBinding)
        : RecyclerView.ViewHolder(binding.root) {


        fun bind(item: DeadlineProjectItem) {
            //  카테고리별 기본 이미지 리소스
            val defaultImageRes = when (item.category.uppercase()) {
                "ART" -> R.drawable.dummy_art
                "PUBLISHING" -> R.drawable.dummy_book
                "GOODS" -> R.drawable.dummy_goods
                else -> R.drawable.dummy_art // 기본값
            }
            Glide.with(binding.ivThumbnail.context)  // context 안전
                .load(defaultImageRes)
                .into(binding.ivThumbnail)

            binding.tvTitle.text = item.projectTitle
            binding.tvDesc.text = item.category  // 필요시 description으로 매핑
            binding.pointText.text = item.currentAmount.toString()

//        fun bind(item: ProjectItem) {
//            binding.ivThumbnail.setImageResource(item.thumbnail)
//            binding.tvTitle.text = item.title
//            binding.tvDesc.text = item.description
//            binding.pointText.text = item.participants.toString()
//            // 하트 등 다른 UI 처리 필요시 여기에!
//
//            binding.root.setOnClickListener {
//                val context = it.context
//                val intent = Intent(context, DetailActivity::class.java).apply {
//                    // putExtra("projectId", item.projectId)
//                    putExtra("projectId", 1L)
//                }
//                context.startActivity(intent)
//            }
//>>>>>>> 70c0a19a3ee7d8b77be58c365aa1b6d7c7e02bee
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProjectBinding.inflate(inflater, parent, false)
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<DeadlineProjectItem>() {
        override fun areItemsTheSame(oldItem: DeadlineProjectItem, newItem: DeadlineProjectItem) =
            oldItem.projectId == newItem.projectId

        override fun areContentsTheSame(oldItem: DeadlineProjectItem, newItem: DeadlineProjectItem) =
            oldItem == newItem
    }
}


//import android.view.LayoutInflater
//import android.view.ViewGroup
//import androidx.recyclerview.widget.RecyclerView
//import com.example.a8th_hackathon_android.databinding.ItemProjectBinding
//import com.example.a8th_hackathon_android.project.ProjectItem
//
//class ProjectAdapter(
//    private val items: List<ProjectItem>
//) : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {
//
//    inner class ProjectViewHolder(private val binding: ItemProjectBinding)
//        : RecyclerView.ViewHolder(binding.root) {
//
//        fun bind(item: ProjectItem) {
//            binding.ivThumbnail.setImageResource(item.thumbnail)
//            binding.tvTitle.text = item.title
//            binding.tvDesc.text = item.description
//            binding.pointText.text = item.participants.toString()
//            // 하트 등 다른 UI 처리 필요시 여기에!
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        val binding = ItemProjectBinding.inflate(inflater, parent, false)
//        return ProjectViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
//        holder.bind(items[position])
//    }
//
//    override fun getItemCount(): Int = items.size
//}