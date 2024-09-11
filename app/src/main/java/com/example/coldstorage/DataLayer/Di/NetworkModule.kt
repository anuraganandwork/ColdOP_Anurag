package com.example.coldstorage.DataLayer.Di

import com.example.coldstorage.DataLayer.Api.ColdOpApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {


    @Singleton
    @Provides
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder().baseUrl("https://coldop-backend-1gn6.onrender.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    @Singleton
    @Provides
    fun registerUser(retrofit: Retrofit):ColdOpApi{
        return retrofit.create(ColdOpApi::class.java)
    }



}