package com.emedinaa.kotlinmvvm.di

import androidx.lifecycle.ViewModelProvider
import com.emedinaa.kotlinmvvm.model.*
import com.emedinaa.kotlinmvvm.viewmodel.AlbumsModelFactory
import com.emedinaa.kotlinmvvm.viewmodel.PhotosModelFactory
import com.emedinaa.kotlinmvvm.viewmodel.ViewModelFactory

object Injection {

    private val USER_DATA_SOURCE:UserDataSource = UserRepository()
    private val UserViewModelFactory = ViewModelFactory(USER_DATA_SOURCE)

    private val Albums_DATA_SOURCE: AlbumsDataSource = AlbumsRepository()
    private val AlbumsViewModelFactory = AlbumsModelFactory(Albums_DATA_SOURCE)

    private val photos_DATA_SOURCE: PhotosRepository = PhotosRepository()
    private val photosViewModelFactory = PhotosModelFactory(photos_DATA_SOURCE)

    fun providerRepository():UserDataSource{
        return USER_DATA_SOURCE
    }

    fun provideViewModelFactory(): ViewModelProvider.Factory{
        return UserViewModelFactory
    }

    fun providerAlbumsRepository():AlbumsDataSource{
        return Albums_DATA_SOURCE
    }

    fun provideAlbumsViewModelFactory(): ViewModelProvider.Factory{
        return AlbumsViewModelFactory
    }

    fun providerPhotosRepository():PhotosDataSource{
        return photos_DATA_SOURCE
    }

    fun providePhotosViewModelFactory(): ViewModelProvider.Factory{
        return photosViewModelFactory
    }
}