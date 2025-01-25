package com.app.socialmedia

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.app.socialmedia.presentation.feed.FeedScreen
import com.app.socialmedia.presentation.navigation.NavGraphSetup
import com.app.socialmedia.presentation.ui.theme.SocialMediaTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SocialMediaTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    val navController = rememberNavController()
//                    FeedScreen(navController = navController)
                    NavGraphSetup(navController=navController)
//PostScreen(postId = "1")
//           ParentScreenLayout(modifier = Modifier)
//                    LoginScreen()
//                  RegisterScreen()

                }
            }
        }
    }
}
//
//@Composable
//fun Greeting(name: String, modifier: Modifier = Modifier) {
//    Text(
//        text = "Hello $name!",
//        modifier = modifier
//    )
//}
//
//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    SocialMediaTheme {
//        Greeting("Android")
//    }
//}

/*
*   val dummyPost = PostModel(
                        postId = "post123",
                        userId = "user456",
                        comments = listOf(
                            PostCommentModel(
                                content = "Great post!",
                                hashtags = listOf("#Inspiration", "#Motivation"),
                                postId = 123,
                                userId = 789,
                                commentId = 1,
                                likesCount = 5,
                                mentionedUsers = listOf("user890", "user123"),
                                parentCommentId = 1,
                                repliesCount = 2,
                                reportedCount = 0,
                                reportedReasons = "",
                                visibility = "public"
                            ),
                            PostCommentModel(
                                content = "Interesting thoughts.",
                                hashtags = listOf("#Discussion"),
                                postId = 123,
                                userId = 101,
                                commentId = 2,
                                likesCount = 3,
                                mentionedUsers = listOf("user456"),
                                parentCommentId = 1,
                                repliesCount = 0,
                                reportedCount = 1,
                                reportedReasons = listOf("Spam"),
                                visibility = "public"
                            )
                        ),
                        commentsCount = 2,
                        createdAt = "2025-01-19T12:34:56Z",
                        description = "This is a sample post description with some #tags and interesting content.",
                        likes = listOf(
                            PostLikeModel(
                                postId = "post123",
                                userId = "user789"
                            ),
                            PostLikeModel(
                                postId = "post123",
                                userId = "user101"
                            )
                        ),
                        likesCount = 2,
                        privacy = "public",
                        postOwner = PostOwner(
                            ownerId = 456,
                            ownerName = "John Doe",
                            ownerEmail = "john.doe@example.com",
                            emailVerifiedAt = 1,
                            password = "hashedpassword123",
                            rememberToken = "",
                            createdAt = "2025-01-10T08:00:00Z",
                            updatedAt = "2025-01-15T10:30:00Z"
                        )
                    )

* */