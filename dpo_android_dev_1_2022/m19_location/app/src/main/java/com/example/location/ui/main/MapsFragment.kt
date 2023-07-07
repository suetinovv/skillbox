package com.example.location.ui.main

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.location.R
import com.example.location.data.AttractionRepository
import com.example.location.databinding.FragmentMapsBinding
import com.example.location.domain.GetAttractionsUseCase
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.LocationSource
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.scopes.FragmentScoped
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@FragmentScoped
class MapsFragment : Fragment() {

    private lateinit var contextFragment: Context
    lateinit var fusedClient: FusedLocationProviderClient

    private var locationListener: LocationSource.OnLocationChangedListener? = null

    private var _binding: FragmentMapsBinding? = null
    private val binding get() = _binding!!
    private var map: GoogleMap? = null

    private var needAnimateCamera = false
    private var needMoveCamera = true

    private var handler = Handler(Looper.getMainLooper())
    private var cameraMoveRunnable = Runnable {
        needMoveCamera = true
    }

    private val viewModel: MapsViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val getAttractionsUseCase = GetAttractionsUseCase(AttractionRepository())
                return MapsViewModel(getAttractionsUseCase) as T
            }
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.isNotEmpty() && map.values.all { it }) {
                startLocation()
            }
        }

    private val locationCallBack = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult) {

            p0.lastLocation?.let { location ->
                locationListener?.onLocationChanged(location)
                viewModel.loadInfo(location.latitude, location.longitude)

                val cameraUpdate = CameraUpdateFactory.newLatLngZoom(
                    LatLng(location.latitude, location.longitude),
                    6f
                )


                if (needMoveCamera) {
                    if (needAnimateCamera) {
                        map?.animateCamera((cameraUpdate))
                    } else {
                        needAnimateCamera = true
                        map?.moveCamera((cameraUpdate))
                    }
                }
            }
        }
    }

    private val callback = OnMapReadyCallback { googleMap ->

        map = googleMap
        checkPermissions()

        with(googleMap.uiSettings) {
            this.isZoomControlsEnabled = true
            isMyLocationButtonEnabled = true
        }
        googleMap.isMyLocationEnabled = true
        googleMap.setLocationSource(object : LocationSource {
            override fun activate(p0: LocationSource.OnLocationChangedListener) {
                locationListener = p0
            }

            override fun deactivate() {
                locationListener = null
            }
        })

        viewModel.attraction.onEach { list ->
            list.forEach {
                googleMap.addMarker(
                    MarkerOptions()
                        .icon(
                            BitmapDescriptorFactory
                                .defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)
                        )
                        .position(LatLng(it.point.lon, it.point.lat))
                        .title(it.name)
                )
            }
        }.launchIn(viewLifecycleOwner.lifecycleScope)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contextFragment = this.requireContext()
        fusedClient = LocationServices.getFusedLocationProviderClient(contextFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.mapOverlay.setOnTouchListener { v, event ->
            handler.removeCallbacks(cameraMoveRunnable)
            needMoveCamera = false
            handler.postDelayed(cameraMoveRunnable, 5000)
            false

        }
    }

    override fun onStart() {
        super.onStart()
        checkPermissions()
    }

    override fun onStop() {
        super.onStop()
        fusedClient.removeLocationUpdates(locationCallBack)
        needAnimateCamera = false
    }

    private fun checkPermissions() {
        if (REQUEST_PERMISSIONS.all { permission ->
                ContextCompat.checkSelfPermission(
                    contextFragment,
                    permission
                ) == PackageManager.PERMISSION_GRANTED
            }) {
            startLocation()
        } else {
            launcher.launch(REQUEST_PERMISSIONS)
        }
    }

    @Suppress("DEPRECATION")
    private fun startLocation() {
        val request = LocationRequest.create()
            .setInterval(1000)
            .setPriority(Priority.PRIORITY_HIGH_ACCURACY)

        fusedClient.requestLocationUpdates(
            request,
            locationCallBack,
            Looper.getMainLooper()
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private val REQUEST_PERMISSIONS: Array<String> = buildList {
            add(Manifest.permission.ACCESS_FINE_LOCATION)
            add(Manifest.permission.ACCESS_COARSE_LOCATION)
        }.toTypedArray()
    }
}