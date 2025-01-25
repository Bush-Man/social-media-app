package com.app.socialmedia.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.app.socialmedia.domain.model.PostCommentModel
@Composable
fun CommentCard(
    comment: PostCommentModel,
    modifier: Modifier = Modifier
) {
    var isExpanded by remember { mutableStateOf(false) }
    var isReplyLiked by remember { mutableStateOf(false) }

    Column(
        horizontalAlignment = Alignment.Start // Align elements to the start
    ) {

        Row(
            modifier = modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                imageVector = Icons.Default.Person,
                contentDescription = "user image",
                contentScale = ContentScale.Crop,
                modifier = modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .padding(end = 8.dp)
            )
            Column {
                comment.commentOwner?.name?.let {
                    Text(
                        text = it,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp
                    )
                }
                Text(
                    text = "@${comment.commentOwner?.name} " + "." + comment.createdAt,
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = modifier.height(5.dp))

        Text(
            text = comment.content,
            textAlign = TextAlign.Start, // Align text to the start
            maxLines = if (isExpanded) Int.MAX_VALUE else 3,
            overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis,
            fontSize = 14.sp,
            lineHeight = 20.sp,
            modifier = modifier.clickable { isExpanded = !isExpanded }
        )

        Spacer(modifier = modifier.height(5.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            PostCommentButtons(
                iconRes = if (isReplyLiked) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                label = comment.likesCount.toString(),
                description = "like replies button",
                onClick = { isReplyLiked = !isReplyLiked }
            )
            PostCommentButtons(
                iconRes = Icons.Default.Edit,
                label = "reply",
                description = "reply to this comment",
                onClick = {}
            )
            PostCommentButtons(
                iconRes = Icons.Default.Share,
                label = "share",
                description = "share this reply",
                onClick = {}
            )
        }
    }
}
