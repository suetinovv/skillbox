package com.example.permission.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.permission.R
import com.example.permission.databinding.FragmentPhotoListBinding
import com.example.permission.model.Photo
import com.example.permission.model.PhotoAdapter
import com.example.permission.model.PhotoListViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


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

        viewModel.photos.onEach {
            photoAdapter.setData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)
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

    companion object {
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