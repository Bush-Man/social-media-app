package com.app

import android.app.Application
import com.app.socialmedia.util.AuthTokenManager
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@HiltAndroidApp
class SocialMediaApp : Application()