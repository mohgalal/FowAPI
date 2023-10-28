package com.example.observertaskodc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

class MainViewModel() : ViewModel() {

    private var _textLiveData = MutableLiveData<String>()
    val textLiveData: LiveData<String> = _textLiveData

    private var _textSharedFlow = MutableSharedFlow<String>()
    val textSharedFlow
        get() = _textSharedFlow.asSharedFlow()

   /* var textFlow = flow<String> {
        emit("Hello World!")
    }*/

    private var _textStateFlow = MutableStateFlow<String>("Hello World!")
    val textStateFlow
        get() = _textStateFlow.asStateFlow()

    fun setTextLiveData() {
            _textLiveData.value = "I'm LiveData"
    }

    fun setTextSharedFlow() {
        CoroutineScope(Dispatchers.IO).launch {
            _textSharedFlow.emit("I'm SharedFlow")
        }
    }

    fun setTextStateFlow() {
        CoroutineScope(Dispatchers.IO).launch {
            _textStateFlow.value = "I'm StateFlow"
        }
    }

    fun getTextFlowValue() = flow<String> {
        repeat(5){
            emit("Item: $it ")
            delay(1000)
        }
    }
}