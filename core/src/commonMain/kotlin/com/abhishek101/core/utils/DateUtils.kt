package com.abhishek101.core.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.toHumanReadableDate(): String {
    val localDate = Instant.fromEpochSeconds(this)
        .toLocalDateTime(TimeZone.currentSystemDefault())

    var dateString = ""

    dateString += "${localDate.year}"

    dateString += when {
        localDate.monthNumber < 9 -> "-0${localDate.monthNumber}"
        else -> "-${localDate.monthNumber}"
    }

    dateString += when {
        localDate.dayOfMonth < 9 -> "-0${localDate.dayOfMonth}"
        else -> "-${localDate.dayOfMonth}"
    }

    return dateString
}