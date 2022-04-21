package com.igordam.flowexample

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _liveData = MutableLiveData<Int>(0)
    val liveData: LiveData<Int> = _liveData

    private val _stateFlow = MutableStateFlow(0)
    val stateFlow = _stateFlow.asStateFlow()

    private val _sharedFlow = MutableSharedFlow<String>()
    val sharedFlow = _sharedFlow.asSharedFlow()


    fun updateLiveData() {
        _liveData.value?.let { count ->
            _liveData.value = (count + 1)
        } ?: run {
            _liveData.value = 0
        }
    }

    fun updateStateFlow() {
        _stateFlow.value = _stateFlow.value + 1
    }

    fun updateFlow(): Flow<String> {
        return flow {
            repeat(6) { value ->
                emit(value.toString())
                delay(1000L)
            }
        }
    }

    fun updateSharedFlow() {
        viewModelScope.launch {
            _sharedFlow.emit("SharedFlow")
        }
    }

}