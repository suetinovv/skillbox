package com.example.practice.photo_paging

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.practice.App
import com.example.practice.R
import com.example.practice.data.photo.PhotoItem
import com.example.practice.databinding.PhotoItemBinding
import kotlinx.coroutines.delay

class PhotoListAdapter(
    private val onClick: (PhotoItem) -> Unit,
    private val onClickLike: (PhotoItem, Int) -> Unit
) :
    PagingDataAdapter<PhotoItem, PhotoViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoViewHolder {
        return PhotoViewHolder(
            PhotoItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PhotoViewHolder, position: Int) {
        var item = getItem(position)
        with(holder.binding) {
            textName.text = item?.user?.name ?: ""
            textNikName.text = "@${item?.user?.username}" ?: ""
            textCountLike.text = item?.likes.toString() ?: ""
            if (item?.liked_by_user!!) {
                buttonImageLike.setImageResource(R.drawable.like)
            } else {
                buttonImageLike.setImageResource(R.drawable.dislike)
            }

            item.let {
                Glide
                    .with(imageView.context)
                    .load(it.urls.small)
                    .error(R.drawable.smile)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.status_load)
                    .into(imageView)
                Glide
                    .with(imageAvatar.context)
                    .load(it.user.profile_image.small)
                    .error(R.drawable.smile)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .placeholder(R.drawable.status_load)
                    .apply(RequestOptions.circleCropTransform())
                    .into(imageAvatar)
            }

            holder.binding.buttonImageLike.setOnClickListener {

                val isConnected = isInternetAvailable(App.context)
                if (isConnected) {
                    item.let {
                        onClickLike(item, position)
                    }

                } else {
                    Toast.makeText(
                        App.context,
                        R.string.text_error_load,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            }

            holder.binding.root.setOnClickListener {
                item.let {
                    onClick(item)
                }
            }

        }
    }

    fun updateItem(position: Int) {
        notifyItemChanged(position)
    }


    private fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val networkCapabilities = connectivityManager.activeNetwork ?: return false
            val actNw =
                connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
            return actNw.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
        } else {
            val networkInfo = connectivityManager.activeNetworkInfo ?: return false
            return networkInfo.isConnectedOrConnecting
        }
    }


}

class DiffUtilCallback : DiffUtil.ItemCallback<PhotoItem>() {
    override fun areItemsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PhotoItem, newItem: PhotoItem): Boolean {
        return oldItem == newItem
    }

}

class PhotoViewHolder(val binding: PhotoItemBinding) : RecyclerView.ViewHolder(binding.root) {
}