package com.app.socialmedia.data.dto

import com.app.socialmedia.domain.model.PostCommentModel
import com.google.gson.annotations.SerializedName
import java.time.LocalDateTime


data class CommentDto(
    @SerializedName("id") val id: Int,
    @SerializedName("content") val content: String,
    @SerializedName("post_id") val postId: Int,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("parent_comment_id") val parentCommentId: Int?,
    @SerializedName("likes_count") val likesCount: Int,
    @SerializedName("replies_count") val repliesCount: Int,
    @SerializedName("reported_count") val reportedCount: Int,
    @SerializedName("reported_reasons") val reportedReasons: String?,
    @SerializedName("mentioned_users") val mentionedUsers: String?,
    @SerializedName("hashtags") val hashtags: String?,
    @SerializedName("visibility") val visibility: String,
    @SerializedName("is_edited") val isEdited: Boolean,
    @SerializedName("thread_id") val threadId: Int?,
    @SerializedName("created_at") val createdAt: String,
    @SerializedName("updated_at") val updatedAt: String,
    @SerializedName("user") val commentOwner:UserDto
)


fun CommentDto.toPostCommentModel(): PostCommentModel {
    return PostCommentModel(
            content = content,
            hashtags = hashtags,
            postId = postId ,
            userId = userId,
            commentId = id,
            likesCount = likesCount,
            mentionedUsers = mentionedUsers,
            parentCommentId = parentCommentId,
            repliesCount = repliesCount,
            reportedCount = reportedCount,
            reportedReasons = reportedReasons,
            visibility = visibility,
            commentOwner = commentOwner.toUserModel(),
        createdAt = createdAt,
        updatedAt = updatedAt,
        isEdited = isEdited


        )
    }
