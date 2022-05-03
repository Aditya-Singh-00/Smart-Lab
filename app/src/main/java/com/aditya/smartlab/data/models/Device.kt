package com.aditya.smartlab.data.models

data class Device(
    val id: Int,
    val name: String,
    val status: Int,
    val type: DeviceType,
    val lastOnTime: Long
)

enum class DeviceType {
    FAN, LIGHT, AC, SENSOR
}
