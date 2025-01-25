package com.app.socialmedia.presentation.profile

import com.app.socialmedia.domain.model.PostModel

data class ProfileScreenState(
    val ownPosts:List<PostModel> = emptyList(),
    val sharedPosts:List<PostModel> = emptyList(),
    val singlePost:PostModel? = null

    )