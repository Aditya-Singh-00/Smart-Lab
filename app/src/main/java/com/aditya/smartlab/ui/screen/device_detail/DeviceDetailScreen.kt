package com.aditya.smartlab.ui.screen.device_detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aditya.smartlab.data.models.DeviceType
import com.aditya.smartlab.ui.theme.DarkGray
import com.aditya.smartlab.util.getTimeDifference

@Composable
fun DeviceDetailScreen(
    viewModel: DeviceDetailScreenViewModel = hiltViewModel()
) {

    val editScreen = remember { mutableStateOf(false) }

    val device = viewModel.device.value

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(DarkGray)
    ) {
        Row {
            if (editScreen.value) {
                TextField(
                    value = viewModel.deviceNameText.value,
                    onValueChange = {
                        viewModel.updateDeviceNameText(it)
                    }
                )
            } else {
                Text(
                    text = device.name,
                    fontSize = 32.sp,
                    color = Color.White
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            if (device.status != 0) {
                Text(getTimeDifference(System.currentTimeMillis(), device.lastOnTime))
            }
            Switch(
                checked = device.status != 0,
                onCheckedChange = {
                    viewModel.updateDeviceStatus(
                        if (device.status == 0) 100 else 0
                    )
                }
            )
        }

        if (device.type == DeviceType.AC || device.type == DeviceType.FAN) {
            Slider(
                value = device.status.toFloat()/100,
                onValueChange = {
                    viewModel.updateDeviceStatus((it * 100).toInt())
                }
            )
        }
        if (editScreen.value) {
            Button(
                onClick = {
                    editScreen.value = !editScreen.value
                    viewModel.updateDeviceName()
                },
                content = {
                    Text("Save Changes")
                },
                enabled = viewModel.deviceNameText.value.isNotBlank()
            )
        } else {
            Button(
                onClick = { editScreen.value = !editScreen.value },
                content = {
                    Text("Edit Device")
                }
            )
        }
    }
}