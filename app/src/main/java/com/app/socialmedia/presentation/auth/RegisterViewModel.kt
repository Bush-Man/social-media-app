package com.app.socialmedia.presentation.auth

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.socialmedia.common.Resource
import com.app.socialmedia.domain.model.UserRegister
import com.app.socialmedia.domain.use_case.auth.RegisterUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase,
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

            is RegisterScreenEvents.clearStateError -> {
                _state.value = state.value.copy(error = null)
            }
        }
    }

    private  fun registerRequest(){

           val name = state.value.name
           val  email = state.value.email
            val password = state.value.password
           val passwordConfirmation = state.value.passwordConfirmation
        val userRegisterInfo = UserRegister(
            name = name,
            email=email,
            password = password,
            passwordConfirmation = passwordConfirmation
        )


        viewModelScope.launch {

            registerUseCase(userRegisterInfo).collect { result ->
//                Log.d("Register state", state.toString())
                when (result) {
                    is Resource.Error -> {
                        val data = result.errorData
                        if(data != null){
                            data.entries.forEach { entry ->
                                if(entry.key.equals("email")){
                                    _state.value = state.value.copy(emailErrors=entry.value)
                                }
                                if(entry.key.equals("name")){
                                    _state.value = state.value.copy(usernameErrors = entry.value)
                                }
                                if(entry.key.equals("password")){
                                    _state.value = state.value.copy(passwordErrors = entry.value)
                                }
                            }

                        }
                        Log.e("Register view model error","Response $result")
                        _state.value = state.value.copy(

                            isLoading = false,
                            error = result.message ?: "An unknown error occurred"

                        )



                    }
                    is Resource.Loading -> {
                        _state.value = result.isLoading.let { state.value.copy(isLoading = it) }
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