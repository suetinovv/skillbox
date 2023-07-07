package com.example.practice.ui.photo_info

import android.app.DownloadManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Context.NOTIFICATION_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.practice.App
import com.example.practice.MainActivity
import com.example.practice.R
import com.example.practice.State
import com.example.practice.databinding.FragmentPhotoInfoBinding
import com.google.android.material.snackbar.Snackbar
import java.io.File

private const val ARG_PARAM1 = "param1"

class PhotoInfoFragment : Fragment() {

    private var _binding: FragmentPhotoInfoBinding? = null
    private val binding get() = _binding!!
    private val photoInfoViewModel: PhotoInfoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PhotoInfoViewModel(
                    App.component.getUnsplashRepository()
                ) as T
            }
        }
    }
    private var param1: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        photoInfoViewModel.loadPhotoInfo(param1!!)
//        val moshi = Moshi.Builder()
//            .add(KotlinJsonAdapterFactory())
//            .build()
//        photoItem = moshi.adapter(PhotoItem::class.java).fromJson(param1!!)!!
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoInfoBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope
            .launchWhenCreated {
                photoInfoViewModel.data.collect {
                    if (it != null) {
                        with(binding) {
                            with(it) {
                                textName.text = user.name ?: ""
                                textNikName.text = "@" + user.username
                                textCountLike.text = likes.toString()

                                if (liked_by_user) {
                                    imageLike.setImageResource(R.drawable.like)
                                } else {
                                    imageLike.setImageResource(R.drawable.dislike)
                                }
                                textLocation.text = location?.city.toString()

                                val str = StringBuilder()
                                tags?.forEach { tag ->
                                    if (tag.type == "landing_page") {
                                        str.append("#${tag.title} ")
                                    }
                                }
                                textHashTag.text = str
                                madeWith.text = getString(R.string.made_with) + exif.make
                                model.text = getString(R.string.model) + exif.model
                                exposure.text = getString(R.string.exposure) + exif.exposure_time
                                aperture.text = getString(R.string.aperture) + exif.aperture
                                focalLength.text =
                                    getString(R.string.focal_length) + exif.focal_length
                                iso.text = getString(R.string.iso) + exif.iso
                                about.text = getString(R.string.about) + "@${user.username}: "

                                textCountDownload.text = downloads.toString()

                                Glide
                                    .with(imageView.context)
                                    .load(urls.small)
                                    .error(R.drawable.smile)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .placeholder(R.drawable.status_load)
                                    .into(imageView)
                                Glide
                                    .with(imageAvatar.context)
                                    .load(user.profile_image?.small)
                                    .error(R.drawable.smile)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .placeholder(R.drawable.status_load)
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(imageAvatar)
                            }
                        }
                    }
                }
            }

        val toolbar = activity?.findViewById<ImageButton>(R.id.toolbar_button)
        toolbar?.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Check out this photo on Unsplash")
            shareIntent.putExtra(
                Intent.EXTRA_TEXT,
                "https://unsplash.com/photos/${photoInfoViewModel.data.value?.id}"
            )
            startActivity(Intent.createChooser(shareIntent, "Share link using"))
        }

        binding.imageLike.setOnClickListener {
            val isConnected = isInternetAvailable(App.context)
            if (isConnected) {
                if (photoInfoViewModel.data.value?.liked_by_user!!) {
                    photoInfoViewModel.unlike(photoInfoViewModel.data.value?.id!!)
                } else {
                    photoInfoViewModel.setLike(photoInfoViewModel.data.value?.id!!)
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
        binding.buttonImageDownload.setOnClickListener {
            val connectivityManager =
                context?.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                val downloadManager = context?.getSystemService(DOWNLOAD_SERVICE) as DownloadManager
                val request =
                    DownloadManager.Request(Uri.parse(photoInfoViewModel.data.value?.urls?.raw))
                        .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                        .setAllowedOverRoaming(false)
                        .setTitle("Загрузка фотографии")
                        .setDescription("Загрузка фотографии в исходном качестве")
                        .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                        .setDestinationInExternalPublicDir(
                            Environment.DIRECTORY_DOWNLOADS,
                            "${photoInfoViewModel.data.value?.id}.jpg"
                        )
                downloadManager.enqueue(request)

                val snackbar = Snackbar.make(view, "Фотография загружена", Snackbar.LENGTH_LONG)
                snackbar.setAction("Просмотреть") {
                    val photoFile = File(
                        Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                        "photo.jpg"
                    )
                    val photoUri = FileProvider.getUriForFile(
                        App.context,
                        App.context.packageName + ".provider",
                        photoFile
                    )
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.setDataAndType(photoUri, "image/*")
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    App.context.startActivity(intent)
                }
                snackbar.show()

                val notificationManager =
                    context?.getSystemService(NOTIFICATION_SERVICE) as NotificationManager
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val name = "Channel Name"
                    val descriptionText = "Channel Description"
                    val importance = NotificationManager.IMPORTANCE_DEFAULT
                    val channel = NotificationChannel("channel_id", name, importance).apply {
                        description = descriptionText
                    }
                    notificationManager.createNotificationChannel(channel)
                }

                val notificationIntent = Intent(context, MainActivity::class.java)
                notificationIntent.flags =
                    Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                val pendingIntent = PendingIntent.getActivity(
                    context,
                    0,
                    notificationIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
                val builder = NotificationCompat.Builder(this.requireContext(), "channel_id")
                    .setSmallIcon(R.drawable.baseline_notifications_24)
                    .setContentTitle("Фотография загружена")
                    .setContentText("Нажмите, чтобы открыть")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                notificationManager.notify(777, builder.build())
            } else {
                // сохранение ссылки на фотографию для загрузки позже
            }


        }


        viewLifecycleOwner.lifecycleScope
            .launchWhenCreated {
                photoInfoViewModel.state.collect { state ->
                    when (state) {
                        State.Success -> {
                            binding.errorLayout.visibility = View.GONE
                            binding.progressLayout.visibility = View.GONE
                            binding.infoLayout.visibility = View.VISIBLE
                        }

                        State.Loading -> {
                            binding.progressLayout.visibility = View.VISIBLE
                            binding.errorLayout.visibility = View.GONE
                            binding.infoLayout.visibility = View.VISIBLE
                        }

                        is State.Error -> {
                            binding.progressLayout.visibility = View.GONE
                            binding.errorLayout.visibility = View.VISIBLE
                            binding.infoLayout.visibility = View.INVISIBLE
                            Toast.makeText(
                                requireContext(),
                                "${state.throwable.message}",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }

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

    companion object {
        fun newInstance(param1: String) =
            PhotoInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}
