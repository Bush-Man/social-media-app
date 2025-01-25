package com.app.socialmedia.di

import android.content.Context
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.app.socialmedia.common.Constants
import com.app.socialmedia.data.remote.ChatApi
import com.app.socialmedia.data.remote.SocialMediaApi
import com.app.socialmedia.data.repository.AuthRepositoryImpl
import com.app.socialmedia.data.repository.ChatRepositoryImpl
import com.app.socialmedia.data.repository.PostRepositoryImpl
import com.app.socialmedia.domain.repository.AuthRepository
import com.app.socialmedia.domain.repository.ChatRepository
import com.app.socialmedia.domain.repository.PostRepository
import com.app.socialmedia.util.AuthTokenManager
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.internal.commonEmptyHeaders
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
//@Singleton
@InstallIn(SingletonComponent::class)
object AppModule {


    @Provides
    @Singleton
    fun providesOkHttpClient(authTokenManager: AuthTokenManager): OkHttpClient {
        val authInterceptor = Interceptor { chain ->
            val request = chain.request()
            val urlPath = request.url.encodedPath

            // Skip token for login and register requests
            if (urlPath.contains("login") || urlPath.contains("register")) {
                return@Interceptor chain.proceed(request)
            }

//             Get token from authTokenManager
            val token = runBlocking {
                authTokenManager.getAuthToken().firstOrNull() // Collect the first emitted value
            }
            val newRequest = request.newBuilder()
                .addHeader("Accept", "application/json")


            if (!token.isNullOrEmpty()) {

                    Log.e("Authorization", "token found.$token")
                newRequest.addHeader("Authorization", "Bearer $token")

            }else{
                Log.e("Authorization", "No token found.")

            }




            chain.proceed(newRequest.build())
        }

        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()

    }
    @Provides
    @Singleton
    fun providesRetrofitApi(client: OkHttpClient):SocialMediaApi{
//        const val BASE_URL ="http://10.0.2.2:8000/api/"
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
            .create(SocialMediaApi::class.java)
    }
    @Provides
    @Singleton
    fun providesChatApi(client: OkHttpClient):ChatApi{
//        const val BASE_URL ="http://10.0.2.2:8000/api/"
//        const val CHAT_BASE_URL ="http://10.0.2.2:3000/"
        return Retrofit.Builder()
            .baseUrl(Constants.CHAT_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ChatApi::class.java)
    }
    @Provides
    @Singleton
    fun providesAuthRepository(api: SocialMediaApi):AuthRepository{
        return AuthRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesAuthTokenManager(@ApplicationContext context: Context):AuthTokenManager{
        return AuthTokenManager(context)
    }

    @Provides
    @Singleton
    fun providesNavHostController(@ApplicationContext context: Context):NavHostController{
        return NavHostController(context)
    }
    @Provides
    @Singleton
    fun providesNavController(@ApplicationContext context: Context): NavController {
        return NavController(context)
    }

    @Provides
    @Singleton
    fun providesPostRepository(api:SocialMediaApi): PostRepository {
        return PostRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesChatRepository(api:ChatApi):ChatRepository{
        return ChatRepositoryImpl(api)
    }

/*
*   @Binds
    @Singleton
    abstract fun bindPostRepository(
        postRepositoryImpl: PostRepositoryImpl
    ): PostRepository
*This tells Hilt:
"Whenever PostRepository is required, provide PostRepositoryImpl."
* */
}