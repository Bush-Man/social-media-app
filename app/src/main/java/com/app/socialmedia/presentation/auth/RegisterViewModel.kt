package com.app.socialmedia.presentation.auth

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.socialmedia.common.Resource
import com.app.socialmedia.domain.model.UserRegister
import com.app.socialmedia.domain.repository.AuthRepository
import com.app.socialmedia.domain.use_case.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.logging.Logger
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
    private val authRepository: AuthRepository
):ViewModel() {
    private val _state = mutableStateOf(RegisterScreenState())
    val state:State<RegisterScreenState> = _state;

    fun onRegisterScreenEvent(event:RegisterScreenEvents){
        when(event){
            is RegisterScreenEvents.onEmailChange -> {
                _state.value = state.value.copy(email = event.email)
            }
            is RegisterScreenEvents.onNameChange -> {
                _state.value = state.value.copy(name = event.name)
            }
            is RegisterScreenEvents.onPasswordChange -> {
                _state.value = state.value.copy(password = event.password)
            }
            is RegisterScreenEvents.onPasswordConfirmChange -> {
                _state.value = state.value.copy(passwordConfirmation = event.passwordConfirm)
            }
            is RegisterScreenEvents.onRegisterButtonClick -> {
                registerRequest()
            }

        }
    }

    private  fun registerRequest(){
        val userRegisterInfo = UserRegister(
            name = state.value.name,
            email = state.value.email,
            password = state.value.password,
            passwordConfirmation = state.value.passwordConfirmation
        )


        viewModelScope.launch {

            registerUseCase(userRegisterInfo).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            error = result.message ?: "An unknown error occurred"
                        )
                    }
                    is Resource.Loading -> {
                        _state.value = result.isLoading?.let { state.value.copy(isLoading = it) }!!
                    }
                    is Resource.Success -> {
                        _state.value = state.value.copy(
                            isLoading = false,
                            response = result.data.toString()
                        )
                    }
                }

        }
}
    }

}