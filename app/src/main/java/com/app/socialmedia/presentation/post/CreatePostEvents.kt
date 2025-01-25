package com.app.socialmedia.presentation.post

sealed class CreatePostEvents {
    data object onCreatePostButtonClick : CreatePostEvents()
    data class onPostDescriptionChange(val description:String):CreatePostEvents()

}
