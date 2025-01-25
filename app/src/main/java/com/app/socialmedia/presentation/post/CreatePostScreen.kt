package com.app.socialmedia.presentation.post


import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.TextButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.socialmedia.presentation.feed.FeedScreenEvents
import com.app.socialmedia.presentation.feed.FeedScreenViewModel

@Composable
fun CreatePostScreen(
    navController: NavController,
    createPostViewModel: CreatePostViewModel = hiltViewModel(),
    feedScreenViewModel: FeedScreenViewModel = hiltViewModel()
) {
    var expanded by remember { mutableStateOf(false) }
    var visibilityOption by remember { mutableStateOf("Public") }
    var selectedMediaUri by remember { mutableStateOf<Uri?>(null) }

    val uiState = createPostViewModel.state.value
    val uiEvents = createPostViewModel::onCreatePostEvent
    val feedScreenEvents= feedScreenViewModel::onFeedScreenEvent


    // Launcher for picking images or videos
    val mediaPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        selectedMediaUri = uri
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)

    ) {
        // Top bar with dropdown for visibility options
        //Make reusable
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box {
                TextButton(
                    onClick = { expanded = true },

                    ) {
                    Text(text = visibilityOption, color = Color.Black)
                    Icon(Icons.Default.ArrowDropDown, contentDescription = "Dropdown", tint = Color.Black)
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.background(Color.DarkGray)
                ) {
                    listOf("Public", "Private", "Only Me").forEach { option ->
                        DropdownMenuItem(
                            text ={ Text(text = option, color = Color.White)},
                            onClick = {
                                visibilityOption = option
                                expanded = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            ButtonComponents(
                mediaPickerLauncher,
                description =uiState.description ?: "",
                createPostButtonClicked = {
                    uiEvents(CreatePostEvents.onCreatePostButtonClick)
                    feedScreenEvents(FeedScreenEvents.loadFeedPostsOnPageLoad)

                }
                )
        }



        // Text field for the post
        OutlinedTextField(
            value = uiState.description ?: "",
            onValueChange ={uiEvents(CreatePostEvents.onPostDescriptionChange(description = it))},
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 8.dp),
            placeholder = { Text(text = "create a post", color = Color.Gray)},





            )

        // Preview selected media/image for the post
        if (selectedMediaUri != null) {
            Text(
                text = "Selected Media: ${selectedMediaUri.toString()}",
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }

    }
}

@Composable
private fun ButtonComponents(
    mediaPickerLauncher: ManagedActivityResultLauncher<String, Uri?>,
    description:String,
    createPostButtonClicked:()->Unit
){

    // Footer with options
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // Icons (e.g., image, GIF, poll, emoji, etc.)
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            IconButton(onClick = {
                mediaPickerLauncher.launch("image/* video/*") // Allow image and video selection
            }) {
                Icon(Icons.Rounded.Star, contentDescription = "Add Image or Video", tint = Color.Gray)
            }
            IconButton(onClick = { /* Handle GIF */ }) {
                Icon(Icons.Default.Info, contentDescription = "Add GIF", tint = Color.Gray)
            }
//                    IconButton(onClick = { /* Handle poll */ }) {
//                        Icon(Icons.Default.BarChart, contentDescription = "Add Poll", tint = Color.Gray)
//                    }
            IconButton(onClick = { /* Handle emoji */ }) {
                Icon(Icons.Default.Face, contentDescription = "Add Emoji", tint = Color.Gray)
            }
            IconButton(onClick = { /* Handle location */ }) {
                Icon(Icons.Default.LocationOn, contentDescription = "Add Location", tint = Color.Gray)
            }
        }

        // Click to create/upload Post
        Button(
            onClick = {
                createPostButtonClicked()

                      },
            enabled = description.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (description.isNotEmpty()) Color.Cyan else Color.Gray
            )
        ) {
            Text(text = "post", color = Color.White)
        }
    }
}

