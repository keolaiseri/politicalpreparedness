package com.example.android.politicalpreparedness.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.data.Division
import com.example.android.politicalpreparedness.data.Election
import com.example.android.politicalpreparedness.local.ActiveElectionDatabase
import com.example.android.politicalpreparedness.local.SavedElectionDatabase
import com.example.android.politicalpreparedness.remote.CivicsApiInstance
import com.example.android.politicalpreparedness.repository.ElectionsRepository
import kotlinx.coroutines.launch
import java.util.*

class ElectionsViewModel(app: Application, private val state: SavedStateHandle) : BaseViewModel(app, state) {

    private val repository = ElectionsRepository(
        ActiveElectionDatabase.getInstance(app),
        SavedElectionDatabase.getInstance(app),
        CivicsApiInstance
    )

    val activeElections = repository.activeElections
    val savedElections = repository.savedElections

    private val mockData = false
    private val _mockElections = MutableLiveData<List<Election>>()
    val mockElections : LiveData<List<Election>>
        get() = _mockElections

    init {
        if(mockData) {
            mockElections()
        } else {
            refreshElections()
        }
    }

    private fun refreshElections() {
        viewModelScope.launch {
            try {
                repository.refreshElections()

            } catch (e: Exception) {
                e.printStackTrace()
                showSnackBarInt.postValue(R.string.fail_no_network_msg)
            }
        }
    }

    private fun mockElections() {

        val mockElections = mutableListOf<Election>()

        var count = 1
        while (count <= 10) {

            val data = Election(
                count,
                "name:$count",
                Date(),
                Division("id", "US", "state")
            )

            mockElections.add(data)

            ++count
        }

        _mockElections.postValue(mockElections)
    }
}