package com.aditya.smartlab.ui.screen.device_detail

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.smartlab.data.models.Device
import com.aditya.smartlab.data.repository.SmartLabRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeviceDetailScreenViewModel @Inject constructor(
    private val repository: SmartLabRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var device: MutableState<Device> = mutableStateOf(Device())
        private set

    var deviceNameText = mutableStateOf("")
        private set

    init {
        getDevice()
        deviceNameText.value = device.value.name
    }

    fun updateDeviceNameText(text: String) {
        deviceNameText.value = text
    }

    fun updateDeviceName() {
        if (deviceNameText.value.isBlank()) return
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDeviceName(device.value.id, deviceNameText.value)
        }
    }

    fun updateDeviceStatus(status: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateDeviceStatus(device.value.id, status)
        }
    }

    private fun getDevice() {
        viewModelScope.launch(Dispatchers.IO) {
            val id = savedStateHandle.get<Int>("id")
            id?.let {
                repository.getStatusById(id).collect {
                    device.value = it
                }
            }
        }
    }
}