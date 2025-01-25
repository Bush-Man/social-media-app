package com.app.socialmedia.presentation.parent_screen_layout

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.currentRecomposeScope
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.navigation.NavController
import com.app.socialmedia.data.dto.toUserDto
import com.app.socialmedia.presentation.chat.ChatRoomEvents
import com.app.socialmedia.presentation.chat.ChatRoomScreen
import com.app.socialmedia.presentation.chat.ChatRoomViewModel
import com.app.socialmedia.presentation.feed.FeedScreen
import com.app.socialmedia.presentation.home.HomeScreen
import com.app.socialmedia.presentation.navigation.Screen
import com.app.socialmedia.presentation.post.CreatePostScreen
import com.app.socialmedia.presentation.post.PostViewModel
import com.app.socialmedia.presentation.profile.ProfileScreen
import com.app.socialmedia.util.AuthTokenManager
import kotlinx.coroutines.coroutineScope

@SuppressLint("SuspiciousIndentation")
@Composable
fun ParentScreenLayout(
    modifier: Modifier = Modifier,
    navController: NavController,
    postViewModel: PostViewModel = hiltViewModel(),
    authTokenManager: AuthTokenManager
) {
    // Removed unused variable
    // val posts = postViewModel::onPostEvent

      val userInfo = authTokenManager.getUserInfo().collectAsState(initial = null).value

     var userName by remember { mutableStateOf("") }
    if(userInfo != null){
        userName = userInfo.name
    }

    // Use rememberSaveable to preserve state across configuration changes
    var selectedTabIndex by rememberSaveable { mutableStateOf(0) }

    val screenNavTabs = listOf(
        Icons.Default.Home to "home",
        Icons.Default.Search to "search",
        Icons.Default.MailOutline to "message",
        Icons.Default.Edit to "post",
        Icons.Default.Person to "profile"
    )

    Scaffold(
        topBar = { TopBar(modifier = Modifier, userName = userName,navController = navController) },
        modifier = modifier.fillMaxSize(),
        bottomBar = {
            BottomNavigationBarExample(
                bottomNavTabs = screenNavTabs,
                selectedTabIndex = selectedTabIndex,
                onSelectTab = { index -> selectedTabIndex = index }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize() // Ensures proper constraints
        ) {
            when (selectedTabIndex) {
                0 -> HomeScreen(navController = navController)
                1 -> Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "search screen not implemented")
                }
                2 -> ChatRoomScreen(chatRoomId = "4_6")
                3 -> CreatePostScreen(navController = navController)
                4 -> ProfileScreen(navController = navController, authTokenManager = authTokenManager)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TopBar(
    modifier: Modifier,
    userName: String,
    navController: NavController
) {
    var isExpanded by remember { mutableStateOf(false) }
    TopAppBar(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp),


        navigationIcon = {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "go back",
                modifier.clickable { navController.navigateUp() }
            )
        },
        title = {
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center // Center the title
            ) {
                Text(
                    text = "@$userName",
                    textAlign = TextAlign.Center
                )
            }
        },
        actions = {
            IconButton(onClick = { isExpanded = true }) {
                Icon(imageVector = Icons.Default.MoreVert, contentDescription = "more")
            }
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("logout") },
                    onClick = { navController.navigate(Screen.LogoutScreen.route)}
                )
            }
        }
    )
}

@Composable
fun BottomNavigationBarExample(
    bottomNavTabs: List<Pair<ImageVector, String>>,
    selectedTabIndex: Int,
    onSelectTab: (Int) -> Unit
) {
    NavigationBar {
        bottomNavTabs.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = {
                    Icon(imageVector = item.first, contentDescription = item.second)
                },
                label = { Text(item.second) },
                selected = selectedTabIndex == index,
                onClick = { onSelectTab(index) }
            )
        }
    }
}

@Composable
fun LoadingIndicator() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}



