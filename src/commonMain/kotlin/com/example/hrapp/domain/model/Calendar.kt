package com.example.hrapp.domain.model

import kotlinx.datetime.LocalDate

enum class CalendarEventType(
    val displayName: String,
    val shortLabel: String
) {
    ANNUAL_LEAVE("Annual Leave", "AL"),
    EMERGENCY_LEAVE("Emergency Leave", "EL"),
    MEDICAL_CLAIM("Medical Claim", "MC"),
    WORK_FROM_HOME("Work From Home", "WFH"),
    COMPASSIONATE_LEAVE("Compassionate Leave", "CL"),
    COMP_TIME_REGISTER("Comp Time (Register)", "Comp Time (Register)"),
    COMP_TIME_CLAIM("Comp Time (Claim)", "Comp Time (Claim)"),
    GENERAL_EVENT("General Event", "Event"),
    HOLIDAY("Holiday", "Holiday")
}

data class CalendarEvent(
    val id: String,
    val employeeId: String? = null,
    val employeeName: String? = null,
    val eventType: CalendarEventType,
    val title: String,
    val date: LocalDate,
    val startDate: LocalDate? = null,
    val endDate: LocalDate? = null,
    val startTime: String? = null,
    val endTime: String? = null,
    val hours: Double? = null,
    val leaveProgressDay: Int? = null,
    val leaveProgressTotal: Int? = null,
    val isAllDay: Boolean = true,
    val isHoliday: Boolean = false,
    val reason: String? = null,
    val description: String? = null
)

data class CalendarAgendaEntry(
    val id: String,
    val sourceEventId: String,
    val date: LocalDate,
    val employeeId: String? = null,
    val employeeName: String? = null,
    val eventType: CalendarEventType,
    val title: String,
    val startTime: String? = null,
    val endTime: String? = null,
    val hours: Double? = null,
    val leaveProgressDay: Int? = null,
    val leaveProgressTotal: Int? = null,
    val isAllDay: Boolean = true,
    val isHoliday: Boolean = false,
    val description: String? = null
)

data class CalendarDaySection(
    val date: LocalDate,
    val entries: List<CalendarAgendaEntry>
)