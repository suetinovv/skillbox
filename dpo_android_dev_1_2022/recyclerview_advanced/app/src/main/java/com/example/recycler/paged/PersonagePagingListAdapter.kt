package com.example.recycler.paged


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recycler.R
import com.example.recycler.databinding.PersonageItemBinding
import com.example.recycler.models.Personage

class PersonagePagingListAdapter :
    PagingDataAdapter<Personage, PersonageViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonageViewHolder {
        return PersonageViewHolder(
            PersonageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PersonageViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            textName.text = item?.name ?: ""
            textStatus.text = "${item?.status} - ${item?.species}"
            when (item?.status) {
                ("unknown") -> imageStatus.setImageResource(R.drawable.black_circle_24)
                ("Alive") -> imageStatus.setImageResource(R.drawable.green_circle_24)
                ("Dead") -> imageStatus.setImageResource(R.drawable.red_circle_24)
            }
            textLocation.text = item?.location?.name ?: ""
            item?.let {
                Glide
                    .with(imageView.context)
                    .load(it.image)
                    .into(imageView)
            }
        }
//        holder.binding.root.setOnClickListener {
//            item?.let {
//                onClick(item)
//            }
//        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<Personage>() {
    override fun areItemsTheSame(oldItem: Personage, newItem: Personage): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Personage, newItem: Personage): Boolean {
        return oldItem == newItem
    }

}

class PersonageViewHolder(val binding: PersonageItemBinding) : RecyclerView.ViewHolder(binding.root)