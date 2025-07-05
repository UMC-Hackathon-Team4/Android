package com.example.a8th_hackathon_android.project


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a8th_hackathon_android.databinding.ItemProjectBinding
import com.example.a8th_hackathon_android.project.ProjectItem

class ProjectAdapter(
    private val items: List<ProjectItem>
) : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    inner class ProjectViewHolder(private val binding: ItemProjectBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ProjectItem) {
            binding.ivThumbnail.setImageResource(item.thumbnail)
            binding.tvTitle.text = item.title
            binding.tvDesc.text = item.description
            binding.pointText.text = item.participants.toString()
            // 하트 등 다른 UI 처리 필요시 여기에!
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemProjectBinding.inflate(inflater, parent, false)
        return ProjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}