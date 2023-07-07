package com.example.weather.ui.cities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.weather.App
import com.example.weather.R
import com.example.weather.databinding.FragmentCitiesBinding
import com.example.weather.ui.search.SearchViewModel

private const val ARG_PARAM1 = "param1"

class CitiesFragment : Fragment() {

    private var _binding: FragmentCitiesBinding? = null
    private val binding get() = _binding!!

    private val citiesViewModel: CitiesViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return CitiesViewModel(App.component.getHistoryRepository()) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        citiesViewModel.loadListCities()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCitiesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = CityAdapter(citiesViewModel.list.value) { city ->
            onItemClick(city)
        }
        binding.citiesRecyclerView.adapter = adapter
    }

    private fun onItemClick(city: String) {
        val bundle = Bundle().apply {
            fragmentManager?.isDestroyed
            putString(
                "param1", city
            )
        }
        findNavController().navigate(R.id.action_navigation_cities_to_navigation_history, bundle)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CitiesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}