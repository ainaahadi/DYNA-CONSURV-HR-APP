package com.example.hrapp.domain.model

import androidx.compose.runtime.mutableStateListOf

object MockLeaveData {
    val requests = mutableStateListOf(
        LeaveRequest(
            id = 1,
            type = "annual",
            typeName = "Annual Leave",
            icon = "annual",
            startDate = "2026-03-18",
            endDate = "2026-03-27",
            dateDisplay = "Mar 18 - Mar 27, 2026",
            days = 10,
            reason = "Family trip and school holiday planning.",
            status = LeaveStatus.APPROVED,
            appliedDate = "2026-03-10",
            createdAt = "2026-03-10",
            employeeName = "Linda"
        ),
        LeaveRequest(
            id = 2,
            type = "annual",
            typeName = "Annual Leave",
            icon = "annual",
            startDate = "2026-03-20",
            endDate = "2026-03-27",
            dateDisplay = "Mar 20 - Mar 27, 2026",
            days = 8,
            reason = "Outstation leave request.",
            status = LeaveStatus.APPROVED,
            appliedDate = "2026-03-13",
            createdAt = "2026-03-13",
            employeeName = "Zakwan"
        ),
        LeaveRequest(
            id = 3,
            type = "el",
            typeName = "Emergency Leave",
            icon = "el",
            startDate = "2026-03-26",
            endDate = "2026-03-27",
            dateDisplay = "Mar 26 - Mar 27, 2026",
            days = 2,
            reason = "Urgent family appointment.",
            status = LeaveStatus.APPROVED,
            appliedDate = "2026-03-24",
            createdAt = "2026-03-24",
            employeeName = "Haziqah"
        ),
        LeaveRequest(
            id = 4,
            type = "mc",
            typeName = "Medical Claim",
            icon = "mc",
            startDate = "2026-03-24",
            endDate = "2026-03-24",
            dateDisplay = "Mar 24, 2026",
            days = 1,
            reason = "Clinic follow-up appointment.",
            status = LeaveStatus.APPROVED,
            appliedDate = "2026-03-22",
            createdAt = "2026-03-22",
            employeeName = "Farahin"
        ),
        LeaveRequest(
            id = 5,
            type = "wfh",
            typeName = "WFH",
            icon = "wfh",
            startDate = "2026-03-25",
            endDate = "2026-03-25",
            dateDisplay = "Mar 25, 2026",
            days = 1,
            reason = "Remote work for home maintenance.",
            status = LeaveStatus.APPROVED,
            appliedDate = "2026-03-23",
            createdAt = "2026-03-23",
            employeeName = "Najihah"
        ),
        LeaveRequest(
            id = 6,
            type = "comp_time_register",
            typeName = "Comp Time (Register)",
            icon = "comp_time_register",
            startDate = "2026-03-22",
            endDate = "2026-03-22",
            dateDisplay = "Mar 22, 2026",
            days = 1,
            reason = "Task module development.",
            status = LeaveStatus.APPROVED,
            appliedDate = "2026-03-21",
            createdAt = "2026-03-21",
            employeeName = "Ainaa",
            startTime = "13:00",
            endTime = "17:00",
            hours = 1.5
        ),
        LeaveRequest(
            id = 7,
            type = "comp_time_claim",
            typeName = "Comp Time (Claim)",
            icon = "comp_time_claim",
            startDate = "2026-03-27",
            endDate = "2026-03-27",
            dateDisplay = "Mar 27, 2026",
            days = 1,
            reason = "Overtime claim for client escalation.",
            status = LeaveStatus.APPROVED,
            appliedDate = "2026-03-26",
            createdAt = "2026-03-26",
            employeeName = "Aliff",
            startTime = "18:30",
            endTime = "20:30",
            hours = 2.0
        ),
        LeaveRequest(
            id = 8,
            type = "annual",
            typeName = "Annual Leave",
            icon = "annual",
            startDate = "2026-04-06",
            endDate = "2026-04-10",
            dateDisplay = "Apr 06 - Apr 10, 2026",
            days = 5,
            reason = "Long weekend leave block.",
            status = LeaveStatus.PENDING,
            appliedDate = "2026-03-28",
            createdAt = "2026-03-28",
            employeeName = "Nurul"
        ),
        LeaveRequest(
            id = 9,
            type = "compassionate",
            typeName = "Compassionate Leave",
            icon = "compassionate",
            startDate = "2026-02-14",
            endDate = "2026-02-14",
            dateDisplay = "Feb 14, 2026",
            days = 1,
            reason = "Family bereavement.",
            status = LeaveStatus.REJECTED,
            appliedDate = "2026-02-13",
            createdAt = "2026-02-13",
            employeeName = "Nur Aisyah"
        ),
        LeaveRequest(
            id = 10,
            type = "annual",
            typeName = "Annual Leave",
            icon = "annual",
            startDate = "2026-02-02",
            endDate = "2026-02-03",
            dateDisplay = "Feb 02 - Feb 03, 2026",
            days = 2,
            reason = "Cancelled due to project reschedule.",
            status = LeaveStatus.CANCELLED,
            appliedDate = "2026-01-28",
            createdAt = "2026-01-28",
            employeeName = "Aminuddin"
        )
    )

    val allLeaves: List<LeaveRequest>
        get() = requests
            .toList()
            .sortedWith(compareByDescending<LeaveRequest> { it.createdAt }.thenByDescending { it.id })

    val leaveHistoryList: List<LeaveRequest>
        get() = allLeaves.filter { it.status.isHistory }

    val leaveRequestList: List<LeaveRequest>
        get() = allLeaves.filter { it.status == LeaveStatus.PENDING }

    fun addRequest(request: LeaveRequest) {
        requests.add(0, request)
    }

    fun updateRequestStatus(
        requestId: Int,
        status: LeaveStatus,
        approver: String? = null,
        rejectionReason: String? = null
    ) {
        val index = requests.indexOfFirst { it.id == requestId }
        if (index == -1) return

        requests[index] = requests[index].copy(
            status = status,
            approver = approver ?: requests[index].approver,
            rejectionReason = rejectionReason
        )
    }

    fun cancelRequest(requestId: Int, note: String? = null) {
        updateRequestStatus(
            requestId = requestId,
            status = LeaveStatus.CANCELLED,
            rejectionReason = note ?: "Request cancelled"
        )
    }
}