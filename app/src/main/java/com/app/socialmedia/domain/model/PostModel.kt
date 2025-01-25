package com.app.socialmedia.domain.model

import com.app.socialmedia.data.dto.LikerDto
import com.app.socialmedia.data.dto.UserDto
import java.time.LocalDateTime

data class PostModel(
    val postId:String,
    val userId:String,
    val comments: List<PostCommentModel>,
    val commentsCount:Int,
    val createdAt:String,
    val description:String,
    val likers:List<LikerDto>,
    val likesCount:Int,
    val privacy:String,
    val postOwner:UserModel,
    val isLiked:Boolean,
    val shareCount:Int



)


