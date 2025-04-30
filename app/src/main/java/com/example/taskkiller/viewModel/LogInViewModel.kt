package com.example.taskkiller.viewModel

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class LogInViewModel : ViewModel() {
    private val _loaderState = MutableLiveData<Boolean>()
    val loaderState: LiveData<Boolean>
        get() = _loaderState
    private val _sessionValid = MutableLiveData<Boolean>()
    val sessionValid: LiveData<Boolean>
        get() = _sessionValid
    >private val firebase = FirebaseAuth.getInstance()

    fun requestSignIn(email: String, password: String) {
        _loaderState.value = true
        viewModelScope.launch {
            val result = firebase.signInWithEmailAndPassword(email, password).await()
            _loaderState.value = false
            result.user?.let {
                _sessionValid.value = true
            } ?: run {
                Log.i("Firebase", "Ocurrio un problema")
            }
        }
    }
}