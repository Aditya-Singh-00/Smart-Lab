package com.aditya.smartlab.ui.screen.home

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.aditya.smartlab.R
import com.aditya.smartlab.data.models.Device
import com.aditya.smartlab.ui.theme.DarkGray
import com.aditya.smartlab.ui.theme.White
import com.aditya.smartlab.util.getTimeDifference

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    navigateTo: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Smart Lab",
                style = MaterialTheme.typography.h5
            )
        }
        Spacer(Modifier.height(25.dp))
        LazyColumn {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(8.dp),
                    shape = MaterialTheme.shapes.large,
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    elevation = 5.dp
                ) {
                    Box(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Text(
                            text = "Room temperature is ${viewModel.roomTemperature.value.status}\u00B0C",
                            modifier = Modifier.align(Alignment.Center),
                            style = MaterialTheme.typography.h6,
                            color = White
                        )
                    }
                }
            }
            item {
                Spacer(Modifier.height(18.dp))
            }
            item {
                RoomCard(
                    name = "306-A",
                    devices = viewModel.devicesA.value,
                    navigateTo = { navigateTo(it) },
                    onDeviceStatusChange = { id, status ->
                        viewModel.updateDevice(id, status)
                    }
                )
            }
            item {
                Spacer(Modifier.height(8.dp))
            }
            item {
                RoomCard(
                    name = "306-B",
                    devices = viewModel.devicesB.value,
                    navigateTo = { navigateTo(it) },
                    onDeviceStatusChange = { id, status ->
                        viewModel.updateDevice(id, status)
                    }
                )
            }
        }
    }
}

@Composable
private fun RoomCard(
    name: String,
    devices: List<Device>,
    navigateTo: (Int) -> Unit,
    onDeviceStatusChange: (Int, Int) -> Unit
) {

    val state = remember { mutableStateOf(-1) }
    val deviceTypes = listOf("Fans", "Lights", "ACs")
    val iconList = listOf(
        painterResource(R.drawable.ic_ceiling_fan),
        painterResource(R.drawable.ic_light),
        painterResource(R.drawable.ic_ac)
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
            .padding(8.dp),
        shape = MaterialTheme.shapes.large,
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 5.dp
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = name,
                modifier = Modifier.padding(12.dp),
                style = MaterialTheme.typography.h5,
                color = MaterialTheme.colors.onBackground
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                deviceTypes.forEachIndexed { index, type ->
                    DeviceRoundButton(
                        label = type,
                        icon = iconList[index],
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
            if (devices.isNotEmpty()) {
                if (state.value == 0) {
                    DevicesRegion(
                        devices = devices.subList(0, 6),
                        onClick = { navigateTo(it) },
                        onDeviceStatusChange = { id, status ->
                            onDeviceStatusChange(id, status)
                        }
                    )
                }

                if (state.value == 1) {
                    DevicesRegion(
                        devices = devices.subList(6, 12),
                        onClick = { navigateTo(it) },
                        onDeviceStatusChange = { id, status ->
                            onDeviceStatusChange(id, status)
                        }
                    )
                }

                if (state.value == 2) {
                    DevicesRegion(
                        devices = devices.subList(12, 14),
                        onClick = { navigateTo(it) },
                        onDeviceStatusChange = { id, status ->
                            onDeviceStatusChange(id, status)
                        }
                    )
                }
            }
            Spacer(Modifier.height(15.dp))
        }
    }
}

@Composable
private fun DeviceRoundButton(
    label: String,
    icon: Painter,
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
                    painter = icon,
                    contentDescription = label
                )
            },
            modifier = Modifier
                .background(
                    color = MaterialTheme.colors.primaryVariant,
                    shape = CircleShape,
                )
                .padding(8.dp)
                .size(50.dp)
        )
        Text(
            text = label,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(12.dp),
            color = MaterialTheme.colors.onBackground.copy(alpha = 0.7f)
        )
        Box(
            modifier = Modifier
                .background(MaterialTheme.colors.primary)
                .animateContentSize(
                    animationSpec = tween(
                        durationMillis = 300,
                        easing = LinearOutSlowInEasing
                    )
                )
                .width(if (selected) 60.dp else 0.dp)
                .height(1.dp)
        )
    }
}

@Composable
private fun DevicesRegion(
    devices: List<Device>,
    onClick: (Int) -> Unit,
    onDeviceStatusChange: (Int, Int) -> Unit
) {

    if (devices.isNotEmpty()) {
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
                            onDeviceStatusChange(id, status)
                        }
                    )
                }
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
            .background(
                color = MaterialTheme.colors.background,
                shape = MaterialTheme.shapes.medium
            )
            .clickable { onClick() },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .padding(16.dp),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = device.name,
                style = MaterialTheme.typography.body1,
                color = MaterialTheme.colors.onBackground
            )
            if (device.status != 0) {
                val text = getTimeDifference(System.currentTimeMillis(), device.lastOnTime)
                if (text.isNotBlank()) {
                    Text(
                        text = text,
                        style = MaterialTheme.typography.caption,
                        color = MaterialTheme.colors.onBackground.copy(alpha = 0.5f)
                    )
                }
            }
        }
        Switch(
            checked = device.status != 0,
            onCheckedChange = {
                onDeviceStatusChange(
                    device.id, if (device.status == 0) 100 else 0
                )
            },
            colors = SwitchDefaults.colors(
                uncheckedThumbColor = DarkGray,
                uncheckedTrackColor = DarkGray
            )
        )
    }
}
