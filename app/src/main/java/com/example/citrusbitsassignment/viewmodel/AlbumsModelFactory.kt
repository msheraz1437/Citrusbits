package com.emedinaa.kotlinmvvm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.emedinaa.kotlinmvvm.model.AlbumsDataSource
import com.emedinaa.kotlinmvvm.model.UserDataSource

class AlbumsModelFactory(private val repository: AlbumsDataSource):ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return AlbumsViewModel(repository) as T
    }
}