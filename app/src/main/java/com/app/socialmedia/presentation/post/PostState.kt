package com.app.socialmedia.presentation.post

import com.app.socialmedia.domain.model.PostModel

data class PostState(
    val posts:List<PostModel> = emptyList(),
    val singlePost:PostModel? = null ,
    val isLoading:Boolean  = false,
    val errors:List<String> = emptyList(),
    val likePostSuccess:String=""

)
