package com.ayoolamasha.gopaddi.featureTrips.presentation.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ayoolamasha.gopaddi.R
import com.ayoolamasha.gopaddi.databinding.ItemHomeCategoryRecyclerDesignBinding
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetCategoryTagsUIData

class HomeCategoryAdapter(private var onQuickAmountClicked: (GetCategoryTagsUIData) -> Unit) :
    ListAdapter<GetCategoryTagsUIData, HomeCategoryAdapter.CategoryViewHolder>(
        QuickAmountCallBack,
    ) {
    private var selectedItem = RecyclerView.NO_POSITION

    inner class CategoryViewHolder(private var binding: ItemHomeCategoryRecyclerDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binds(items: GetCategoryTagsUIData) {
            binding.apply {
                category = items
                executePendingBindings()
                setClickListener {
                    onQuickAmountClicked(items)
                    val previousSelectedItem = selectedItem
                    selectedItem = absoluteAdapterPosition
                    notifyItemChanged(previousSelectedItem)
                    notifyItemChanged(selectedItem)
                }

                if (selectedItem == absoluteAdapterPosition) {
                    binding.quickAmountLinear.background =
                        ContextCompat.getDrawable(
                            binding.quickAmountLinear.context,
                            R.drawable.home_dashboard_background,
                        )
                    binding.quickAmount.setTextColor(Color.parseColor("#FFFFFF"))
                } else {
                    binding.quickAmountLinear.background =
                        ContextCompat.getDrawable(
                            binding.quickAmountLinear.context,
                            R.drawable.white_label_opacity_background,
                        )
                    binding.quickAmount.setTextColor(Color.parseColor("#676E7E"))
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): HomeCategoryAdapter.CategoryViewHolder {
        return CategoryViewHolder(
            ItemHomeCategoryRecyclerDesignBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false,
            ),
        )
    }

    override fun onBindViewHolder(
        holder: HomeCategoryAdapter.CategoryViewHolder,
        position: Int,
    ) {
        val quickItems = getItem(position)
        if (quickItems != null) {
            holder.binds(quickItems)
        }
    }

    object QuickAmountCallBack : DiffUtil.ItemCallback<GetCategoryTagsUIData>() {
        override fun areItemsTheSame(
            oldItem: GetCategoryTagsUIData,
            newItem: GetCategoryTagsUIData,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: GetCategoryTagsUIData,
            newItem: GetCategoryTagsUIData,
        ): Boolean {
            return oldItem.name == newItem.name
        }
    }
}