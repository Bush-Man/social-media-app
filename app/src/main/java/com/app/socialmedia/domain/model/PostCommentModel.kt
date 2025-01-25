package com.app.socialmedia.domain.model

data class PostCommentModel(
    val content:String,
    val hashtags:String?,
    val postId: Int,
    val userId: Int,
    val commentId: Int,
    val likesCount:Int,
    val mentionedUsers:String?,
    val parentCommentId: Int?,
    val repliesCount:Int,
    val reportedCount:Int,
    val reportedReasons:String?,
    val visibility:String,
    val commentOwner:UserModel?,
    val createdAt:String,
    val updatedAt:String,
    val isEdited:Boolean

)
