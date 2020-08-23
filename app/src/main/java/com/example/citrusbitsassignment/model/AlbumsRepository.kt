package com.emedinaa.kotlinmvvm.model

import com.emedinaa.kotlinmvvm.data.ApiClient
import com.emedinaa.kotlinmvvm.data.OperationCallback
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AlbumsRepository:AlbumsDataSource {

    private var call:Call<List<Albums>>?=null

    override fun retrieveUsers(callback: OperationCallback<Albums>) {
        call=ApiClient.build()?.getAllAlbums()
        call?.enqueue(object :Callback<List<Albums>>{
            override fun onFailure(call: Call<List<Albums>>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(call: Call<List<Albums>>, response: Response<List<Albums>>) {
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