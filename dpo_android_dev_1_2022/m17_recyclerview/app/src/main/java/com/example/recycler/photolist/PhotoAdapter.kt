package com.example.recycler.photolist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.recycler.databinding.PhotoItemBinding
import com.example.recycler.models.Photo

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
            textViewCamera.text = "Camera: ${item?.camera?.name ?: ""}"
            textViewRover.text = "Rover: ${item?.rover?.name ?: ""}"
            textViewSol.text = "Sol: ${item?.sol.toString() ?: ""}"
            textViewDate.text = "Date: ${item?.earth_date ?: ""}"
            item?.let {
                Glide
                    .with(imageView.context)
                    .load(it.img_src)
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