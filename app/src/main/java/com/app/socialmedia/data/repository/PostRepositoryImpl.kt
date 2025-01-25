package com.app.socialmedia.data.repository

import com.app.socialmedia.data.dto.CommentDto
import com.app.socialmedia.data.dto.CreateCommentDto
import com.app.socialmedia.data.dto.PostDto
import com.app.socialmedia.data.dto.toCreateCommentDto
import com.app.socialmedia.data.remote.SocialMediaApi
import com.app.socialmedia.data.remote.api_responses.CommonRes
import com.app.socialmedia.domain.model.CreateCommentModel
import com.app.socialmedia.domain.model.CreatePostModel
import com.app.socialmedia.domain.model.toCreatePostDto
import com.app.socialmedia.domain.repository.PostRepository
import retrofit2.Response
import javax.inject.Inject

class PostRepositoryImpl @Inject constructor(
    private val  api: SocialMediaApi
):PostRepository{
    override suspend fun getAllPosts(): Response<List<PostDto>> {
        return api.getAllPosts()
    }

    override suspend fun getUserPosts(userId: Int): Response<List<PostDto>> {
        return api.getUserPosts(userId)
    }

    override suspend fun createPost(createPostModel: CreatePostModel): Response<PostDto> {
        return api.createPost(createPostModel.toCreatePostDto())
    }

    override suspend fun getPostById(postId:Int): Response<PostDto> {
        return api.getPostById(postId)
    }

    override suspend fun likeUnlikePost(postId: Int): Response<PostDto> {
        return api.likeUnlikePost(postId)
    }

    override suspend fun sharePost(postId: Int): Response<CommonRes> {
        return api.sharePost(postId)
    }

    override suspend fun commentOnPost(postId: Int,comment: CreateCommentModel): Response<PostDto> {
        return api.commentOnPost(postId=postId,content=comment.toCreateCommentDto())
    }


}