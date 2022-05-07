package com.aditya.smartlab.ui.screen.device_detail

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aditya.smartlab.data.models.DeviceType
import com.aditya.smartlab.ui.theme.DarkGray
import com.aditya.smartlab.ui.theme.Green
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
            .padding(16.dp)
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
                Text(
                    text = getTimeDifference(System.currentTimeMillis(), device.lastOnTime),
                    color = Color.White
                )
            }
            Switch(
                checked = device.status != 0,
                onCheckedChange = {
                    viewModel.updateDeviceStatus(
                        if (device.status == 0) 100 else 0
                    )
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Green,
                    uncheckedThumbColor = DarkGray
                )
            )
        }

        if (device.type == DeviceType.AC || device.type == DeviceType.FAN) {
            Slider(
                value = device.status.toFloat()/100,
                onValueChange = {
                    viewModel.updateDeviceStatus((it * 100).toInt())
                },
                modifier = Modifier.padding(8.dp),
                colors = SliderDefaults.colors(
                    thumbColor = Green,
                    activeTrackColor = Green
                )
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
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = viewModel.deviceNameText.value.isNotBlank()
            )
        } else {
            Button(
                onClick = { editScreen.value = !editScreen.value },
                content = {
                    Text("Edit Device")
                },
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}