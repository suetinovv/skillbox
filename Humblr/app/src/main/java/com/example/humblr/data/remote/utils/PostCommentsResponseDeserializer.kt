package com.example.humblr.data.remote.utils

import com.example.humblr.data.remote.models.CommentsResponse
import com.example.humblr.data.remote.models.PostCommentsResponse
import com.example.humblr.data.remote.models.PostResponse
import com.google.gson.*

import java.lang.reflect.Type

internal class PostCommentsResponseDeserializer : JsonDeserializer<PostCommentsResponse?> {
    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): PostCommentsResponse {
        val gson = Gson()
        val jsonArray = json.asJsonArray
        val postResponse = gson.fromJson(jsonArray[0], PostResponse::class.java)
        val commentsResponse = gson.fromJson(jsonArray[1], CommentsResponse::class.java)
        return PostCommentsResponse(postResponse, commentsResponse)
    }
}