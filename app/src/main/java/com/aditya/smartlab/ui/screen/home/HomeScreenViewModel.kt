package com.aditya.smartlab.ui.screen.home

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.smartlab.data.models.Device
import com.aditya.smartlab.data.repository.SmartLabRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val repository: SmartLabRepository
) : ViewModel() {

    var devices: MutableState<List<Device>> = mutableStateOf(listOf())
        private set

    var devicesA: MutableState<List<Device>> = mutableStateOf(listOf())
        private set

    var devicesB: MutableState<List<Device>> = mutableStateOf(listOf())
        private set

    var roomTemperature: MutableState<Device> = mutableStateOf(Device())
        private set

    init {
        getDevices()
    }

    fun updateDevice(id: Int, status: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDeviceStatus(id, status)
        }
    }

    private fun getRoomTemperature() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getStatusById(29).collect {
                roomTemperature.value = it
            }
        }
    }

    private fun getDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.getStatus().collect {
                devices.value = it
                devicesA.value = devices.value.subList(0,14)
                devicesB.value = devices.value.subList(14,28)
            }
        }
    }
}