package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.data.OperationCallback

interface AlbumsDataSource {

    fun retrieveUsers(callback: OperationCallback<Albums>)
    fun cancel()
}