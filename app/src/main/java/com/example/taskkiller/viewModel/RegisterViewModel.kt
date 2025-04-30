package com.example.taskkiller.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel(){

    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean>
        get() = _loaderState
    private val firebase = FirebaseAuth.getInstance()

    fun requestSignUp(email: String, password: String) {
        _loaderState.value = true
        viewModelScope.launch {
            val result = firebase.createUserWithEmailAndPassword(email, password).await()
            _loaderState.value = false
            result.user?.let {
                Log.i("Firebase", "SE puedo crear el usuario")
            } ?: run {
                Log.e("Firebase", "Ocurrio un problema")
            }
        }
    }

}