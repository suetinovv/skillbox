package com.example.humblr.data.remote.models

import javax.inject.Singleton

@Singleton
object TokenStorage {
    var accessToken: String? = null
    var refreshToken: String? = null
    var idToken: String? = null
}