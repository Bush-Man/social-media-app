package com.app.socialmedia.data.dto

import com.app.socialmedia.domain.model.CreateCommentModel

data class CreateCommentDto(
    val content:String
)
fun CreateCommentDto.toCreateCommentModel():CreateCommentModel{
    return CreateCommentModel(
        content = content
    )
}
fun CreateCommentModel.toCreateCommentDto():CreateCommentDto{
    return CreateCommentDto(
        content = content
    )
}