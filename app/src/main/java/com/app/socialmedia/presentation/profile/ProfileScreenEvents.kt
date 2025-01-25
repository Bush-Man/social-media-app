package com.app.socialmedia.presentation.profile

import com.app.socialmedia.presentation.feed.FeedScreenEvents


sealed class ProfileScreenEvents {
    data class getUserPostsEvent(val userId:String): ProfileScreenEvents()
    data class likeProfilePostEvent(val postId:String): ProfileScreenEvents()
    data class shareProfilePostEvent(val postId:String,val userId: String): ProfileScreenEvents()
    data class commentProfilePostEvent(val postId: String, val comment: String) : ProfileScreenEvents()

}