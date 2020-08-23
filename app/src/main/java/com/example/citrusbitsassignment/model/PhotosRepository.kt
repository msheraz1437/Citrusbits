package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.data.ApiClient
import com.emedinaa.kotlinmvvm.data.OperationCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PhotosRepository:PhotosDataSource {

    private var call:Call<List<Photos>>?=null

    override fun retrieveUsers(callback: OperationCallback<Photos>) {
        call=ApiClient.build()?.getAllPhotos()
        call?.enqueue(object :Callback<List<Photos>>{
            override fun onFailure(call: Call<List<Photos>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<List<Photos>>, response: Response<List<Photos>>) {
                response.body()?.let {
                    if(response.isSuccessful ){
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