package com.aditya.smartlab.data.models

data class Device(
    val id: Int = 0,
    val name: String = "",
    val status: Int = 0,
    val type: DeviceType = DeviceType.FAN,
    val lastOnTime: Long = System.currentTimeMillis()
)

enum class DeviceType {
    FAN, LIGHT, AC, SENSOR
}
