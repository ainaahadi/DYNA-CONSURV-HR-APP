package com.example.hrapp.domain.model

data class LeaveRequest(
    val id: Int,
    val type: String,
    val typeName: String,
    val icon: String,
    val startDate: String,
    val endDate: String,
    val dateDisplay: String,
    val days: Int,
    val reason: String,
    val status: LeaveStatus,
    val appliedDate: String,
    val createdAt: String = appliedDate,
    val employeeName: String = "You",
    val startTime: String? = null,
    val endTime: String? = null,
    val hours: Double? = null,
    val approver: String? = null,
    val rejectionReason: String? = null
) {
    val leaveType: String
        get() = typeName

    val duration: Int
        get() = days

    val purpose: String
        get() = reason
}

enum class LeaveStatus(val label: String, val isHistory: Boolean) {
    PENDING("Pending", false),
    APPROVED("Approved", true),
    REJECTED("Declined", true),
    CANCELLED("Cancelled", true)
}

data class LeaveBalance(
    val type: String,
    val name: String,
    val icon: String,
    val total: Int,
    val used: Int,
    val available: Int
)