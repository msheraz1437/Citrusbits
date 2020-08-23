package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.data.ApiClient
import com.emedinaa.kotlinmvvm.data.OperationCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRepository:UserDataSource {

    private var call:Call<List<User>>?=null

    override fun retrieveUsers(callback: OperationCallback<User>) {
        call=ApiClient.build()?.getAllUsers()
        call?.enqueue(object :Callback<List<User>>{
            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                response.body()?.let {
                    if(response.isSuccessful ){//&& (it.isSuccess())
                        callback.onSuccess(it)
                    }else{
                        callback.onError(response.message())
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}