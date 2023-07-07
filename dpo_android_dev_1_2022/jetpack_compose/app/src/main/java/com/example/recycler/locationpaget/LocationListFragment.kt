package com.example.recycler.locationpaget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.recycler.State
import com.example.recycler.databinding.FragmentPersonageListBinding
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class LocationListFragment : Fragment() {

    private var _binding: FragmentPersonageListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LocationListViewModel by viewModels()
    private val locationAdapter = LocationPagingListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPersonageListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = locationAdapter

        binding.rebootButton.setOnClickListener {
            locationAdapter.refresh()
        }

        viewModel.pagedLocations.onEach {
            locationAdapter.submitData(it)
        }.launchIn(viewLifecycleOwner.lifecycleScope)


        viewLifecycleOwner.lifecycleScope
            .launchWhenCreated {
                viewModel.state.collect { state ->
                    when (state) {
                        State.Success -> {
                            binding.rebootButton.visibility = View.INVISIBLE
                            binding.progress.visibility = View.INVISIBLE
                            binding.recycler.visibility = View.VISIBLE
                        }
                        State.Loading -> {
                            binding.progress.visibility = View.VISIBLE
                            binding.rebootButton.visibility = View.INVISIBLE
                            binding.recycler.visibility = View.VISIBLE
                        }
                        is State.Error -> {
                            binding.progress.visibility = View.INVISIBLE
                            binding.rebootButton.visibility = View.VISIBLE
                            binding.recycler.visibility = View.INVISIBLE
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}