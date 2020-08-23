package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.data.OperationCallback

interface UserDataSource {

    fun retrieveUsers(callback: OperationCallback<User>)
    fun cancel()
}