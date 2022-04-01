package com.picpay.desafio.android.ui.presentation

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.domain.FetchUseCase
import kotlinx.coroutines.launch
import com.picpay.desafio.android.domain.Result
private const val KEY = "key"
const val KEY_2 = "key"

class UserViewModel(
    private val savedStateHandle: SavedStateHandle,
    private val mapper: UserMapper,
    private val userUseCase: FetchUseCase
) : ViewModel() {

    val userState = MutableLiveData<ViewState<ArrayList<UserViewObject>>>()

    private val objSaved by lazy { savedStateHandle.get<ArrayList<UserViewObject>>(KEY) }

    init {
        fetchUsers()
    }

    fun fetchUsers() {
        userState.value = ViewState.Loading()
        viewModelScope.launch {
            when (val result = userUseCase.execute()) {
                is Result.Success -> {
                    if (result.data.isNullOrEmpty()) {
                        userState.value = ViewState.Empty()
                    } else {
                        val list = mapper.responseToViewObject(result.data)
                        setState((list))
                        userState.value = ViewState.Success((list))
                    }
                }
                is Result.Error -> userState.value = ViewState.Error(result.error.message)
            }
        }
    }

    private fun setState(value: ArrayList<UserViewObject>){
        savedStateHandle.set(KEY, value)
    }

    fun getState(){
        userState.value = objSaved?.let { ViewState.Success(it) }
    }





}