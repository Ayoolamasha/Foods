package com.ayoolamasha.gopaddi.featureTrips.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ayoolamasha.gopaddi.databinding.ItemTripRecyclerDesignBinding
import com.ayoolamasha.gopaddi.featureTrips.domain.model.TripsUIData

class TripsAdapter (private var onTripClick: (TripsUIData) -> Unit) :
    ListAdapter<TripsUIData, TripsAdapter.TripsViewHolder>(TripsDiffCallBack) {

    inner class TripsViewHolder(private val binding: ItemTripRecyclerDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binds(items: TripsUIData) {
            binding.apply {
                tripData = items
                executePendingBindings()
                setClickListener { onTripClick(items) }

            }

        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TripsViewHolder {
        return TripsViewHolder(
            ItemTripRecyclerDesignBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: TripsViewHolder, position: Int) {
        val reportUICase = getItem(position)
        holder.binds(reportUICase)
    }

    object TripsDiffCallBack : DiffUtil.ItemCallback<TripsUIData>() {
        override fun areItemsTheSame(
            oldItem: TripsUIData,
            newItem: TripsUIData
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: TripsUIData,
            newItem: TripsUIData
        ): Boolean {
            return false
        }

    }


}