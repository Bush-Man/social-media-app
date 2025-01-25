package com.app.socialmedia.presentation.post

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.socialmedia.presentation.components.CommentCard
import com.app.socialmedia.presentation.components.Editor
import com.app.socialmedia.presentation.components.PostCard
import com.app.socialmedia.presentation.feed.FeedScreenEvents
import com.app.socialmedia.presentation.feed.FeedScreenViewModel

@Composable
fun PostScreen(
    postId:String,
    modifier: Modifier = Modifier,
    navController: NavController,
    postViewModel: PostViewModel = hiltViewModel(),
    feedScreenViewModel: FeedScreenViewModel = hiltViewModel()

) {
    var isExpanded by remember { mutableStateOf(false) }
    var selectedSortOption by remember { mutableStateOf("Sort by") }
    val uiState = postViewModel.state.collectAsState()
    val post = uiState.value.singlePost
    val postEvents = postViewModel::onPostEvent
//    val feedScreenEvents = feedScreenViewModel::onFeedScreenEvent

    LaunchedEffect(postId) {
        postViewModel.onPostEvent(PostEvents.getPostById(postId))
    }
    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
    ) {
        //post info
        if (post != null) {
            PostCard(
                navController = navController,
                post = post,
                onLikeClick = {
                    postEvents(PostEvents.onLikePost(postId))
                },
                onShareButtonClick = {
                    postEvents(PostEvents.sharePostEvent(postId))

                }


            )
        }

        Editor(
            navController = navController,
            buttonText = "comment",
            showPrivacyMenu = false,
            placeHolder = "comment on this post",
            onPostButtonClicked = {
                postEvents(PostEvents.commentPostEvent(postId = postId, comment = it))
            })
        // post comments section
        Column(modifier = modifier.padding(16.dp)) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = modifier.fillMaxWidth()
            ) {
                Text(text = "${post?.commentsCount} comments", fontWeight = FontWeight.SemiBold)

                Box {
                    TextButton(onClick = { isExpanded = true }) {
                        Text(text = selectedSortOption, color = Color.Black)
                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = "Dropdown",
                            tint = Color.Black
                        )
                    }

                    DropdownMenu(
                        expanded = isExpanded,
                        onDismissRequest = { isExpanded = false },
                        modifier = modifier.background(Color.DarkGray)
                    ) {

                        listOf("latest", "most liked").forEach { option ->

                            DropdownMenuItem(
                                text = { Text(text = option, color = Color.White) },
                                onClick = {
                                    selectedSortOption = option
                                    isExpanded = false

                                })


                        }
                    }
                }


            }

            post?.comments?.forEach { comment ->
                CommentCard(comment = comment)
            }


        }

    }
    
    }




