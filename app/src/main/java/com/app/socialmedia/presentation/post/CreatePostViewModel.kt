package com.app.socialmedia.presentation.post

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.socialmedia.domain.model.CreatePostModel
import com.app.socialmedia.domain.use_case.post.CreatePostUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePostViewModel @Inject constructor(
   private val createPostUseCase: CreatePostUseCase
):ViewModel(){
    private val _state = mutableStateOf(CreatePostState())
    val state: State<CreatePostState> = _state

    fun onCreatePostEvent(event: CreatePostEvents){
        when(event){
            is CreatePostEvents.onPostDescriptionChange -> {
                _state.value = state.value.copy(description = event.description)
            }

            CreatePostEvents.onCreatePostButtonClick -> {
                createPost()
            }

        }

    }
    private fun createPost(){
        val createPostModel = CreatePostModel(
            description = state.value.description!!
        )
        viewModelScope.launch {
            createPostUseCase(createPostModel)
        }
    }



}