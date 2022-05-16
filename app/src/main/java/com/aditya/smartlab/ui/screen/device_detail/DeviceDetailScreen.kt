package com.aditya.smartlab.ui.screen.device_detail

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aditya.smartlab.data.models.DeviceType
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
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = MaterialTheme.shapes.large,
            backgroundColor = MaterialTheme.colors.surface,
            elevation = 5.dp
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    if (editScreen.value) {
                        OutlinedTextField(
                            value = viewModel.deviceNameText.value,
                            onValueChange = {
                                viewModel.updateDeviceNameText(it)
                            },
                            modifier = Modifier
                                .fillMaxWidth(0.70f)
                                .padding(8.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                textColor = MaterialTheme.colors.onBackground,
                                unfocusedBorderColor = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                            )
                        )
                    } else {
                        Text(
                            text = device.name,
                            modifier = Modifier.align(Alignment.CenterVertically),
                            style = MaterialTheme.typography.h6,
                            color = MaterialTheme.colors.onBackground
                        )
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
                if (device.status != 0) {
                    Text(
                        text = getTimeDifference(System.currentTimeMillis(), device.lastOnTime),
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                    )
                }
            }
        }

        if (device.type == DeviceType.FAN) {
            FanSpeedCard(
                value = device.status,
                onClick = { viewModel.updateDeviceStatus(it) }
            )
        }
        if (editScreen.value) {
            Button(
                onClick = {
                    editScreen.value = !editScreen.value
                    viewModel.updateDeviceName()
                },
                content = {
                    Text(
                        text = "Save Changes",
                        style = MaterialTheme.typography.button
                    )
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                enabled = viewModel.deviceNameText.value.isNotBlank(),
                shape = MaterialTheme.shapes.medium
            )
        } else {
            Button(
                onClick = { editScreen.value = !editScreen.value },
                content = {
                    Text(
                        text = "Edit Device",
                        style = MaterialTheme.typography.button
                    )
                },
                modifier = Modifier.align(Alignment.CenterHorizontally),
                shape = MaterialTheme.shapes.medium
            )
        }
    }
}

@Composable
private fun FanSpeedCard(
    value: Int,
    onClick: (Int) -> Unit
) {
    val selected = remember { mutableStateOf(value / 20) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Fan Speed"
        )
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            repeat(5) {
                Card(
                    modifier = Modifier,
                    shape = CircleShape,
                    backgroundColor = if (selected.value == it + 1) {
                        MaterialTheme.colors.primary
                    } else {
                        MaterialTheme.colors.surface
                    },
                    elevation = 5.dp
                ) {
                    IconButton(
                        onClick = {
                            onClick((it + 1) * 20)
                            selected.value = it + 1
                        },
                        content = {
                            Text(
                                text = (it + 1).toString(),
                                color = if (selected.value == it + 1) {
                                    MaterialTheme.colors.onPrimary
                                } else {
                                    MaterialTheme.colors.onBackground
                                }
                            )
                        },
                        modifier = Modifier
                    )
                }
            }
        }
    }
}