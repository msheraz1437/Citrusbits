package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.data.OperationCallback

interface PhotosDataSource {

    fun retrieveUsers(callback: OperationCallback<Photos>)
    fun cancel()
}