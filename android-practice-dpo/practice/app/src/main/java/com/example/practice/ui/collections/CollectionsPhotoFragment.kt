package com.example.practice.ui.collections

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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.practice.App
import com.example.practice.R
import com.example.practice.State
import com.example.practice.data.photo.PhotoItem
import com.example.practice.databinding.FragmentCollectionsPhotoBinding
import com.example.practice.photo_paging.PhotoListAdapter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val ARG_PARAM1 = "param1"

class CollectionsPhotoFragment : Fragment() {

    private var _binding: FragmentCollectionsPhotoBinding? = null
    private val binding get() = _binding!!

    private val collectionsPhotoViewModel: CollectionsPhotoViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CollectionsPhotoViewModel(
                    App.component.getUnsplashRepository()
                ) as T
            }
        }
    }

    private var param1: String? = null

    private val photoAdapter = PhotoListAdapter(
        onClick = { photo -> onItemClick(photo) },
        onClickLike = { photo, position -> onItemClickLike(photo, position)!! }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        collectionsPhotoViewModel.load(param1!!)
//        val moshi = Moshi.Builder()
//            .add(KotlinJsonAdapterFactory())
//            .build()
//        photoItem = moshi.adapter(PhotoItem::class.java).fromJson(param1!!)!!
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCollectionsPhotoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope
            .launchWhenCreated {
                collectionsPhotoViewModel.data.observe(viewLifecycleOwner) { collectionInfo ->
                    if (collectionInfo != null) {
                        with(binding) {
                            with(collectionInfo) {
                                textNameCollection.text = title
                                val str = StringBuilder()
                                tags.forEach { tag ->
                                    if (tag.type == "landing_page") {
                                        str.append("#${tag.title} ")
                                    }
                                }
                                textTagsCollection.text = str
                                textDescriptionCollection.text = description
                                textTotalImage.text = total_photos.toString()
                                textNikName.text = user.name

                                Glide
                                    .with(imageView.context)
                                    .load(cover_photo.urls.small)
                                    .error(R.drawable.smile)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .placeholder(R.drawable.status_load)
                                    .into(imageView)
                            }
                        }
                    }
                }
            }

        binding.recycler.adapter = photoAdapter

        binding.rebootButton.setOnClickListener {
            photoAdapter.refresh()
        }

        collectionsPhotoViewModel.pagedPhotos.onEach {
            photoAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope
            .launchWhenCreated {
                collectionsPhotoViewModel.state.collect { state ->
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
                            binding.recycler.visibility = View.INVISIBLE
                            binding.rebootButton.visibility = View.VISIBLE
                            binding.errorLayout.visibility = View.VISIBLE
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
            collectionsPhotoViewModel.unlike(item.id)
        } else {
            collectionsPhotoViewModel.setLike(item.id)
        }

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}