package com.example.practice.ui.photo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
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
import com.example.practice.databinding.FragmentPhotoBinding
import com.example.practice.photo_paging.PhotoListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val ARG_PARAM1 = "param1"

class PhotoFragment : Fragment() {

    private var _binding: FragmentPhotoBinding? = null
    private val binding get() = _binding!!

    private val photoViewModel: PhotoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return PhotoViewModel(
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
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = photoAdapter
        photoViewModel.load()

        binding.rebootButton.setOnClickListener {
            photoAdapter.refresh()
        }

        photoViewModel.pagedPhotos.onEach {
            photoAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        val toolbar = activity?.findViewById<ImageButton>(R.id.toolbar_button)
        val searchEditText = activity?.findViewById<EditText>(R.id.searchEditText)
        toolbar?.setOnClickListener {
            if (searchEditText?.visibility == View.GONE) {
                searchEditText.visibility = View.VISIBLE
                searchEditText.requestFocus()
            } else {
                photoViewModel.searchPhoto(searchEditText?.text.toString())
                photoAdapter.refresh()
            }
        }

        viewLifecycleOwner.lifecycleScope
            .launchWhenCreated {
                photoViewModel.state.collect { state ->
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
                photoViewModel.position.collect {
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
            photoViewModel.unlike(item.id, position)
        } else {
            photoViewModel.setLike(item.id, position)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}