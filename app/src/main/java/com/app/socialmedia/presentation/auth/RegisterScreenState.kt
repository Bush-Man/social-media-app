package com.app.socialmedia.presentation.auth

import android.provider.ContactsContract.CommonDataKinds.Email
import com.app.socialmedia.domain.model.UserRegister
data class RegisterScreenState(
    val name: String = "",
    val email: String = "",
    val password: String = "",
    val passwordConfirmation: String = "",
    val isLoading: Boolean = false,
    val error: String? = null,
    val response: String? = null,
    val passwordErrors: List<String> = emptyList(),
    val emailErrors: List<String> = emptyList(),
    val usernameErrors:List<String> = emptyList()

)
