package com.app.socialmedia.data.dto

import com.app.socialmedia.domain.model.PostModel
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime

data class PostDto(
    @SerializedName("id") val id: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("is_liked") val isLiked: Boolean,
    @SerializedName("description") val description: String,
    @SerializedName("privacy") val privacy: String,
    @SerializedName("likes_count") val likesCount: Int,
    @SerializedName("comments_count") val commentsCount: Int,
    @SerializedName("total_share_count") val shareCount: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("comments") val comments: List<CommentDto>,
    @SerializedName("likers") val likers: List<LikerDto>,
    @SerializedName("user") val postOwner: UserDto
)

data class LikerDto(
    @SerializedName("id") val id: Int,
    @SerializedName("post_id") val postId: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String
)
fun PostDto.toPostModel():PostModel{
    return PostModel(
        postId = id.toString(),
    userId = userId.toString(),
        comments = comments.map { it.toPostCommentModel() },
    commentsCount = commentsCount,
    createdAt = createdAt,
    description =description,
    likers = likers,
    likesCount = likesCount,
    privacy = privacy,
    postOwner = postOwner.toUserModel(),
        isLiked = isLiked,
        shareCount = shareCount

    )
}
