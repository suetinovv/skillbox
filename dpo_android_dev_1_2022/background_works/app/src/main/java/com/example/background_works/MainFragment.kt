package com.example.background_works

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.background_works.databinding.FragmentMainBinding
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import java.time.LocalDate
import java.time.LocalTime

class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private lateinit var lastLocationProvider: LastLocationProvider

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { map ->
        if (map.values.isNotEmpty() && map.values.any { it }) {
            lastLocationProvider.getLastLocation(lastLocationCallback)
        }
    }

    private val lastLocationCallback = { it: Location? ->
        if (it == null) {
            Toast.makeText(
                requireContext(),
                getString(R.string.turn_on_gps_toast),
                Toast.LENGTH_LONG
            ).show()
        } else {
            startTimeCalculation(it)
            collectTimeCalculationResult()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        lastLocationProvider = LastLocationProvider(requireContext())
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupDateField()
        setupTimeField()
        setupAlarmButton()
        setAlarmButtonAvailability()

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun startTimeCalculation(location: Location) {
        viewModel.startCalculation(location)
    }

    private fun collectTimeCalculationResult() {
        viewModel.observeWorkerResult()
    }

    private fun setupAlarmButton() {
        binding.setAlarmButton.setOnClickListener {
            if (lastLocationProvider.hasLocationPermission()) {
                lastLocationProvider.getLastLocation(lastLocationCallback)
            } else {
                launcher.launch(LastLocationProvider.REQUIRED_PERMISSIONS)
            }
        }
    }

    private fun setAlarmButtonAvailability() {
        binding.setAlarmButton.isEnabled =
            (!binding.date.text.isNullOrBlank() && !binding.time.text.isNullOrBlank())
    }

    private fun setupDateField() {
        binding.date.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText(getString(R.string.date_picker_title))
                .setCalendarConstraints(
                    CalendarConstraints.Builder()
                        .setValidator(DateValidatorPointForward.from(System.currentTimeMillis()))
                        .build()
                )
                .build().apply {
                    addOnPositiveButtonClickListener {
                        viewModel.saveDate(it)
                        binding.date.setText(LocalDate.ofEpochDay(it / 86_400_000).toString())
                        setAlarmButtonAvailability()
                    }
                }
            datePicker.show(childFragmentManager, "DatePicker")
        }
    }

    private fun setupTimeField() {
        binding.time.setOnClickListener {
            val timePicker = MaterialTimePicker.Builder()
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .setTitleText(getString(R.string.time_picker_title))
                .build().apply {
                    addOnPositiveButtonClickListener {
                        viewModel.saveTime(this.hour, this.minute)
                        binding.time.setText(LocalTime.of(this.hour, this.minute).toString())
                        setAlarmButtonAvailability()
                    }
                }
            timePicker.show(childFragmentManager, "TimePicker")
        }
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}