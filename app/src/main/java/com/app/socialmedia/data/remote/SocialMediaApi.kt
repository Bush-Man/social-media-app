package com.app.socialmedia.data.remote

import com.app.socialmedia.data.dto.CommentDto
import com.app.socialmedia.data.dto.CreateCommentDto
import com.app.socialmedia.data.dto.CreatePostDto
import com.app.socialmedia.data.dto.LoginDto
import com.app.socialmedia.data.dto.LoginResponse
import com.app.socialmedia.data.dto.PostDto
import com.app.socialmedia.data.dto.UserRegisterDto
import com.app.socialmedia.data.remote.api_responses.CommonRes
import com.app.socialmedia.data.remote.api_responses.RegistrationApiResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface SocialMediaApi {

    @POST("auth/register")
    suspend fun register(@Body registerDto: UserRegisterDto): Response<RegistrationApiResponse>

    @POST("auth/login")
    suspend fun login(@Body loginDto: LoginDto): Response<LoginResponse>

    @POST("logout")
    suspend fun logout(): Response<CommonRes>



    @GET("posts")
    suspend fun getAllPosts(): Response<List<PostDto>>

    @GET("posts/user-posts/{user_id}")
    suspend fun getUserPosts(@Path("user_id") postId: Int):Response<List<PostDto>>


    @POST("posts/create")
    suspend fun createPost(@Body createPostDto: CreatePostDto): Response<PostDto>

@GET("posts/{post_id}")
suspend fun getPostById( @Path("post_id") postId:Int):Response<PostDto>

@PUT("posts/{post_id}/like")
suspend fun likeUnlikePost(@Path("post_id")postId: Int):Response<PostDto>

@POST("posts/{post_id}/share")
suspend fun sharePost(@Path("post_id") postId:Int):Response<CommonRes>

@POST("posts/{post_id}/comment")
suspend fun commentOnPost(@Body content:CreateCommentDto, @Path("post_id") postId:Int):Response<PostDto>
}