package com.example.practice.ui.user

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.practice.App
import com.example.practice.AuthActivity
import com.example.practice.R
import com.example.practice.databinding.FragmentUserBinding
import com.google.android.material.tabs.TabLayoutMediator

class UserFragment : Fragment() {

    private var _binding: FragmentUserBinding? = null
    private val binding get() = _binding!!

    private val userViewModel: UserViewModel by viewModels {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return UserViewModel(
                    App.component.getUnsplashRepository(),
                    App.component.getHistoryRepository()
                ) as T
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userViewModel.loadMeInfo()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.viewPager.adapter = UserViewPagerAdapter(childFragmentManager, lifecycle)

        viewLifecycleOwner.lifecycleScope
            .launchWhenCreated {
                userViewModel.data.observe(viewLifecycleOwner) { userInfo ->
                    if (userInfo != null) {
                        with(binding) {
                            with(userInfo) {
                                textName.text = name
                                textNikName.text = "@$username"
                                if (bio != null){
                                    textStatus.visibility = View.VISIBLE
                                    textStatus.text = bio.toString()
                                }
                                if (location != null){
                                    imageLocation.visibility = View.VISIBLE
                                    textLocation.visibility = View.VISIBLE
                                    textLocation.text = location.toString()
                                }
                                textMail.text = email
                                textDownload.text = downloads.toString()

                                Glide
                                    .with(imageAvatar.context)
                                    .load(profile_image.small)
                                    .error(R.drawable.smile)
                                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                                    .placeholder(R.drawable.status_load)
                                    .apply(RequestOptions.circleCropTransform())
                                    .into(imageAvatar)

                                TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                                    when(position){
                                        0 -> tab.text = "$total_photos  ${getString(R.string.photo)}"
                                        1 -> tab.text = "$total_likes  ${getString(R.string.liked)}"
                                        2 -> tab.text = "$total_collections  ${getString(R.string.title_collections)}"
                                    }

                                }.attach()
                            }
                        }
                    }

                }
            }

        val toolbar = activity?.findViewById<ImageButton>(R.id.toolbar_button)
        toolbar?.setOnClickListener {
            val alertDialog = AlertDialog.Builder(context)
                .setTitle("Выход")
                .setMessage("Вы уверены, что хотите выйти? Все локальные данные будут удалены.")
                .setPositiveButton("Выйти") { dialog, which ->
                    val prefs = context?.getSharedPreferences("my_prefs", AppCompatActivity.MODE_PRIVATE)
                    prefs?.edit()?.putString("auth_code", "")?.apply()
                    prefs?.edit()?.putString("access_token", "")?.apply()
                    userViewModel.logOut()
                    val intent = Intent(context, AuthActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    context?.startActivity(intent)
                }
                .setNegativeButton("Отмена", null)
                .create()

            alertDialog.show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}