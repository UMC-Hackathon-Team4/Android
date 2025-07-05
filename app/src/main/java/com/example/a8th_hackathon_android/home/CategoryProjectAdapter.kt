package com.example.a8th_hackathon_android.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a8th_hackathon_android.databinding.ItemProjectBinding
import com.example.a8th_hackathon_android.home.CategoryProjectItem

class CategoryProjectAdapter :
    ListAdapter<CategoryProjectItem, CategoryProjectAdapter.ProjectViewHolder>(DiffCallback()) {

    inner class ProjectViewHolder(private val binding: ItemProjectBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CategoryProjectItem) {
            Glide.with(binding.ivThumbnail)
                .load(item.imageUrl)
                .into(binding.ivThumbnail)

            binding.tvTitle.text = item.projectTitle
            binding.tvDesc.text = item.category
            binding.pointText.text = item.currentAmount.toString()
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

    class DiffCallback : DiffUtil.ItemCallback<CategoryProjectItem>() {
        override fun areItemsTheSame(oldItem: CategoryProjectItem, newItem: CategoryProjectItem) =
            oldItem.projectId == newItem.projectId

        override fun areContentsTheSame(oldItem: CategoryProjectItem, newItem: CategoryProjectItem) =
            oldItem == newItem
    }
}