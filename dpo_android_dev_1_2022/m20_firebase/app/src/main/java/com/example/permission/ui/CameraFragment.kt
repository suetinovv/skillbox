package com.example.permission.ui

import android.Manifest
import android.content.ContentValues
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.permission.databinding.FragmentCameraBinding
import com.example.permission.model.CameraModel
import com.example.permission.model.Photo
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.Executor

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CameraFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
private const val FILE_DATE = "yyyy-MM-dd-HH-mm-ss"

class CameraFragment : Fragment() {
    // TODO: Rename and change types of parameters
    lateinit var contextFragment: Context

    private val launcher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { map ->
            if (map.values.all { it }) {
                startCamera()
            } else {
                Toast.makeText(contextFragment, "permission is not Granted", Toast.LENGTH_SHORT)
                    .show()
            }
        }

    private val viewModel: CameraModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                val photoDao = (activity as MainActivity).photoDao
                return CameraModel(photoDao) as T
            }
        }
    }

    private lateinit var executor: Executor
    private var imageCapture: ImageCapture? = null
    private val data = SimpleDateFormat(FILE_DATE, Locale.US).format(System.currentTimeMillis())

    private var _binding: FragmentCameraBinding? = null
    private val binding get() = _binding!!

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        contextFragment = this.requireContext()
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        executor = ContextCompat.getMainExecutor(this.requireActivity())
        checkPermissions()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCameraBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonShot.setOnClickListener {
            takePhoto()
        }
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return

        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, data)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
        }

        val outputOptions = ImageCapture.OutputFileOptions
            .Builder(
                contextFragment.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ).build()

        imageCapture.takePicture(
            outputOptions,
            executor,
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    viewModel.savePhotos(Photo(outputFileResults.savedUri.toString(), data))
                    Toast.makeText(
                        contextFragment,
                        "Photo saved: ${outputFileResults.savedUri}",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onError(exception: ImageCaptureException) {
                    Toast.makeText(
                        contextFragment,
                        "Photo failed: ${exception.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    exception.printStackTrace()
                }
            }
        )
    }

    private fun startCamera() {
        val cameraProviderFeature = ProcessCameraProvider.getInstance(contextFragment)
        cameraProviderFeature.addListener({
            val cameraProvider = cameraProviderFeature.get()
            val preview = Preview.Builder().build()
            preview.setSurfaceProvider(binding.viewFinder.surfaceProvider)
            imageCapture = ImageCapture.Builder().build()
            cameraProvider.unbindAll()
            cameraProvider.bindToLifecycle(
                this,
                CameraSelector.DEFAULT_BACK_CAMERA,
                preview,
                imageCapture
            )
        }, executor)
    }

    private fun checkPermissions() {
        val isAllGrandes = REQUEST_PERMISSIONS.all { permission ->
            ContextCompat.checkSelfPermission(
                contextFragment,
                permission
            ) == PackageManager.PERMISSION_GRANTED
        }
        if (isAllGrandes) {
            startCamera()
            Toast.makeText(contextFragment, "permission is Granted", Toast.LENGTH_SHORT)
                .show()
        } else {
            launcher.launch(REQUEST_PERMISSIONS)
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private val REQUEST_PERMISSIONS: Array<String> = buildList {
            add(Manifest.permission.CAMERA)
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CameraFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CameraFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}