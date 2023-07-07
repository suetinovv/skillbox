package com.example.weather.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.weather.App
import com.example.weather.R
import com.example.weather.data.History
import com.example.weather.data.Weather
import com.example.weather.databinding.FragmentHistoryBinding
import com.example.weather.ui.cities.CitiesFragment
import com.example.weather.ui.cities.CitiesViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

private const val ARG_PARAM1 = "param1"

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private var param1: String? = null

    private val historyViewModel: HistoryViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return HistoryViewModel(App.component.getHistoryRepository()) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        historyViewModel.loadListCities(param1!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        historyViewModel.listHistory.value.forEach {
            val item = HistoryItem(requireContext())
            item.setInfo(it)
            binding.linearLayout.addView(item)
        }

        historyViewModel.listHistory.onEach {
            it.forEach { history ->
                val item = HistoryItem(requireContext())
                item.setInfo(history)
                binding.linearLayout.addView(item)
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}