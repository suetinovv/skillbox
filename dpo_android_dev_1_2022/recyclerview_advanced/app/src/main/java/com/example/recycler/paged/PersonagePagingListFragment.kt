package com.example.recycler.paged

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

/**
 * A simple [Fragment] subclass.
 * Use the [PersonagePagingListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PersonagePagingListFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentPersonageListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: PersonagePagingListViewModel by viewModels()
    private val personageAdapter = PersonagePagingListAdapter()


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
        _binding = FragmentPersonageListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recycler.adapter = personageAdapter

        binding.rebootButton.setOnClickListener {
            personageAdapter.refresh()
        }

        viewModel.pagedPersonages.onEach {
            personageAdapter.submitData(it)
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

//    private fun onItemClick(item: Personage) {
//        val bundle = Bundle().apply {
//            fragmentManager?.isDestroyed
//            putString(
//                "param1", item.image
//            )
//        }
//        findNavController().navigate(R.id.PersonageFullFragment, bundle)
//
//    }

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
            PersonagePagingListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}