package com.example.hrapp.data.repository

import androidx.compose.runtime.mutableStateListOf
import com.example.hrapp.domain.model.ApprovalStatus
import com.example.hrapp.domain.model.EmployeeLeaveRecord
import com.example.hrapp.domain.model.LeaveApprovalRequest
import com.example.hrapp.domain.model.LeaveBalanceSummary
import com.example.hrapp.domain.model.LeaveType

object MockApprovalRepository {
    private val approvalRequests = mutableStateListOf(
        LeaveApprovalRequest(
            id = 101,
            employeeId = "EMP-1042",
            employeeName = "Haziqah",
            employeeCode = "EMP-1042",
            leaveType = LeaveType.ANNUAL,
            appliedDate = "Mar 23, 2026",
            startDate = "Apr 10, 2026",
            endDate = "Apr 12, 2026",
            totalDays = 3.0,
            purpose = "Family matters and travel arrangements.",
            address = "Johor Bahru, Johor",
            recommendedByName = "A",
            recommendedById = "MGR-219",
            recommendedDate = "Mar 23, 2026",
            leaveBalance = 9,
            status = ApprovalStatus.PENDING,
            notificationBucket = "Today"
        ),
        LeaveApprovalRequest(
            id = 102,
            employeeId = "EMP-1048",
            employeeName = "Aliff",
            employeeCode = "EMP-1048",
            leaveType = LeaveType.EMERGENCY,
            appliedDate = "Mar 23, 2026",
            startDate = "Apr 11, 2026",
            endDate = "Apr 11, 2026",
            totalDays = 1.0,
            purpose = "Urgent family appointment.",
            address = "Shah Alam, Selangor",
            recommendedByName = "Aisyah",
            recommendedById = "MGR-219",
            recommendedDate = "Mar 23, 2026",
            leaveBalance = 4,
            status = ApprovalStatus.PENDING,
            notificationBucket = "Today"
        ),
        LeaveApprovalRequest(
            id = 103,
            employeeId = "EMP-1081",
            employeeName = "Farahin",
            employeeCode = "EMP-1081",
            leaveType = LeaveType.MEDICAL_CLAIM,
            appliedDate = "Mar 22, 2026",
            startDate = "Apr 15, 2026",
            endDate = "Apr 15, 2026",
            totalDays = 0.5,
            purpose = "Follow-up clinic appointment.",
            address = "Kuala Lumpur",
            recommendedByName = "Zulhelmi Harun",
            recommendedById = "MGR-301",
            recommendedDate = "Mar 22, 2026",
            leaveBalance = 6,
            status = ApprovalStatus.PENDING,
            notificationBucket = "Yesterday"
        ),
        LeaveApprovalRequest(
            id = 104,
            employeeId = "EMP-1104",
            employeeName = "Najihah",
            employeeCode = "EMP-1104",
            leaveType = LeaveType.COMPASSIONATE,
            appliedDate = "Mar 22, 2026",
            startDate = "Apr 16, 2026",
            endDate = "Apr 17, 2026",
            totalDays = 2.0,
            purpose = "Attending to an immediate family bereavement.",
            address = "Bangi, Selangor",
            recommendedByName = "Zulhelmi Harun",
            recommendedById = "MGR-301",
            recommendedDate = "Mar 22, 2026",
            leaveBalance = 7,
            status = ApprovalStatus.PENDING,
            notificationBucket = "Yesterday"
        ),
        LeaveApprovalRequest(
            id = 105,
            employeeId = "EMP-1109",
            employeeName = "Linda",
            employeeCode = "EMP-1109",
            leaveType = LeaveType.ANNUAL,
            appliedDate = "Mar 21, 2026",
            startDate = "Apr 20, 2026",
            endDate = "Apr 21, 2026",
            totalDays = 2.0,
            purpose = "Personal leave for a family event.",
            address = "Melaka",
            recommendedByName = "Aisyah Rahim",
            recommendedById = "MGR-219",
            recommendedDate = "Mar 21, 2026",
            leaveBalance = 5,
            status = ApprovalStatus.APPROVED,
            notificationBucket = "Earlier"
        ),
        LeaveApprovalRequest(
            id = 106,
            employeeId = "EMP-1042",
            employeeName = "Haziqah",
            employeeCode = "EMP-1042",
            leaveType = LeaveType.COMP_TIME_CLAIM,
            appliedDate = "Mar 21, 2026",
            startDate = "Apr 18, 2026",
            endDate = "Apr 18, 2026",
            totalDays = 1.0,
            purpose = "Claiming time off after weekend deployment support.",
            address = "Johor Bahru, Johor",
            recommendedByName = "Aisyah Rahim",
            recommendedById = "MGR-219",
            recommendedDate = "Mar 21, 2026",
            leaveBalance = 9,
            status = ApprovalStatus.PENDING,
            notificationBucket = "Earlier"
        ),
        LeaveApprovalRequest(
            id = 107,
            employeeId = "EMP-1115",
            employeeName = "Ainaa",
            employeeCode = "EMP-1115",
            leaveType = LeaveType.COMP_TIME_REGISTER,
            appliedDate = "Mar 20, 2026",
            startDate = "Apr 19, 2026",
            endDate = "Apr 19, 2026",
            totalDays = 1.0,
            purpose = "Registering overtime worked during server maintenance.",
            address = "Cyberjaya, Selangor",
            recommendedByName = "Nur Aina Mohd",
            recommendedById = "MGR-412",
            recommendedDate = "Mar 20, 2026",
            leaveBalance = 8,
            status = ApprovalStatus.PENDING,
            notificationBucket = "Earlier"
        )
    )

