package com.example.retrofit

import android.content.Context
import android.content.ContextParams
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.retrofit.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.squareup.moshi.Moshi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUserDate()

        binding.button.setOnClickListener {
            loadUserDate()
        }

    }

    fun loadUserDate() {
        RetrofitInstance.uploadingUserData.getUserDate().enqueue(object :
            Callback<User> {
            override fun onResponse(
                call: Call<User>,
                response: Response<User>
            ) {
                if (response.isSuccessful) {
                    val user = response.body()?.results?.first() ?: return
                    binding.textFirst.text = user.name.first
                    binding.textLast.text = user.name.last
                    binding.textMail.text = user.email
                    Glide.with(binding.imageView.context)
                        .load(user.picture.large)
                        .into(binding.imageView)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                loadUserDate()
            }
        })
    }

}