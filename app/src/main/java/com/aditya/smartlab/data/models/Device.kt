package com.aditya.smartlab.data.models

import com.google.firebase.database.PropertyName

data class Device(
    @PropertyName("id")
    val id: Int = 0,
    @PropertyName("name")
    val name: String = "",
    @PropertyName("status")
    val status: Int = 0,
    @PropertyName("type")
    val type: DeviceType = DeviceType.FAN,
    @PropertyName("lastOnTime")
    val lastOnTime: Long = System.currentTimeMillis()
)

enum class DeviceType {
    FAN, LIGHT, AC, SENSOR
}
