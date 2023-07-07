package com.example.skillbox_hw_quiz

import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.navigation.fragment.findNavController
import com.example.skillbox_hw_quiz.databinding.FragmentThirdBinding

class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null
    private var param1: String? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
        _binding = FragmentThirdBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.textView.text = param1

        ObjectAnimator.ofFloat(binding.textView, View.SCALE_X,0.0f,1f).apply {
            duration = 3000
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = 0
            start()
        }

        ObjectAnimator.ofFloat(binding.buttonThird, View.ALPHA,0.4f,1f).apply {
            duration = 1000
            interpolator = AccelerateDecelerateInterpolator()
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
            start()
        }

        binding.buttonThird.setOnClickListener {
            findNavController().navigate(R.id.action_ThirdFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) = ThirdFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
            }

        }

        private const val ARG_PARAM1 = "param1"
    }

}