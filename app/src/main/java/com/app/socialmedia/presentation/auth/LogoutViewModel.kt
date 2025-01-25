package com.app.socialmedia.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.socialmedia.common.Resource
import com.app.socialmedia.domain.use_case.auth.LogoutUseCase
import com.app.socialmedia.util.AuthTokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LogoutViewModel @Inject constructor(
    private val logoutUseCase: LogoutUseCase,
    private val authTokenManager: AuthTokenManager

) :ViewModel(){
    private val _state = MutableStateFlow(LogoutState())
    val state:StateFlow<LogoutState> = _state.asStateFlow()


    fun onLogoutEvent(event:LogoutEvents){
        when(event){

            LogoutEvents.LogoutConfirmEvent -> {
                logoutHandler()
            }
        }
    }

    private fun logoutHandler(){
         viewModelScope.launch {
             logoutUseCase().collect{result ->
                 when(result){
                     is Resource.Error -> {
                         _state.update { it.copy(error = result.errorData.toString()) }
                     }
                     is Resource.Loading -> {
                         _state.update { it.copy(isLoading = result.isLoading) }
                     }
                     is Resource.Success -> {
                         _state.update { it.copy(message = result.data?.message.toString()) }
                         authTokenManager.clearAuthToken()
                     }
                 }

             }
         }
    }
}