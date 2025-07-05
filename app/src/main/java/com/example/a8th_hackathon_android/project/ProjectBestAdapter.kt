package com.example.a8th_hackathon_android.project


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a8th_hackathon_android.R
import com.example.a8th_hackathon_android.databinding.ItemProjectBinding
import com.example.a8th_hackathon_android.model.ProjectBestItem

class ProjectBestAdapter :
    ListAdapter<ProjectBestItem, ProjectBestAdapter.ProjectViewHolder>(DiffCallback()) {

    inner class ProjectViewHolder(private val binding: ItemProjectBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProjectBestItem) {
            val defaultImageRes = when (item.category.trim().uppercase()) {
                "ART" -> R.drawable.dummy_art
                "PUBLISHING" -> R.drawable.dummy_book
                "GOODS" -> R.drawable.dummy_goods
                else -> R.drawable.dummy_art
            }

            val imageUrl = item.imageUrl

            if (imageUrl.isNullOrEmpty()) {
                // 서버 이미지가 없으면 기본 이미지
                Glide.with(binding.ivThumbnail.context)
                    .load(defaultImageRes)
                    .into(binding.ivThumbnail)
            } else {
                // 서버 이미지 있으면 그것도 시도, 실패하면 기본 이미지
                Glide.with(binding.ivThumbnail.context)
                    .load(imageUrl)
                    .error(defaultImageRes)
                    .into(binding.ivThumbnail)
            }

            binding.tvTitle.text = item.projectTitle
            binding.tvDesc.text = item.category
            binding.pointText.text = item.supportersCount.toString()
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

    class DiffCallback : DiffUtil.ItemCallback<ProjectBestItem>() {
        override fun areItemsTheSame(oldItem: ProjectBestItem, newItem: ProjectBestItem) =
            oldItem.projectId == newItem.projectId

        override fun areContentsTheSame(oldItem: ProjectBestItem, newItem: ProjectBestItem) =
            oldItem == newItem
    }
}