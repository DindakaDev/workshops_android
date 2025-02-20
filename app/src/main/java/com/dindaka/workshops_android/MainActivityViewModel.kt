package com.dindaka.workshops_android

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dindaka.workshops_android.data.local.sharedpreferences.MyPreferences
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(private val myPreferences: MyPreferences): ViewModel(){
    private val _isLogged = MutableLiveData<Boolean>()
    val isLogged: LiveData<Boolean> = _isLogged

    init {
        checkSession()
    }

    private fun checkSession(){
        _isLogged.value = myPreferences.getUid() != null
    }
}