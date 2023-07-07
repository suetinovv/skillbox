package com.example.practice.authorization

import net.openid.appauth.ResponseTypeValues

object AuthConfig {
    const val AUTH_URI = "https://unsplash.com/oauth/authorize"
    const val TOKEN_URI = "https://unsplash.com/oauth/token"
    const val END_SESSION_URI = "https://unsplash.com/logout"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE = "public read_user write_user read_photos write_photos write_likes write_followers read_collections write_collections"
    const val CLIENT_ID = "5n3sCDqHTkn9zMkRW6jSvBf5Z6GvtqHU8G_w0VRuwQQ"
    const val CLIENT_SECRET = "O7E1-1mbFaWlizBwoZ0-9697nn9o9AQ7uyaHZG3KgrM"
    const val CALLBACK_URL = "ru.nikalaich.oauth://nikalaich.ru/callback"
    const val LOGOUT_CALLBACK_URL = "https://unsplash.com/"
}