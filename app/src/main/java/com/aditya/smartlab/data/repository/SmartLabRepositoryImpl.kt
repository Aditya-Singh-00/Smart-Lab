package com.aditya.smartlab.data.repository

import com.aditya.smartlab.data.models.Device
import com.aditya.smartlab.util.LAB
import com.aditya.smartlab.util.NAME
import com.aditya.smartlab.util.STATUS
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class SmartLabRepositoryImpl(
    private val firebaseDatabase: FirebaseDatabase
) : SmartLabRepository {
    override fun updateDeviceName(id: Int, name: String) {
        firebaseDatabase.getReference(LAB)
            .child(id.toString())
            .child(NAME)
            .setValue(name)
    }

    override fun updateDeviceStatus(id: Int, status: Int) {
        firebaseDatabase.getReference(LAB)
            .child(id.toString())
            .child(STATUS)
            .setValue(status)
    }

    override fun getStatus(): Flow<MutableList<Device>> {
        return callbackFlow {
            firebaseDatabase.getReference(LAB)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val devices = mutableListOf<Device>()
                        for (deviceSnapshot in snapshot.children) {
                            deviceSnapshot.getValue(Device::class.java)?.let {
                                devices.add(it)
                            }
                        }
                        this@callbackFlow.trySend(devices).isSuccess
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })
            awaitClose()
        }
    }

    override fun getStatusById(id: Int): Flow<Device> {
        return callbackFlow {
            firebaseDatabase.getReference(LAB).child(id.toString())
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        snapshot.getValue(Device::class.java)?.let {
                            this@callbackFlow.trySend(it).isSuccess
                        }
                    }
                    override fun onCancelled(error: DatabaseError) {}
                })
            awaitClose()
        }
    }
}