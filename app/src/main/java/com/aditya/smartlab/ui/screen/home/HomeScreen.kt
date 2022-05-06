package com.aditya.smartlab.ui.screen.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aditya.smartlab.data.models.Device
import com.aditya.smartlab.ui.theme.DarkGray
import com.aditya.smartlab.util.getTimeDifference

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navigateTo: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(DarkGray)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Smart Lab",
                fontSize = 32.sp,
                color = Color.White
            )
        }
        Spacer(Modifier.height(25.dp))
        LazyColumn {
            item {
                RoomCard(
                    name = "306-A",
                    devices = viewModel.devicesA.value,
                    navigateTo = { navigateTo(it) },
                    onDeviceStatusChange = { id, status ->
                        viewModel.updateDevice(id,status)
                    }
                )
            }
            item {
                Spacer(Modifier.height(18.dp))
            }
            item {
                RoomCard(
                    name = "306-B",
                    devices = viewModel.devicesB.value,
                    navigateTo = { navigateTo(it) },
                    onDeviceStatusChange = { id, status ->
                        viewModel.updateDevice(id,status)
                    }
                )
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RoomCard(
    name: String,
    devices: List<Device>,
    navigateTo: (Int) -> Unit,
    onDeviceStatusChange: (Int, Int) -> Unit
) {
    val state = remember { mutableStateOf(-1) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    listOf(Color.Blue.copy(alpha = 0.7f), Color.Magenta.copy(alpha = 0.7f))
                ),
                shape = RoundedCornerShape(10.dp)
            )
            .animateContentSize(
                animationSpec = spring(
                    stiffness = Spring.StiffnessMediumLow
                )
            ),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = name,
            color = Color.White,
            fontSize = 28.sp,
            modifier = Modifier.padding(12.dp)
        )
        val deviceTypes = listOf("Fans", "Lights", "ACs")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            deviceTypes.forEachIndexed { index, type ->
                DeviceRoundButton(
                    label = type,
                    onClick = {
                        if (index == state.value) {
                            state.value = -1
                        } else {
                            state.value = index
                        }
                    },
                    selected = index == state.value
                )
            }
        }
        if (state.value == 0) {
            DevicesRegion(
                devices = devices.subList(0, 6),
                onClick = { navigateTo(it) },
                onDeviceStatusChange = { id, status ->
                    onDeviceStatusChange(id,status)
                }
            )
        }

        if (state.value == 1) {
            DevicesRegion(
                devices = devices.subList(6, 12),
                onClick = { navigateTo(it) },
                onDeviceStatusChange = { id, status ->
                    onDeviceStatusChange(id,status)
                }
            )
        }

        if (state.value == 2) {
            DevicesRegion(
                devices = devices.subList(12, 14),
                onClick = { navigateTo(it) },
                onDeviceStatusChange = { id, status ->
                    onDeviceStatusChange(id,status)
                }
            )
        }

        Spacer(Modifier.height(15.dp))
    }
}

@Composable
private fun DeviceRoundButton(
    label: String,
    onClick: () -> Unit,
    selected: Boolean
) {
    Column(
        modifier = Modifier.padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        IconButton(
            onClick = { onClick() },
            content = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            modifier = Modifier
                .background(
                    color = Color.Yellow,
                    shape = RoundedCornerShape(50),
                )
                .size(60.dp)
        )
        Text(
            text = label,
            fontSize = 16.sp,
            color = Color.White,
            modifier = Modifier.padding(12.dp)
        )
        Box(
            modifier = Modifier
                .background(Color.White)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                )
                .width(if (selected) 60.dp else 0.dp)
                .height(5.dp)
        )
    }
}

@Composable
private fun DevicesRegion(
    devices: List<Device>,
    onClick: (Int) -> Unit,
    onDeviceStatusChange: (Int, Int) -> Unit
) {

    devices.chunked(2).forEach {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            it.forEach { device ->
                DeviceCard(
                    device = device,
                    onClick = { onClick(device.id) },
                    onDeviceStatusChange = { id, status ->
                        onDeviceStatusChange(id,status)
                    }
                )
            }
        }
    }
}

@Composable
private fun DeviceCard(
    device: Device,
    onClick: () -> Unit,
    onDeviceStatusChange: (Int, Int) -> Unit
) {
    Row(
        modifier = Modifier
            .width(150.dp)
            .height(100.dp)
            .padding(8.dp)
            .background(Color.Yellow, RoundedCornerShape(10.dp))
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(device.name)
            if (device.status != 0) {
                Text(
                    text = getTimeDifference(System.currentTimeMillis(), device.lastOnTime),
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
        Switch(
            checked = device.status != 0,
            onCheckedChange = {
                onDeviceStatusChange(
                    device.id, if (device.status == 0) 100 else 0
                )
            }
        )
    }
}
