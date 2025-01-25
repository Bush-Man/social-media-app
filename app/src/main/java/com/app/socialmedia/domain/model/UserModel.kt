package com.app.socialmedia.domain.model

import com.google.gson.annotations.SerializedName

data class UserModel(

      val id: Int,
     val name: String,
     val email: String,
     val emailVerifiedAt: String?,
     val createdAt: String,
     val updatedAt: String,
     val rememberToken: String?,
     val password: String?,
)
