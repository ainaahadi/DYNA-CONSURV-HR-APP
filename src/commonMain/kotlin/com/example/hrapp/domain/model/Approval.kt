package com.example.hrapp.domain.model

enum class ApprovalStatus {
    PENDING, APPROVED, REJECTED, CANCELLED
}

enum class LeaveType(val displayName: String) {
    ANNUAL("Annual Leave"),
    MEDICAL_CLAIM("Medical Claim"),
    EMERGENCY("Emergency Leave"),
    COMPASSIONATE("Compassionate Leave"),
    COMP_TIME_CLAIM("Comp Time (Claim)"),
    COMP_TIME_REGISTER("Comp Time (Register)");

    val standardizedDisplayName: String
        get() = displayName
}

data class LeaveApprovalRequest(
    val id: Int,
    val employeeId: String,
    val employeeName: String,
    val employeeCode: String,
    val employeeAvatar: String? = null,
    val leaveType: LeaveType,
    val appliedDate: String,
    val startDate: String,
    val endDate: String,
    val totalDays: Double,
    val purpose: String,
    val address: String = "",
    val recommendedByName: String = "",
    val recommendedById: String = "",
    val recommendedDate: String = "",
    val leaveBalance: Int,
    val status: ApprovalStatus,
    val notificationBucket: String = "Today"
) {
    val leaveDateDisplay: String
        get() = if (startDate == endDate) startDate else "$startDate - $endDate"
}

data class EmployeeLeaveRecord(
    val id: Int,
    val employeeId: String,
    val startDate: String,
    val endDate: String,
    val leaveType: LeaveType,
    val status: ApprovalStatus,
    val subtitle: String? = null
) {
    val leaveDateDisplay: String
        get() = if (startDate == endDate) startDate else "$startDate - $endDate"
}

data class LeaveBalanceSummary(
    val employeeId: String,
    val alLeft: Int,
    val wfhMonthlyLeft: Int,
    val mcTaken: Int,
    val elTaken: Int,
    val clTaken: Int,
    val comptimeHour: Int
)
