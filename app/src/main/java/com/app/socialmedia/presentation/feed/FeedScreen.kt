package com.app.socialmedia.presentation.feed

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.socialmedia.presentation.components.PostCard
import com.app.socialmedia.presentation.components.SnackbarComponent


@Composable
fun FeedScreen(
    viewModel: FeedScreenViewModel = hiltViewModel(),
    navController: NavController
) {
    val uiState by viewModel.state.collectAsState()
    val feedPosts = uiState.feedPosts
    val uiEvents = viewModel::onFeedScreenEvent

    LaunchedEffect(Unit) {
        uiEvents(FeedScreenEvents.loadFeedPostsOnPageLoad)
    }
    if(uiState.error.isNotEmpty()){
        Text(text = uiState.error)
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (feedPosts.isEmpty()) {
                    Text(
                        text = "No posts available",
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyMedium
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                    ) {
                        items(feedPosts, key = { it.postId }) { post ->
                            PostCard(
                                navController = navController,
                                post = post,
                                onLikeClick = {
                                    Log.d("Post Liked feeds screen", it)
                                    viewModel.onFeedScreenEvent(
                                        FeedScreenEvents.likeFeedPostEvent(it)
                                    )
                                },
                                onShareButtonClick = {
                                    Log.d("Post shared feed screen", it)
                                    viewModel.onFeedScreenEvent(FeedScreenEvents.shareFeedScreenPost(it))

                                }
                            )
                        }
                    }
                }
            }
        }
    )
}
