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
): ViewModel() {

    var device: MutableState<Device?> = mutableStateOf(null)
        private set

    init {
        getDevice()
    }

    fun updateDeviceName(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch(Dispatchers.IO) {
            device.value?.let {
                repository.updateDeviceName(it.id, name)
            }
        }
    }

    fun updateDeviceStatus(status: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            device.value?.let {
                repository.updateDeviceStatus(it.id, status)
            }
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