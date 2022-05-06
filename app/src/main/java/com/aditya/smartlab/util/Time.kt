package com.aditya.smartlab.util

fun getTimeDifference(current: Long, prev: Long): String {

    val diff = current - prev

    val minutes = diff / (60 * 1000) % 60
    val hours = diff / (60 * 60 * 1000);

    return if (hours == 0L && minutes < 1L) {
        ""
    } else if (hours == 0L) {
        "On for last $minutes min"
    } else {
        "On for last $hours hrs"
    }
}