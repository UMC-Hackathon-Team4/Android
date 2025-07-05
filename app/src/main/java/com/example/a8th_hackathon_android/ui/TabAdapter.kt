package com.example.a8th_hackathon_android.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.a8th_hackathon_android.databinding.ItemTabBinding
import com.example.a8th_hackathon_android.model.TabItem

class TabAdapter(
    private val items: List<TabItem>,
    private val clickListener: (position: Int) -> Unit
) : RecyclerView.Adapter<TabAdapter.TabViewHolder>() {

    inner class TabViewHolder(private val binding: ItemTabBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: TabItem) {
            binding.item = item

            // 선택 상태에 따라 배경 selector 동작
            binding.root.isSelected = item.isSelected

            binding.root.setOnClickListener {
                clickListener(adapterPosition)
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TabViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemTabBinding.inflate(inflater, parent, false)
        return TabViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TabViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}