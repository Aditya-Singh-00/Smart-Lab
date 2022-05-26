package com.aditya.smartlab.data.repository

import com.aditya.smartlab.data.models.Device
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface SmartLabRepository {

    suspend fun login(email: String, password: String): FirebaseUser?

    fun getCurrentUser(): FirebaseUser?

    fun updateDeviceName(id: Int, name: String)

    fun updateDeviceStatus(id: Int, status: Int)

    fun getStatus(): Flow<MutableList<Device>>

    fun getStatusById(id: Int): Flow<Device>

}