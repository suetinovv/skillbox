package com.example.practice.ui.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.practice.App
import com.example.practice.R
import com.example.practice.State
import com.example.practice.data.photo.PhotoItem
import com.example.practice.databinding.FragmentUserPhotoBinding
import com.example.practice.photo_paging.PhotoListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val ARG_PARAM1 = "param1"

class UserPhotoFragment : Fragment() {

    private var _binding: FragmentUserPhotoBinding? = null
    private val binding get() = _binding!!

    private val userPhotoViewModel: UserPhotoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserPhotoViewModel(
                    App.component.getUnsplashRepository()
                ) as T
            }
        }
    }

    private val photoAdapter = PhotoListAdapter(
        onClick = { photo -> onItemClick(photo) },
        onClickLike = { photo, position -> onItemClickLike(photo, position )!! }
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = photoAdapter
        userPhotoViewModel.load()

        binding.rebootButton.setOnClickListener {
            photoAdapter.refresh()
        }

        userPhotoViewModel.pagedPhotos.onEach {
            photoAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope
            .launchWhenCreated {
                userPhotoViewModel.state.collect { state ->
                    when (state) {
                        State.Success -> {
                            binding.progress.visibility = View.INVISIBLE
                            binding.recycler.visibility = View.VISIBLE
                            binding.rebootButton.visibility = View.INVISIBLE
                            binding.errorLayout.visibility = View.INVISIBLE
                        }

                        State.Loading -> {
                            binding.progress.visibility = View.VISIBLE
                            binding.recycler.visibility = View.VISIBLE
                            binding.rebootButton.visibility = View.INVISIBLE
                            binding.errorLayout.visibility = View.INVISIBLE
                        }

                        is State.Error -> {
                            binding.progress.visibility = View.INVISIBLE
                            binding.recycler.visibility = View.VISIBLE
                            binding.rebootButton.visibility = View.INVISIBLE
                            Toast.makeText(
                                requireContext(),
                                R.string.error_load_db,
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }

        viewLifecycleOwner.lifecycleScope
            .launchWhenCreated {
                userPhotoViewModel.position.collect {
                    if (it != -1){
                        photoAdapter.updateItem(it)
                        photoAdapter.refresh()
                    }
                }
            }

    }

    private fun onItemClick(item: PhotoItem) {
        val bundle = Bundle().apply {
            fragmentManager?.isDestroyed
            putString(
                ARG_PARAM1, item.id
            )
        }
        findNavController().navigate(R.id.navigation_photo_info, bundle)
    }

    private fun onItemClickLike(item: PhotoItem, position: Int) {
        if (item.liked_by_user) {
            userPhotoViewModel.unlike(item.id, position)
        } else {
            userPhotoViewModel.setLike(item.id, position)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}