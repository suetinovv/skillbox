package com.example.practice.collection_paging

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.practice.R
import com.example.practice.data.collections.CollectionsItem
import com.example.practice.databinding.CollectionItemBinding

class CollectionListAdapter(private val onClick: (CollectionsItem) -> Unit) :
    PagingDataAdapter<CollectionsItem, CollectionViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CollectionViewHolder {
        return CollectionViewHolder(
            CollectionItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: CollectionViewHolder, position: Int) {
        val item = getItem(position)
        with(holder.binding) {
            textName.text = item?.user?.name ?: ""
            textNikName.text = "@${item?.user?.username}"
            textNameCollection.text = item?.title
            textCount.text = item?.total_photos.toString()

            item?.let {
                Glide
                    .with(imageView.context)
                    .load(it.cover_photo?.urls?.small)
                    .error(R.drawable.smile)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.status_load)
                    .into(imageView)
                Glide
                    .with(imageAvatar.context)
                    .load(it.user?.profile_image?.small)
                    .error(R.drawable.smile)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.status_load)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageAvatar)
            }
        }

        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<CollectionsItem>() {
    override fun areItemsTheSame(oldItem: CollectionsItem, newItem: CollectionsItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CollectionsItem, newItem: CollectionsItem): Boolean {
        return oldItem == newItem
    }

}

class CollectionViewHolder(val binding: CollectionItemBinding) : RecyclerView.ViewHolder(binding.root) {
}