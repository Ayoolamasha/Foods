package com.ayoolamasha.gopaddi.featureTrips.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ayoolamasha.gopaddi.R
import com.ayoolamasha.gopaddi.databinding.ItemFoodRecyclerDesignBinding
import com.ayoolamasha.gopaddi.featureTrips.domain.model.GetFoodUiData
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class FoodsAdapter (private var onFoodClick: (GetFoodUiData) -> Unit) :
    ListAdapter<GetFoodUiData, FoodsAdapter.FoodsViewHolder>(FoodDiffCallBack) {

    inner class FoodsViewHolder(private val binding: ItemFoodRecyclerDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binds(items: GetFoodUiData) {
            binding.apply {
                foodData = items
                executePendingBindings()
                setClickListener { onFoodClick(items) }
                addChips(items.foodTags, binding.tagsChips, binding.tagsChips.context)

            }

        }
    }

    fun addChips(tags: List<String>?, chipGroup: ChipGroup, context: Context) {
        chipGroup.removeAllViews()
        tags?.forEach { tag ->
            val chip = Chip(context).apply {
                text = tag
                setTextAppearance(R.style.TagsChips) // Only affects text appearance
                setChipBackgroundColorResource(R.color.pink_ish_color) // Set background
                setTextColor(ContextCompat.getColor(context, R.color.tag_color)) // Set text color
                isCheckable = false
                isClickable = false
                chipCornerRadius = resources.getDimension(R.dimen.padding_8)
            }
            chipGroup.addView(chip)
        }
    }



    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FoodsViewHolder {
        return FoodsViewHolder(
            ItemFoodRecyclerDesignBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: FoodsViewHolder, position: Int) {
        val foodUiData = getItem(position)
        holder.binds(foodUiData)
    }

    object FoodDiffCallBack : DiffUtil.ItemCallback<GetFoodUiData>() {
        override fun areItemsTheSame(
            oldItem: GetFoodUiData,
            newItem: GetFoodUiData
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: GetFoodUiData,
            newItem: GetFoodUiData
        ): Boolean {
            return oldItem.id == newItem.id
        }

    }


}