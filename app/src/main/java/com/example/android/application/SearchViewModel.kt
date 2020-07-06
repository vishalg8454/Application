package com.example.android.application

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.application.network.BookObject
import com.example.android.application.json.BookProperty
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.Main
import java.lang.Exception
//comment
class SearchViewModel : ViewModel() {
    private var _name = MutableLiveData<String>("dd")
    private var _enable = MutableLiveData<Boolean>(true)
    private var _bookProperty = MutableLiveData<BookProperty>()
    val name: LiveData<String>
        get() = _name

    val enable: LiveData<Boolean>
        get() = _enable

    val bookProperty: LiveData<BookProperty>
        get() = _bookProperty

    init {
        Log.i("logcat", name.value.toString())
        Log.i("logcat",_bookProperty.value.toString())
    }

    fun fetch(isbn: String) {
        _bookProperty.value = null
        CoroutineScope(Job() + Main).launch {
//        Log.i("logcat",Thread.currentThread().name)
            var t = BookObject.retrofitService.getPropertiesAsync(isbn)
            try {
                _enable.value = false
                _bookProperty.value = t.await()
                Log.i("logcat", _bookProperty.value!!.items[0].volumeInfo.imageLinks.thumbnail)
                Log.i("logcat", _bookProperty.value!!.items[0].volumeInfo.title)
            } catch (e: Exception) {
                Log.i("logcat", "$e")
                _name.value = e.message
            } finally {
                _enable.value = true

            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("logcat", "clear called")
    }
}