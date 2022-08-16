package com.example.android.politicalpreparedness.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.android.politicalpreparedness.R
import com.example.android.politicalpreparedness.data.Address
import com.example.android.politicalpreparedness.remote.CivicsApiInstance
import com.example.android.politicalpreparedness.repository.RepresentativesRepository
import kotlinx.coroutines.launch
import kotlin.random.Random

class RepresentativesViewModel(app: Application, private val state: SavedStateHandle): BaseViewModel(app, state) {

    companion object{
        const val BUNDLE_KEY = "KEY"
    }

    private val repository = RepresentativesRepository(CivicsApiInstance)

    val representatives = repository.representatives

    private val _address = MutableLiveData<Address>()
    val address: LiveData<Address>
        get() = _address

    private val _states = MutableLiveData<List<String>>()
    val states: LiveData<List<String>>
        get() = _states

    val selectedStateIndex = MutableLiveData<Int>()


    init {
        _address.value = Address("", "","","New York","")
        _states.value = app.resources.getStringArray(R.array.states).toList()
        state.set(BUNDLE_KEY, _address.value)
        state.set(BUNDLE_KEY, _states.value)

    }

    fun onSearchButtonClick() {
        refreshRepresentatives()
    }

    private fun refreshRepresentatives() {
        viewModelScope.launch {
            try {
                address.value!!.state = getSelectedState(selectedStateIndex.value!!)
                val addressStr = address.value!!.toFormattedString()
                repository.refreshRepresentatives(addressStr)

            } catch (e: Exception) {
                e.printStackTrace()
                showSnackBarInt.postValue(R.string.no_network_or_address_not_found_msg)
            }
        }
    }

    private fun getSelectedState(stateIndex: Int) : String {
        return states.value!!.toList()[stateIndex]
    }

    fun refreshByCurrentLocation(address: Address) {

        val stateIndex = _states.value?.indexOf(address.state)
        if (stateIndex != null && stateIndex >= 0) {
            selectedStateIndex.value = stateIndex!!
            _address.value = address
            refreshRepresentatives()

        } else {
            showSnackBarInt.value = R.string.current_location_is_not_us_msg
        }
    }
}