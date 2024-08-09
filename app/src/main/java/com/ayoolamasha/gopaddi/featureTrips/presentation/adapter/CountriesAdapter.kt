package com.ayoolamasha.gopaddi.featureTrips.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ayoolamasha.gopaddi.databinding.ItemCountriesRecyclerDesignBinding
import com.ayoolamasha.gopaddi.featureTrips.domain.model.CountriesUIData

class CountriesAdapter(private var onCountryClick: (CountriesUIData) -> Unit) : ListAdapter<CountriesUIData, CountriesAdapter.CountriesViewHolder>(BanksCallBack),
    Filterable {

    private var countryList: ArrayList<CountriesUIData> = ArrayList()
    var countryListFiltered: ArrayList<CountriesUIData> = ArrayList()

    inner class CountriesViewHolder(private val binding: ItemCountriesRecyclerDesignBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun binds(items: CountriesUIData) {
            binding.apply {
                countryData = items
                executePendingBindings()
                setClickListener { onCountryClick(items) }
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CountriesViewHolder {
        return CountriesViewHolder(
            ItemCountriesRecyclerDesignBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(
        holder: CountriesViewHolder,
        position: Int
    ) {
        holder.binds(countryListFiltered[position])
    }


    object BanksCallBack : DiffUtil.ItemCallback<CountriesUIData>() {
        override fun areItemsTheSame(oldItem: CountriesUIData, newItem: CountriesUIData): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: CountriesUIData,
            newItem: CountriesUIData
        ): Boolean {
            return oldItem == newItem
        }

    }
    fun setData(list: List<CountriesUIData>) {
        this.countryList = list as ArrayList<CountriesUIData>
        countryListFiltered = countryList
        submitList(list)
    }

    override fun getItemCount(): Int {
        return countryListFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    countryListFiltered = countryList
                } else {
                    val resultList = ArrayList<CountriesUIData>()
                    for (row in countryList) {
                        if (row.name?.lowercase()
                                ?.contains(constraint.toString().lowercase()) == true
                        ) {
                            resultList.add(row)
                        }
                    }
                    countryListFiltered = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = countryListFiltered
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                countryListFiltered = results?.values as ArrayList<CountriesUIData>
                submitList(countryListFiltered)

            }
        }
    }
}