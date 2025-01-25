package com.app.socialmedia.presentation.profile

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.socialmedia.presentation.components.PostCard

@Composable
fun ProfilePosts(
    navController: NavController,
    profileScreenViewModel: ProfileScreenViewModel = hiltViewModel()

){
    val profilePostEvents= profileScreenViewModel::onProfileScreenEvent
    val profileScreenState = profileScreenViewModel.state.collectAsState()
    val ownPosts = profileScreenState.value.ownPosts


    Column {
        if(ownPosts != null){
            ownPosts.forEach { post ->
                PostCard(
                    navController = navController,
                    post = post,
                    onLikeClick = {profilePostEvents(ProfileScreenEvents.likeProfilePostEvent(postId = post.postId))},
                    onShareButtonClick = { profilePostEvents(ProfileScreenEvents.shareProfilePostEvent(postId = post.postId, userId = post.userId))}
                )
            }
        }

    }
}