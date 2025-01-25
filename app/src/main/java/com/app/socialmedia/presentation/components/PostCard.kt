package com.app.socialmedia.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.socialmedia.domain.model.PostModel
import com.app.socialmedia.presentation.navigation.Screen
@Composable
fun PostCard(
    modifier: Modifier = Modifier,
    navController: NavController,
    post: PostModel,
    onLikeClick: (String) -> Unit,
    onShareButtonClick:(String)->Unit

) {
    var isExpanded by remember { mutableStateOf(false) }
    var isPostVerticalIconClicked by remember { mutableStateOf(false) }
    var selectedPostVerticalIconOption by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.weight(1f)
            ) {
                Image(
                    imageVector = Icons.Default.Person,
                    contentDescription = "User Profile",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .padding(end = 8.dp)
                        .clip(CircleShape)
                )
                Column {
                    post.postOwner!!.name?.let {
                        Text(
                            text = it,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                    Text(
                        text = "@" + post.postOwner.name,
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                }
            }
            Box {
                IconButton(
                    onClick = { isPostVerticalIconClicked = true },
                    modifier = modifier
                        .clip(CircleShape)
                        .padding(1.dp)
                        .border(width = 1.dp, color = Color.LightGray)
                ) {
                    Icon(
                        imageVector = Icons.Default.MoreVert,
                        contentDescription = "More options icon",
                        tint = Color.Black
                    )
                }
                DropdownMenu(
                    expanded = isPostVerticalIconClicked,
                    onDismissRequest = { isPostVerticalIconClicked = false },
                    modifier = modifier.background(Color.DarkGray)
                ) {
                    listOf("Report", "Block").forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = option, color = Color.White) },
                            onClick = {
                                selectedPostVerticalIconOption = option
                                isPostVerticalIconClicked = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = post.description,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            maxLines = if (isExpanded) Int.MAX_VALUE else 3,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .clickable { isExpanded = !isExpanded }
                .padding(4.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PostCommentButtons(
                iconRes = if (post.isLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                label = post.likesCount.toString(),
                description = "Like button",
                onClick = { onLikeClick(post.postId) }
            )
            PostCommentButtons(
                iconRes = Icons.Default.Edit,
                label = post.commentsCount.toString(),
                description = "Comments button",
                onClick = { navController.navigate(Screen.PostScreen.createRoute(postId = post.postId)) }
            )
            PostCommentButtons(
                iconRes = Icons.Default.Share,
                label = post.shareCount.toString(),
                description = "Share button",
                onClick = { onShareButtonClick(post.postId)}
            )
        }
    }
}
