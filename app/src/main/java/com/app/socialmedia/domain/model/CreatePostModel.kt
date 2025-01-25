package com.app.socialmedia.domain.model

import com.app.socialmedia.data.dto.CreatePostDto

data class CreatePostModel(
   val description:String
)

fun CreatePostModel.toCreatePostDto():CreatePostDto{
   return CreatePostDto(
      description = description
   )
}