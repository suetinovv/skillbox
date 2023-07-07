package com.example.weather.ui.search

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.weather.App
import com.example.weather.R
import com.example.weather.State
import com.example.weather.data.History
import com.example.weather.data.Weather
import com.example.weather.databinding.FragmentSearchBinding
import com.example.weather.ui.cities.CitiesViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.net.URLEncoder

private const val PREFERENCE_NAME = "prefs_name"
private const val KEY_NAME = "KEY"

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!

    private lateinit var prefs: SharedPreferences

    private val searchViewModel: SearchViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return SearchViewModel(
                    App.component.getWeatherRepository(),
                    App.component.getHistoryRepository()
                ) as T
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        prefs = requireContext().getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener {
            searchViewModel.loadWeather(binding.editText.text.toString())
        }

        searchViewModel.text.onEach {
            if (it.isNotEmpty()) {
                it.first().let { weather ->
                    if (weather is Weather) {
                        binding.textCity.text = weather.location.name
                        binding.textTempC.text = weather.current.temp_c.toString()
                        binding.textDate.text = weather.location.localtime
                        binding.text.text = weather.current.condition.text
                    } else if (weather is History) {
                        binding.textCity.text = weather.name_city
                        binding.textTempC.text = weather.temp_c
                        binding.textDate.text = weather.date
                        binding.text.text = weather.text
                    }
                }
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)

        viewLifecycleOwner.lifecycleScope
            .launchWhenCreated {
                searchViewModel.state.collect { state ->
                    when (state) {
                        State.SuccessWeather -> {
                            binding.progress.visibility = View.INVISIBLE
                            binding.item.visibility = View.VISIBLE
                        }
                        State.SuccessHistory -> {
                            binding.progress.visibility = View.INVISIBLE
                            binding.item.visibility = View.VISIBLE
                            Toast.makeText(
                                requireContext(),
                                "Не удалось загрузить погоду, данные были загружены из бд",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                        State.Loading -> {
                            binding.item.visibility = View.VISIBLE
                            binding.progress.visibility = View.VISIBLE
                        }
                        State.Empty -> {
                            binding.progress.visibility = View.INVISIBLE
                            binding.item.visibility = View.INVISIBLE
                        }
                        is State.Error -> {
                            binding.progress.visibility = View.INVISIBLE
                            binding.item.visibility = View.INVISIBLE
                            Toast.makeText(
                                requireContext(),
                                "Не удалось загрузить погоду с сервера и из бд",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        }
                    }
                }
            }
        binding.editText.hint = prefs.getString(KEY_NAME, "Введите город")
    }

    override fun onStop() {
        super.onStop()
        val temp = binding.editText.text.toString()
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(KEY_NAME, temp)
        editor.apply()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}