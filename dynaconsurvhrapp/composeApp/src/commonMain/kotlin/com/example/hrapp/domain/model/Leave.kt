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
    val approver: String? = null,
    val rejectionReason: String? = null
)

enum class LeaveStatus {
    PENDING, APPROVED, REJECTED
}

data class LeaveBalance(
    val type: String,
    val name: String,
    val icon: String,
    val total: Int,
    val used: Int,
    val available: Int
)
