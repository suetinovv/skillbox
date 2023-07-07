package com.example.practice.ui.collections

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.practice.R
import com.example.practice.State
import com.example.practice.collection_paging.CollectionListAdapter
import com.example.practice.data.collections.CollectionsItem
import com.example.practice.databinding.FragmentCollectionsBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val ARG_PARAM1 = "param1"

class CollectionsFragment : Fragment() {

    private var _binding: FragmentCollectionsBinding? = null
    private val binding get() = _binding!!

    private val collectionsViewModel: CollectionsViewModel by viewModels()
    private val collectionAdapter = CollectionListAdapter{collection -> onItemClick(collection)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCollectionsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = collectionAdapter

        collectionsViewModel.pagedCollection.onEach {
            collectionAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        binding.rebootButton.setOnClickListener {
            collectionAdapter.refresh()
        }

        viewLifecycleOwner.lifecycleScope
            .launchWhenCreated {
                collectionsViewModel.state.collect { state ->
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
                            Log.d("test", state.throwable.message!!)
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

    private fun onItemClick(item: CollectionsItem) {
        val bundle = Bundle().apply {
            fragmentManager?.isDestroyed
            putString(
                ARG_PARAM1, item.id
            )
        }
        findNavController().navigate(R.id.navigation_collections_photo, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}