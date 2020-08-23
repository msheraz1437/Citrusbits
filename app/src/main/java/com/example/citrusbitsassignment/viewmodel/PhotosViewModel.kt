package com.emedinaa.kotlinmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emedinaa.kotlinmvvm.data.OperationCallback
import com.emedinaa.kotlinmvvm.model.*

class PhotosViewModel(private val repository: PhotosDataSource):ViewModel() {

    private val _photos = MutableLiveData<List<Photos>>().apply { value = emptyList() }
    val photo: LiveData<List<Photos>> = _photos

    private val _isViewLoading=MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<Any>()
    val onMessageError:LiveData<Any> = _onMessageError

    private val _isEmptyList=MutableLiveData<Boolean>()
    val isEmptyList:LiveData<Boolean> = _isEmptyList

    fun loadUsersData(){
        _isViewLoading.postValue(true)
        repository.retrieveUsers(object:OperationCallback<Photos>{
            override fun onError(error: String?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue( error)
            }

            override fun onSuccess(data: List<Photos>?) {
                _isViewLoading.postValue(false)

                if(data!=null){
                    if(data.isEmpty()){
                        _isEmptyList.postValue(true)
                    }else{
                        _photos.value= data
                    }
                }
            }
        })
    }

}