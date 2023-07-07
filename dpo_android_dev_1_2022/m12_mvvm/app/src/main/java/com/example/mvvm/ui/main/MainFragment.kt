package com.example.mvvm.ui.main


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.mvvm.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.editText.addTextChangedListener {
            viewModel.setStatusButton(it?.length!!)
        }

        binding.buttonSearch.setOnClickListener {
            viewModel.getTextSearch(binding.editText.text.toString())
        }

        viewLifecycleOwner.lifecycleScope
            .launchWhenCreated {
                viewModel.state.collect { state ->
                    when (state) {
                        State.ActivButton -> {
                            binding.buttonSearch.isEnabled = true
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                        State.Loading -> {
                            binding.buttonSearch.isEnabled = false
                            binding.progressBar.visibility = View.VISIBLE
                        }
                        State.NoActivButton -> {
                            binding.buttonSearch.isEnabled = false
                            binding.progressBar.visibility = View.INVISIBLE
                        }
                        is State.Result -> {
                            binding.buttonSearch.isEnabled = true
                            binding.progressBar.visibility = View.INVISIBLE
                            binding.message.text = state.text
                        }
                    }
                }
            }
    }

}