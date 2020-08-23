package com.emedinaa.kotlinmvvm.data

import com.emedinaa.kotlinmvvm.model.Albums
import com.emedinaa.kotlinmvvm.model.Photos
import com.emedinaa.kotlinmvvm.model.User
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

object ApiClient {

    private val API_BASE_URL = "https://jsonplaceholder.typicode.com"

    private var servicesApiInterface:ServicesApiInterface?=null

    fun build():ServicesApiInterface?{
        var builder: Retrofit.Builder = Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())

        var httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.addInterceptor(interceptor())

        var retrofit: Retrofit = builder.client(httpClient.build()).build()
        servicesApiInterface = retrofit.create(
            ServicesApiInterface::class.java)

        return servicesApiInterface as ServicesApiInterface
    }

    private fun interceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level=HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    interface ServicesApiInterface{
        @GET("/users")
        fun getAllUsers(): Call<List<User>>

        @GET("/albums")
        fun getAllAlbums(): Call<List<Albums>>

        @GET("/photos")
        fun getAllPhotos(): Call<List<Photos>>
    }
}