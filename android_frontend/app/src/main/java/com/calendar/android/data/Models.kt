package com.calendar.android.data

import com.squareup.moshi.Json

data class EventRequest(
    val title: String,
    val description: String? = null,
    @Json(name = "startTime") val startTime: String,
    @Json(name = "endTime") val endTime: String,
    val location: String? = null,
    @Json(name = "allDay") val allDay: Boolean = false,
    @Json(name = "recurrenceRule") val recurrenceRule: String? = null
)

data class EventResponse(
    val id: String,
    val title: String,
    val description: String?,
    @Json(name = "startTime") val startTime: String,
    @Json(name = "endTime") val endTime: String,
    val location: String?,
    @Json(name = "allDay") val allDay: Boolean,
    @Json(name = "recurrenceRule") val recurrenceRule: String?,
    @Json(name = "createdAt") val createdAt: String,
    @Json(name = "updatedAt") val updatedAt: String
)
