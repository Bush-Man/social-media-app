package com.app.socialmedia.presentation.post

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.socialmedia.common.Resource
import com.app.socialmedia.domain.model.CreateCommentModel
import com.app.socialmedia.domain.model.PostModel
import com.app.socialmedia.domain.use_case.post.CreateCommentUsecase
import com.app.socialmedia.domain.use_case.post.GetAllPostsUseCase
import com.app.socialmedia.domain.use_case.post.GetPostByIdUseCase
import com.app.socialmedia.domain.use_case.post.LikePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PostViewModel @Inject constructor(
   private val useCase: GetAllPostsUseCase,
    private val getPostByIdUseCase: GetPostByIdUseCase,
    private val likePostUseCase: LikePostUseCase,
    private val createCommentUsecase: CreateCommentUsecase
):ViewModel(){

    private val _state = MutableStateFlow(PostState())
    val state:StateFlow<PostState> = _state.asStateFlow()



    fun onPostEvent(event: PostEvents){
        when(event){
            PostEvents.getAllPostEvent -> {
                loadPosts()
            }

            is PostEvents.getPostById -> {
                getPostById(event.postId)

            }

            is PostEvents.onLikePost -> {
                likeUnlikePost(event.postId)
            }

            is PostEvents.sharePostEvent -> {

            }

            is PostEvents.commentPostEvent -> {
                commentOnPost(postId = event.postId, comment = event.comment)
            }
        }
    }
     private fun loadPosts(){
        viewModelScope.launch {
            useCase().collect{result->
                when (result) {
                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {


                        _state.update { it.copy(posts = result.data as List<PostModel>) }
                    }

                    is Resource.Error -> {

                    }
                }
        } }
    }

//    Get post by post id
    private fun getPostById(postId:String){
        viewModelScope.launch {
            getPostByIdUseCase(postId).collect{result ->
                when(result){
                    is Resource.Error -> TODO()
                    is Resource.Loading -> TODO()
                    is Resource.Success -> {
                        _state.update { it.copy(singlePost = result.data as PostModel) }
                    }
                }

            }
        }
    }
//    Like or Unlike post handler
    private fun likeUnlikePost(postId: String){
        viewModelScope.launch {
            likePostUseCase(postId)
                .collect{result ->
                when(result){
                    is Resource.Error -> { }
                    is Resource.Loading -> { }
                    is Resource.Success -> {
                        Log.d("like post","${result.data}")
                        if(result.data !=null) {
                            val updatedPost = result.data

                            _state.update {
                                it.copy(posts = it.posts.map { post ->
                                    if (post.postId == postId) {
                                        updatedPost
                                    } else post
                                })
                            }
                        }
                    }
                }

            }
        }
    }


    private fun commentOnPost(postId: String,comment:String){
        val commentModel = CreateCommentModel(
            content = comment
        )
        viewModelScope.launch {
            createCommentUsecase(comment = commentModel,postId = postId).collect { result ->
                when(result){
                    is Resource.Error -> {

                    }
                    is Resource.Loading -> {

                    }
                    is Resource.Success -> {
                        if(result.data != null){

                            _state.update { it.copy(

                                singlePost =  result.data
                            )

                            }
                        }}

            }}
        }

    }
}