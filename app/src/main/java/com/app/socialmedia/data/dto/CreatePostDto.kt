package com.app.socialmedia.data.dto

import com.google.gson.annotations.SerializedName

data class CreatePostDto(
    @SerializedName("description")
    val description:String
)

