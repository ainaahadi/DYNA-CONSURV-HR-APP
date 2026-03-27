package com.example.hrapp.domain.model

import androidx.compose.runtime.mutableStateListOf

object MockLeaveData {
    val requests = mutableStateListOf(
        LeaveRequest(
            id = 1,
            type = "annual",
            typeName = "Annual Leave",
            icon = "🌴",
            startDate = "2026-01-20",
            endDate = "2026-01-22",
            dateDisplay = "Jan 20 - Jan 22, 2026",
            days = 3,
            reason = "Full Day (3 days)",
            status = LeaveStatus.PENDING,
            appliedDate = "2026-01-15"
        ),
        LeaveRequest(
            id = 2,
            type = "wfh",
            typeName = "WFH",
            icon = "🏠",
            startDate = "2026-01-12",
            endDate = "2026-01-12",
            dateDisplay = "Jan 12, 2026",
            days = 1,
            reason = "Full Day (1 day)",
            status = LeaveStatus.APPROVED,
            appliedDate = "2026-01-10"
        ),
        LeaveRequest(
            id = 3,
            type = "mc",
            typeName = "Medical Certificate",
            icon = "🤒",
            startDate = "2026-01-05",
            endDate = "2026-01-05",
            dateDisplay = "Jan 05, 2026",
            days = 1,
            reason = "Morning (0.5 days)",
            status = LeaveStatus.APPROVED,
            appliedDate = "2025-12-30"
        ),
        LeaveRequest(
            id = 4,
            type = "el",
            typeName = "Emergency Leave",
            icon = "⚠️",
            startDate = "2025-12-15",
            endDate = "2025-12-15",
            dateDisplay = "Dec 15, 2025",
            days = 1,
            reason = "Evening (0.5 days)",
            status = LeaveStatus.REJECTED,
            appliedDate = "2025-12-14"
        )
    )

    fun addRequest(request: LeaveRequest) {
        // Add to the beginning of the list
        requests.add(0, request)
    }
}
