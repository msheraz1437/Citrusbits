package com.emedinaa.kotlinmvvm.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.emedinaa.kotlinmvvm.data.OperationCallback
import com.emedinaa.kotlinmvvm.model.Albums
import com.emedinaa.kotlinmvvm.model.AlbumsDataSource
import com.emedinaa.kotlinmvvm.model.UserDataSource
import com.emedinaa.kotlinmvvm.model.User

class AlbumsViewModel(private val repository: AlbumsDataSource):ViewModel() {

    private val _albums = MutableLiveData<List<Albums>>().apply { value = emptyList() }
    val albums: LiveData<List<Albums>> = _albums

    private val _isViewLoading=MutableLiveData<Boolean>()
    val isViewLoading:LiveData<Boolean> = _isViewLoading

    private val _onMessageError=MutableLiveData<Any>()
    val onMessageError:LiveData<Any> = _onMessageError

    private val _isEmptyList=MutableLiveData<Boolean>()
    val isEmptyList:LiveData<Boolean> = _isEmptyList

    fun loadUsersData(){
        _isViewLoading.postValue(true)
        repository.retrieveUsers(object:OperationCallback<Albums>{
            override fun onError(error: String?) {
                _isViewLoading.postValue(false)
                _onMessageError.postValue( error)
            }

            override fun onSuccess(data: List<Albums>?) {
                _isViewLoading.postValue(false)

                if(data!=null){
                    if(data.isEmpty()){
                        _isEmptyList.postValue(true)
                    }else{
                        _albums.value= data
                    }
                }
            }
        })
    }

}