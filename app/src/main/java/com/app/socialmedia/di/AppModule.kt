package com.app.socialmedia.di

import com.app.socialmedia.common.Constants
import com.app.socialmedia.data.remote.SocialMediaApi
import com.app.socialmedia.data.repository.AuthRepositoryImpl
import com.app.socialmedia.domain.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Headers
import okhttp3.OkHttpClient
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
    fun providesOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request = chain.request()
                val newRequest = request.newBuilder()
                    .addHeader("Accept", "application/json")
                    .build()
                chain.proceed(newRequest)
            }
            .build()
    }

    @Provides
    @Singleton
    fun providesRetrofitApi(client: OkHttpClient):SocialMediaApi{
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(SocialMediaApi::class.java)
    }
    @Provides
    @Singleton
    fun providesAuthRepository(api: SocialMediaApi):AuthRepository{
        return AuthRepositoryImpl(api)
    }

}