    private val leaveBalanceSummaries = listOf(
        LeaveBalanceSummary("EMP-1042", alLeft = 12, wfhMonthlyLeft = 3, mcTaken = 5, elTaken = 1, clTaken = 0, comptimeHour = 8),
        LeaveBalanceSummary("EMP-1048", alLeft = 10, wfhMonthlyLeft = 2, mcTaken = 1, elTaken = 1, clTaken = 0, comptimeHour = 4),
        LeaveBalanceSummary("EMP-1081", alLeft = 9, wfhMonthlyLeft = 1, mcTaken = 4, elTaken = 0, clTaken = 1, comptimeHour = 2),
        LeaveBalanceSummary("EMP-1104", alLeft = 8, wfhMonthlyLeft = 1, mcTaken = 2, elTaken = 1, clTaken = 1, comptimeHour = 5),
        LeaveBalanceSummary("EMP-1109", alLeft = 6, wfhMonthlyLeft = 0, mcTaken = 3, elTaken = 0, clTaken = 0, comptimeHour = 3),
        LeaveBalanceSummary("EMP-1115", alLeft = 11, wfhMonthlyLeft = 2, mcTaken = 0, elTaken = 0, clTaken = 0, comptimeHour = 12)
    )

    private val previousRecords = listOf(
        EmployeeLeaveRecord(201, "EMP-1042", "Mar 01, 2026", "Mar 02, 2026", LeaveType.ANNUAL, ApprovalStatus.APPROVED, "Personal leave"),
        EmployeeLeaveRecord(202, "EMP-1042", "Feb 03, 2026", "Feb 03, 2026", LeaveType.COMP_TIME_CLAIM, ApprovalStatus.APPROVED, "Weekend deployment compensation"),
        EmployeeLeaveRecord(203, "EMP-1042", "Jan 20, 2026", "Jan 22, 2026", LeaveType.ANNUAL, ApprovalStatus.APPROVED, "Family trip"),
        EmployeeLeaveRecord(204, "EMP-1048", "Jan 15, 2026", "Jan 15, 2026", LeaveType.EMERGENCY, ApprovalStatus.APPROVED, "Emergency leave"),
        EmployeeLeaveRecord(205, "EMP-1048", "Feb 12, 2026", "Feb 13, 2026", LeaveType.ANNUAL, ApprovalStatus.REJECTED, "Family event"),
        EmployeeLeaveRecord(206, "EMP-1081", "Feb 05, 2026", "Feb 05, 2026", LeaveType.MEDICAL_CLAIM, ApprovalStatus.APPROVED, "Dental appointment"),
        EmployeeLeaveRecord(207, "EMP-1081", "Mar 10, 2026", "Mar 11, 2026", LeaveType.ANNUAL, ApprovalStatus.APPROVED, "Short leave"),
        EmployeeLeaveRecord(208, "EMP-1104", "Jan 08, 2026", "Jan 09, 2026", LeaveType.COMPASSIONATE, ApprovalStatus.APPROVED, "Family emergency"),
        EmployeeLeaveRecord(209, "EMP-1104", "Feb 14, 2026", "Feb 14, 2026", LeaveType.ANNUAL, ApprovalStatus.APPROVED, "Personal errand"),
        EmployeeLeaveRecord(210, "EMP-1109", "Jan 22, 2026", "Jan 23, 2026", LeaveType.ANNUAL, ApprovalStatus.APPROVED, "Personal leave"),
        EmployeeLeaveRecord(211, "EMP-1115", "Feb 18, 2026", "Feb 18, 2026", LeaveType.COMP_TIME_REGISTER, ApprovalStatus.APPROVED, "Overtime registration")
    )

    val pendingApprovalCount: Int
        get() = approvalRequests.count { it.status == ApprovalStatus.PENDING }

    fun getPendingApprovalRequests(): List<LeaveApprovalRequest> {
        return approvalRequests.filter { it.status == ApprovalStatus.PENDING }
    }

    fun getAllApprovalRequests(): List<LeaveApprovalRequest> {
        return approvalRequests.toList()
    }

    fun getApprovalRequestById(requestId: Int): LeaveApprovalRequest? {
        return approvalRequests.firstOrNull { it.id == requestId }
    }

    fun getPendingApprovalRequests(query: String): List<LeaveApprovalRequest> {
        val normalizedQuery = query.trim().lowercase()
        return approvalRequests
            .filter { it.status == ApprovalStatus.PENDING }
            .filter {
                normalizedQuery.isBlank() ||
                    it.employeeName.lowercase().contains(normalizedQuery) ||
                    it.employeeCode.lowercase().contains(normalizedQuery) ||
                    it.leaveType.displayName.lowercase().contains(normalizedQuery) ||
                    it.purpose.lowercase().contains(normalizedQuery)
            }
            .sortedWith(compareBy<LeaveApprovalRequest> { it.notificationBucket }.thenByDescending { it.id })
    }

    fun getLeaveBalanceSummary(employeeId: String): LeaveBalanceSummary? {
        return leaveBalanceSummaries.firstOrNull { it.employeeId == employeeId }
    }

    fun getEmployeeLeaveRecords(employeeId: String): List<EmployeeLeaveRecord> {
        return previousRecords
            .filter { it.employeeId == employeeId }
            .sortedByDescending { it.id }
    }

    fun approveRequest(requestId: Int) {
        updateRequestStatus(requestId, ApprovalStatus.APPROVED)
    }

    fun rejectRequest(requestId: Int) {
        updateRequestStatus(requestId, ApprovalStatus.REJECTED)
    }

    private fun updateRequestStatus(requestId: Int, status: ApprovalStatus) {
        val index = approvalRequests.indexOfFirst { it.id == requestId }
        if (index == -1) return
        approvalRequests[index] = approvalRequests[index].copy(status = status)
    }
}
