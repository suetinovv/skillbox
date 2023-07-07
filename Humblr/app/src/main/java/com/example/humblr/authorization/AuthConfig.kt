package com.example.humblr.authorization

import net.openid.appauth.ResponseTypeValues

object AuthConfig {
    const val AUTH_URI = "https://www.reddit.com/api/v1/authorize"
    const val TOKEN_URI = "https://www.reddit.com/api/v1/access_token"
    const val END_SESSION_URI = "https://www.reddit.com/logout"
    const val RESPONSE_TYPE = ResponseTypeValues.CODE
    const val SCOPE =
        "identity edit flair history modconfig modflair modlog modposts modwiki mysubreddits privatemessages read report save submit subscribe vote wikiedit wikiread"
    const val STATE = "test"
    const val DURATION = "permanent"
    const val CLIENT_ID = "DEt0AMJ-aX-w5Vj9kJELDg"
    const val CLIENT_SECRET = ""
    const val CALLBACK_URL = "ru.nikalaich.oauth://humblr.ru/callback"
    const val LOGOUT_CALLBACK_URL = "https://www.reddit.com/"
}