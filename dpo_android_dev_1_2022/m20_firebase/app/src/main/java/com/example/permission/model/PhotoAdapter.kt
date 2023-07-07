package com.example.permission.model

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.permission.databinding.PhotoItemBinding

class PhotoAdapter(private val onClick: (Photo) -> Unit) : RecyclerView.Adapter<PhotoViewHolder>() {
    private var data: List<Photo> = emptyList()
    fun setData(data: List<Photo>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            PhotoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        val item = data.getOrNull(position)
        with(holder.binding) {
            textDate.text = "Date: ${item?.date ?: ""}"
            item?.let {
                Glide
                    .with(imageView.context)
                    .load(it.uri)
                    .into(imageView)
            }
        }
        holder.binding.root.setOnClickListener {
            item?.let {
                onClick(item)
            }
        }
    }
}

class PhotoViewHolder(val binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root)