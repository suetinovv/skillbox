package com.example.permission.ui

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.permission.App
import com.example.permission.R
import com.example.permission.databinding.FragmentPhotoListBinding
import com.example.permission.model.Photo
import com.example.permission.model.PhotoAdapter
import com.example.permission.model.PhotoListViewModel
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import okhttp3.internal.notify


/**
 * A simple [Fragment] subclass.
 * Use the [PhotoListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PhotoListFragment : Fragment() {
    // TODO: Rename and change types of parameters

    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentPhotoListBinding? = null
    private val binding get() = _binding!!


    private val viewModel: PhotoListViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val photoDao = (activity as MainActivity).photoDao
                return PhotoListViewModel(photoDao) as T
            }
        }
    }

    private val photoAdapter = PhotoAdapter { photo -> onItemClick(photo) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPhotoListBinding.inflate(inflater)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = photoAdapter
        binding.recycler.layoutManager =
            GridLayoutManager(requireContext(), 2, GridLayoutManager.VERTICAL, false)
        binding.buttonCamera.setOnClickListener {
            findNavController().navigate(R.id.CameraFragment)
        }
        viewModel.loadPhotos()

        binding.buttonCrash.setOnClickListener {
            throw RuntimeException("Test Crash")
        }
        binding.buttonNotification.setOnClickListener {
            createNotification()
        }
        viewModel.photos.onEach {
            photoAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            Log.d("token", it.result)
        }

    }

    private fun onItemClick(item: Photo) {
        val bundle = Bundle().apply {
            fragmentManager?.isDestroyed
            putString(
                "param1", item.uri
            )
        }
        findNavController().navigate(R.id.PhotoFullFragment, bundle)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createNotification() {
        val intent = Intent(requireContext(), MainActivity::class.java)

        val pendingIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivities(
                requireContext(),
                0,
                arrayOf(intent),
                PendingIntent.FLAG_IMMUTABLE
            )
        } else {
            PendingIntent.getActivities(
                requireContext(),
                0,
                arrayOf(intent),
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }


        val notification = NotificationCompat.Builder(requireContext(), App.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notification_important_24)
            .setContentTitle("Мое уведомление")
            .setContentText("Описание моего уведомления")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()


        NotificationManagerCompat.from(requireContext()).notify(NOTIFICATION_ID, notification)
    }


    companion object {
        const val NOTIFICATION_ID = 777

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PhotoListFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PhotoListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}