package org.example.app.data

/**
 * PUBLIC_INTERFACE
 * Event data model matching backend API.
 */
data class Event(
    val id: String? = null,
    val title: String,
    val description: String? = null,
    val startTime: String, // ISO-8601 string
    val endTime: String,   // ISO-8601 string
    val allDay: Boolean = false,
    val location: String? = null,
    val colorTag: String? = null
)
