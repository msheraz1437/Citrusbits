package com.emedinaa.kotlinmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emedinaa.kotlinmvvm.model.AlbumsDataSource
import com.emedinaa.kotlinmvvm.model.PhotosDataSource
import com.emedinaa.kotlinmvvm.model.UserDataSource

class PhotosModelFactory(private val repository: PhotosDataSource):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PhotosViewModel(repository) as T
    }
}