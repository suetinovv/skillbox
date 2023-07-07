package com.example.recycler.locationpaget

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recycler.databinding.LocationItemBinding
import com.example.recycler.models.location.Location


class LocationPagingListAdapter :
    PagingDataAdapter<Location, LocationViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        return LocationViewHolder(
            LocationItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            textName.text = item?.name ?: ""
            textType.text = item?.type ?: ""
            textDimension.text = item?.dimension ?: ""
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<Location>() {
    override fun areItemsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Location, newItem: Location): Boolean {
        return oldItem == newItem
    }

}

class LocationViewHolder(val binding: LocationItemBinding) : RecyclerView.ViewHolder(binding.root)