package com.app.socialmedia.presentation.feed

import com.app.socialmedia.domain.model.PostModel

data class FeedScreenState(
    val feedPosts:List<PostModel> = emptyList(),
    val update:Boolean = false,
    val singlePost:PostModel? =null,
    val error:String = "",
    val isLoading:Boolean =false

)