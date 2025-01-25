package com.app.socialmedia.domain.repository

import com.app.socialmedia.data.dto.CommentDto
import com.app.socialmedia.data.dto.PostDto
import com.app.socialmedia.data.remote.api_responses.CommonRes
import com.app.socialmedia.domain.model.CreateCommentModel
import com.app.socialmedia.domain.model.CreatePostModel
import retrofit2.Response

interface PostRepository{
    suspend fun getAllPosts():Response<List<PostDto>>

    suspend fun getUserPosts(userId:Int):Response<List<PostDto>>

    suspend fun createPost(createPostModel: CreatePostModel):Response<PostDto>

    suspend fun getPostById(postId:Int):Response<PostDto>

    suspend fun likeUnlikePost(postId: Int):Response<PostDto>

    suspend fun sharePost(postId:Int):Response<CommonRes>

    suspend fun commentOnPost(postId: Int,comment: CreateCommentModel):Response<PostDto>
}