package com.example.hrapp.domain.model

data class Attendance(
    val id: String,
    val date: String,
    val dateFormatted: String,
    val checkIn: String?,
    val checkOut: String?,
    val duration: String,
    val status: String,
    val location: String? = null
)

data class AttendanceSummary(
    val month: String,
    val totalWorkDays: Int,
    val presentDays: Int,
    val absentDays: Int,
    val halfDays: Int,
    val totalHours: Int
)
