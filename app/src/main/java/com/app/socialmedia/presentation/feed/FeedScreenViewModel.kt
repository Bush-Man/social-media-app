package com.app.socialmedia.presentation.feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.socialmedia.common.Resource
import com.app.socialmedia.domain.model.CreateCommentModel
import com.app.socialmedia.domain.model.PostModel
import com.app.socialmedia.domain.use_case.post.CreateCommentUsecase
import com.app.socialmedia.domain.use_case.post.GetAllPostsUseCase
import com.app.socialmedia.domain.use_case.post.LikePostUseCase
import com.app.socialmedia.domain.use_case.post.SharePostUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedScreenViewModel @Inject constructor(
    private val getAllPostsUseCase: GetAllPostsUseCase,
    private val likePostUseCase: LikePostUseCase,
    private val sharePostUsecase: SharePostUsecase,
    private val createCommentUsecase: CreateCommentUsecase
):ViewModel(){

    private val _state = MutableStateFlow(FeedScreenState())
    val state: StateFlow<FeedScreenState> = _state.asStateFlow()

// init{
//     loadFeedPosts()
// }
    fun onFeedScreenEvent(event:FeedScreenEvents){
        when(event){
            FeedScreenEvents.loadFeedPostsOnPageLoad -> {
                loadFeedPosts()
            }

            is FeedScreenEvents.likeFeedPostEvent -> {
                likeFeedPost(event.postId)
            }

            is FeedScreenEvents.shareFeedScreenPost ->
                shareFeedScreenPost(event.postId)

            is FeedScreenEvents.commentFeedPostEvent -> {
                commentOnFeedPost(postId = event.postId,comment = event.comment)
            }
        }
    }

    private fun loadFeedPosts(){
        viewModelScope.launch {
            getAllPostsUseCase().collectLatest{result->
                when(result){
                    is Resource.Error -> {

                        _state.update { it.copy(error = result.errorData.toString()) }
                    }
                    is Resource.Loading -> {
                          _state.update { it.copy(isLoading = result.isLoading) }
                    }
                    is Resource.Success -> {
                        _state.update { it.copy(feedPosts = result.data as List<PostModel>)  }
                    }
                }

            }
        }
    }
    private fun likeFeedPost(postId:String) {
        viewModelScope.launch {
            likePostUseCase(postId)
                .collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            Log.d("like feed post function", "$result")
                            if (result.data != null) {
                                val updatedPost = result.data

                                _state.update {
                                    it.copy(feedPosts = it.feedPosts.map { post ->
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

    // handle sharing feed screen post
    private fun shareFeedScreenPost(postId: String){
        viewModelScope.launch{
        sharePostUsecase(postId).collectLatest { result ->
            when(result){
                is Resource.Error -> {}
                is Resource.Loading -> {}
                is Resource.Success -> {

                  loadFeedPosts()
                }
                }
            }

        }
    }
    // comment on a feed post

    private fun commentOnFeedPost(postId: String,comment:String){
        val commentModel = CreateCommentModel(
            content = comment
        )
        viewModelScope.launch {
            createCommentUsecase(comment = commentModel,postId = postId).collectLatest { result ->
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
                }
            }

        }
    }
}

