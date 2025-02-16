package com.ayoolamasha.gopaddi.featureTrips.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ayoolamasha.gopaddi.databinding.ItemBottomSheetDesignBinding
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetCategoryTagsUIData

class CategoryTagAdapter (private var onCategoryTagClick: (GetCategoryTagsUIData) -> Unit) :
    ListAdapter<GetCategoryTagsUIData, CategoryTagAdapter.CategoryTagViewHolder>(TripsDiffCallBack) {

    inner class CategoryTagViewHolder(private val binding: ItemBottomSheetDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binds(items: GetCategoryTagsUIData) {
            binding.apply {
                categoryTag = items
                executePendingBindings()
                setClickListener { onCategoryTagClick(items) }
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CategoryTagViewHolder {
        return CategoryTagViewHolder(
            ItemBottomSheetDesignBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: CategoryTagViewHolder, position: Int) {
        val foodUiData = getItem(position)
        holder.binds(foodUiData)
    }

    object TripsDiffCallBack : DiffUtil.ItemCallback<GetCategoryTagsUIData>() {
        override fun areItemsTheSame(
            oldItem: GetCategoryTagsUIData,
            newItem: GetCategoryTagsUIData
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: GetCategoryTagsUIData,
            newItem: GetCategoryTagsUIData
        ): Boolean {
            return false
        }

    }


}