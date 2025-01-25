package com.app.socialmedia.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.app.socialmedia.domain.model.UserModel
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name="auth_prefs")
class AuthTokenManager(
    private val context:Context
){

    companion object{
        private val AUTH_TOKEN_KEY = stringPreferencesKey("auth_token")
        private val USER_INFO_KEY = stringPreferencesKey("user_info")
    }


    suspend fun saveAuthToken(token:String){
        context.dataStore.edit { preferences ->
            preferences[AUTH_TOKEN_KEY] = token
        }

    }
    suspend fun saveUserInfo(user:UserModel){
        val userInfoJson = Gson().toJson(user)
        context.dataStore.edit { preferences ->
            preferences[USER_INFO_KEY] = userInfoJson
        }
    }

    fun getUserInfo():Flow<UserModel?>{
        return context.dataStore.data.map { preferences ->
            val userInfoJson = preferences[USER_INFO_KEY]
            if(userInfoJson != null){
                Gson().fromJson(userInfoJson,UserModel::class.java)
            }else{
                null
            }
        }
    }

    fun getAuthToken():Flow<String?>{
        return context.dataStore.data.map { preferences ->
            preferences[AUTH_TOKEN_KEY]
        }
    }


    suspend fun clearAuthToken(){

        context.dataStore.edit { preferences ->
            preferences.remove(AUTH_TOKEN_KEY)
            preferences.remove(USER_INFO_KEY)
        }
    }
}