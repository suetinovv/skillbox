package com.example.dependency.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.dependency.databinding.FragmentMainBinding
import com.example.dependency.App
import org.koin.android.ext.android.get

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainViewModel(App.component.getBicycleFactory()) as T
            }
        }

    }

    private val viewModelKoin: MainKoinViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return MainKoinViewModel(get()) as T
            }
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonDagger.setOnClickListener {
            Toast.makeText(
                requireContext(),
                viewModel.createDagger().let {
                    "Лого: ${it.logo} \n" +
                            "Код цвета: ${it.frame.color}\n" +
                            "Номер рамы: ${it.frame.serialNumber}\n" +
                            "Колесо №1: " +
                            "${it.wheels[0].serialNumber} \n" +
                            "Колесо №2: " +
                            "${it.wheels[1].serialNumber} "
                },
                Toast.LENGTH_SHORT
            )
                .show()
        }
        binding.buttonKoin.setOnClickListener {
            Toast.makeText(requireContext(), viewModelKoin.createKoin().let {
                "Лого: ${it.logo} \n" +
                        "Код цвета: ${it.frame.color}\n" +
                        "Номер рамы: ${it.frame.serialNumber}\n" +
                        "Колесо №1: " +
                        "${it.wheels[0].serialNumber} \n" +
                        "Колесо №2: " +
                        "${it.wheels[1].serialNumber} "
            }, Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}