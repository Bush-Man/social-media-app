package com.app.socialmedia.presentation.auth

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.app.socialmedia.common.Resource
import com.app.socialmedia.data.dto.toUserModel
import com.app.socialmedia.domain.model.UserLogin
import com.app.socialmedia.domain.use_case.auth.LoginUseCase
import com.app.socialmedia.presentation.navigation.Screen
import com.app.socialmedia.util.AuthTokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class LoginScreenViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val authTokenManager: AuthTokenManager,
):ViewModel() {

    private val _state = mutableStateOf(LoginScreenState())
    val state:State<LoginScreenState> = _state;

    fun onLoginEvent(event:LoginScreenEvents){
        when(event){
            is LoginScreenEvents.onEmailChange -> {
                _state.value = state.value.copy(email = event.email)
            }
            is LoginScreenEvents.onPasswordChange -> {
                _state.value = state.value.copy(password = event.password)
            }

            LoginScreenEvents.onLoginButtonClick -> {
                handleLoginRequest()
            }

        }
    }

    private fun handleLoginRequest(){
        val loginData = UserLogin(
            email = state.value.email,
            password = state.value.password
        )
     Log.d("view model","$loginData")
        viewModelScope.launch {

            loginUseCase(loginData).collect { result ->
                when (result) {
                    is Resource.Error -> {
                        _state.value = state.value.copy(error = result.message)
                    }
                    is Resource.Loading -> {
                        _state.value = state.value.copy(isLoading = result.isLoading)
                    }
                    is Resource.Success -> {
                        if(result.data != null) {
                            val token = result.data.token
                            val userInfo = result.data.user.toUserModel()

                                authTokenManager.saveAuthToken(token)
                                authTokenManager.saveUserInfo(userInfo)

                            _state.value = state.value.copy(response = result.data)

                        }
                    }
                }
            }
        }
    }
}