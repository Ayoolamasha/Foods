package com.ayoolamasha.gopaddi.featureTrips.presentation.adapter

import android.graphics.Bitmap
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ayoolamasha.gopaddi.databinding.ItemImagesRecyclerDesignBinding

class ImageAdapter(
    private var imageList: MutableList<Bitmap>,
    private val onRemoveClick: (Int) -> Unit
) : ListAdapter<Bitmap, ImageAdapter.ImageViewHolder>(
    ImageDiffCallBack,
) {
    inner class ImageViewHolder(private val binding: ItemImagesRecyclerDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binds(item: Bitmap, position: Int) {
            binding.apply {
                foodImages.setImageBitmap(item)
                btnRemove.setOnClickListener { onRemoveClick(position) } // Ensure button is clickable
                executePendingBindings()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        return ImageViewHolder(
            ItemImagesRecyclerDesignBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.binds(imageList[position], position)
    }

    override fun getItemCount(): Int = imageList.size


    fun setData(list: MutableList<Bitmap>) {
        this.imageList = list
        submitList(ArrayList(imageList))
    }

    fun removeItem(position: Int) {
        imageList.removeAt(position)
        submitList(ArrayList(imageList))
    }

    object ImageDiffCallBack : DiffUtil.ItemCallback<Bitmap>() {
        override fun areItemsTheSame(
            oldItem: Bitmap,
            newItem: Bitmap,
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Bitmap,
            newItem: Bitmap,
        ): Boolean {
            return oldItem.sameAs(newItem)
        }
    }
}
