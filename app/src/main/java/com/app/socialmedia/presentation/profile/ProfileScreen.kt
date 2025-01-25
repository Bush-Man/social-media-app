package com.app.socialmedia.presentation.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.app.socialmedia.presentation.components.TabsComponent
import com.app.socialmedia.util.AuthTokenManager

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController:NavController,
    authTokenManager: AuthTokenManager

) {
    val profileScreenTabs = listOf("Posts", "Shared", "Media")
    val profileScreenPagerState = rememberPagerState(pageCount = { profileScreenTabs.size }, initialPage = 0)
    var userName by remember {
        mutableStateOf("")
    }
   val userInfo =  authTokenManager.getUserInfo().collectAsState(initial = null).value
        if(userInfo !=null){
            userName = userInfo.name


    }

    var isExpanded by remember { mutableStateOf(false) }

    Column(
        modifier = modifier

            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        // Profile Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ProfileImage()
            Spacer(modifier = Modifier.width(16.dp))
            ProfileInfo(userName)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Bio Section
        BioSection(
            isExpanded = isExpanded,
            onBioClick = { isExpanded = !isExpanded }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Action Buttons
        ActionButtons()

        Spacer(modifier = Modifier.height(16.dp))

        TabsComponent(
            tabs = profileScreenTabs,
            pagerState = profileScreenPagerState,
            modifier = modifier,
            scrollableBody = false,
            navController = navController
            )




    }
}

@Composable
private fun ProfileImage() {
    Image(
        imageVector = Icons.Default.Person,
        contentDescription = "Profile Picture",
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(80.dp) // Reduced size for a cleaner look
            .aspectRatio(1f)
            .clip(CircleShape)
            .border(2.dp, Color.LightGray, CircleShape)
    )
}

@Composable
private fun ProfileInfo(
    userName:String
) {
    Column {
        Text(
            text = userName,
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "11 Followers",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "•",
                style = MaterialTheme.typography.bodyMedium
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "2 Following",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Kenya",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun BioSection(
    isExpanded: Boolean,
    onBioClick: () -> Unit
) {
    Text(
        text = "Hello! Enjoy all tech career-related posts and reposts. " +
                "PostLikeDto and follow. This is the first place you should visit " +
                "when you think of tech. It's a collection of most, if not all, posts " +
                "and reposts from different people. You get to see what fellow technocrats are up to.",
        maxLines = if (isExpanded) Int.MAX_VALUE else 3,
        overflow = if (isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis,
        lineHeight = 20.sp,
        modifier = Modifier.clickable(onClick = onBioClick)
    )
}

@Composable
private fun ActionButtons() {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextButton(
            onClick = { /* TODO: Handle Follow Click */ },
            modifier = Modifier
                .weight(1f)
                .border(2.dp, Color.LightGray, CircleShape)
        ) {
            Text(text = "Following")
        }
        IconButton(
            onClick = { /* TODO: Handle Message Click */ }
        ) {
            Icon(imageVector = Icons.Default.MailOutline, contentDescription = "Message")
        }
        IconButton(
            onClick = { /* TODO: Handle Share Click */ }
        ) {
            Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
        }
    }
}
