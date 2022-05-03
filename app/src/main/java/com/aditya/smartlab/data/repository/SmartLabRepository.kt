package com.aditya.smartlab.data.repository

import com.aditya.smartlab.data.models.Device
import kotlinx.coroutines.flow.Flow

interface SmartLabRepository {

    fun updateDeviceName(id: Int, name: String)

    fun updateDeviceStatus(id: Int, status: Int)

    fun getStatus(): Flow<MutableList<Device>>

    fun getStatusById(id: Int): Flow<Device>

}