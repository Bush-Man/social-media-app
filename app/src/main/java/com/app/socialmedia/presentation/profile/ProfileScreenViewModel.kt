package com.app.socialmedia.presentation.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.socialmedia.common.Resource
import com.app.socialmedia.domain.model.CreateCommentModel
import com.app.socialmedia.domain.model.PostModel
import com.app.socialmedia.domain.use_case.post.CreateCommentUsecase
import com.app.socialmedia.domain.use_case.post.GetUserPostsUsecase
import com.app.socialmedia.domain.use_case.post.LikePostUseCase
import com.app.socialmedia.domain.use_case.post.SharePostUsecase
import com.app.socialmedia.presentation.feed.FeedScreenEvents
import com.app.socialmedia.util.AuthTokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileScreenViewModel @Inject constructor(
    private val getUserPostsUsecase: GetUserPostsUsecase,
    private val likePostUseCase: LikePostUseCase,
    private val sharePostUsecase: SharePostUsecase,
    private val createCommentUsecase: CreateCommentUsecase,
    private val authTokenManager: AuthTokenManager
):ViewModel() {

    private val _state = MutableStateFlow(ProfileScreenState())
    val state: StateFlow<ProfileScreenState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
        authTokenManager.getUserInfo().collect{ user ->
            if(user != null){
                loadUserPosts(user.id.toString())

            }else{
                Log.e("Profile View Model scope","user is null")
            }
        }}


    }
    fun onProfileScreenEvent(event: ProfileScreenEvents) {
        when (event) {
            is ProfileScreenEvents.commentProfilePostEvent -> {
                  commentOnFeedPost(postId = event.postId, comment = event.comment)
            }
            is ProfileScreenEvents.getUserPostsEvent -> {
                loadUserPosts(event.userId)
            }

            is ProfileScreenEvents.likeProfilePostEvent -> {
                likeProfilePost(event.postId)
            }
            is ProfileScreenEvents.shareProfilePostEvent -> {
                shareProfilePost(postId = event.postId, userId = event.userId)
            }
        }
    }

    private fun loadUserPosts(userId: String) {
        viewModelScope.launch {
            getUserPostsUsecase(userId).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        _state.update { it.copy(ownPosts = result.data as List<PostModel>) }
                    }
                }

            }
        }
    }

    private fun likeProfilePost(postId: String) {
        viewModelScope.launch {
            likePostUseCase(postId)
                .collectLatest { result ->
                    when (result) {
                        is Resource.Error -> {}
                        is Resource.Loading -> {}
                        is Resource.Success -> {
                            Log.d("like profile post function", "$result")
                            if (result.data != null) {
                                val updatedPost = result.data

                                _state.update {
                                    it.copy(ownPosts = it.ownPosts.map { post ->
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
    private fun shareProfilePost(postId: String,userId: String) {
        viewModelScope.launch {
            sharePostUsecase(postId).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {}
                    is Resource.Loading -> {}
                    is Resource.Success -> {

                       loadUserPosts(userId)
                    }
                }
            }

        }
    }
    // comment on a profile post

    private fun commentOnFeedPost(postId: String, comment: String) {
        val commentModel = CreateCommentModel(
            content = comment
        )
        viewModelScope.launch {
            createCommentUsecase(comment = commentModel, postId = postId).collectLatest { result ->
                when (result) {
                    is Resource.Error -> {

                    }

                    is Resource.Loading -> {

                    }

                    is Resource.Success -> {
                        if (result.data != null) {

                            _state.update {
                                it.copy(

                                    singlePost = result.data
                                )

                            }
                        }
                    }
                }
            }


        }
    }

}