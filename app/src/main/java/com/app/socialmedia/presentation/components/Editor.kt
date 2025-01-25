package com.app.socialmedia.presentation.components

import android.net.Uri
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.rounded.Star
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.app.socialmedia.presentation.feed.FeedScreenViewModel

@Composable
fun Editor(
    navController: NavController,
    feedScreenViewModel: FeedScreenViewModel = hiltViewModel(),
    buttonText: String,
    showPrivacyMenu: Boolean,
    placeHolder: String,
    onPostButtonClicked: (String) -> Unit
) {
    var text by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    var visibilityOption by remember { mutableStateOf("Public") }
    var selectedMediaUri by remember { mutableStateOf<Uri?>(null) }

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
        // Privacy dropdown menu
        if (showPrivacyMenu) {
            PrivacyMenu(
                expanded = expanded,
                visibilityOption = visibilityOption,
                onVisibilityOptionSelected = { visibilityOption = it },
                onDismissRequest = { expanded = false },
                onExpandRequest = { expanded = true }
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Text field for the post
        OutlinedTextField(
            value = text,
            onValueChange = { newText -> text = newText },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            placeholder = { Text(text = placeHolder) },
            minLines = 4,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = Color.LightGray,
                focusedBorderColor = Color.Blue
            ),
        )

        // Preview selected media
        selectedMediaUri?.let {
            Text(
                text = "Selected Media: ${it.toString()}",
                color = Color.White,
                modifier = Modifier.padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(15.dp))

        // Footer buttons
        ButtonComponents(
            mediaPickerLauncher = mediaPickerLauncher,
            text = text,
            buttonText = buttonText,
            onPostButtonClicked = { onPostButtonClicked(it)}
        )
    }
}

@Composable
private fun PrivacyMenu(
    expanded: Boolean,
    visibilityOption: String,
    onVisibilityOptionSelected: (String) -> Unit,
    onDismissRequest: () -> Unit,
    onExpandRequest: () -> Unit
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        TextButton(onClick = onExpandRequest) {
            Text(text = visibilityOption, color = Color.Black)
            Icon(
                imageVector = Icons.Default.ArrowDropDown,
                contentDescription = "Dropdown",
                tint = Color.Black
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismissRequest,
            modifier = Modifier.background(Color.DarkGray)
        ) {
            listOf("Public", "Private", "Only Me").forEach { option ->
                DropdownMenuItem(
                    content  = { Text(text = option, color = Color.White) },
                    onClick = {
                        onVisibilityOptionSelected(option)
                        onDismissRequest()
                    }
                )
            }
        }
    }
}

@Composable
private fun ButtonComponents(
    mediaPickerLauncher: ManagedActivityResultLauncher<String, Uri?>,
    text: String,
    buttonText: String,
    onPostButtonClicked: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
            IconButton(onClick = { mediaPickerLauncher.launch("image/* video/*") }) {
                Icon(Icons.Rounded.Star, contentDescription = "Add Image or Video", tint = Color.Gray)
            }
            IconButton(onClick = { /* Handle GIF */ }) {
                Icon(Icons.Default.Info, contentDescription = "Add GIF", tint = Color.Gray)
            }
            IconButton(onClick = { /* Handle emoji */ }) {
                Icon(Icons.Default.Face, contentDescription = "Add Emoji", tint = Color.Gray)
            }
            IconButton(onClick = { /* Handle location */ }) {
                Icon(Icons.Default.LocationOn, contentDescription = "Add Location", tint = Color.Gray)
            }
        }

        Button(
            onClick ={ onPostButtonClicked(text)},
            enabled = text.isNotEmpty(),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (text.isNotEmpty()) Color.Blue else Color.Gray
            )
        ) {
            Text(text = buttonText, color = if(text.isNotEmpty()){ Color.White} else {
                Color.Gray
            }
            )
        }
    }
}