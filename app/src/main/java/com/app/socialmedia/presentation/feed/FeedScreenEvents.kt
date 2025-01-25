package com.app.socialmedia.presentation.feed

sealed class FeedScreenEvents{
    data object loadFeedPostsOnPageLoad : FeedScreenEvents()
    data class likeFeedPostEvent(val postId:String):FeedScreenEvents()
    data class shareFeedScreenPost(val postId:String):FeedScreenEvents()
    data class commentFeedPostEvent(val postId: String, val comment: String) : FeedScreenEvents()

}
