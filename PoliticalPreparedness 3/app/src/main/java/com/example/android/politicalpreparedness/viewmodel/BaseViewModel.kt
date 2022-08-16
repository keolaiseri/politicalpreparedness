package com.example.android.politicalpreparedness.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle

abstract class BaseViewModel(app: Application , private val state: SavedStateHandle) : AndroidViewModel(app) {
    val showSnackBar = MutableLiveData<String>()
    val showSnackBarInt = MutableLiveData<Int>()
}