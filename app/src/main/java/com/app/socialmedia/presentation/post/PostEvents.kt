package com.app.socialmedia.presentation.post

import com.app.socialmedia.presentation.feed.FeedScreenEvents

sealed class PostEvents{
    data object getAllPostEvent:PostEvents()
    data class getPostById(val postId:String):PostEvents()
    data class onLikePost(val postId:String):PostEvents()
    data class sharePostEvent(val postId:String):PostEvents()
    data class commentPostEvent(val postId:String,val comment:String): PostEvents()

}