package com.example.hrapp.data.repository

import com.example.hrapp.domain.model.CalendarAgendaEntry
import com.example.hrapp.domain.model.CalendarDaySection
import com.example.hrapp.domain.model.CalendarEvent
import com.example.hrapp.domain.model.CalendarEventType
import com.example.hrapp.domain.model.LeaveRequest
import com.example.hrapp.domain.model.LeaveStatus
import com.example.hrapp.domain.model.MockLeaveData
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.daysUntil
import kotlinx.datetime.minus
import kotlinx.datetime.plus

object MockCalendarRepository : CalendarRepository {
    private val specialEvents = listOf(
        CalendarEvent(
            id = "event-1",
            eventType = CalendarEventType.HOLIDAY,
            title = "Cuti Raya",
            date = LocalDate(2026, 3, 23),
            startDate = LocalDate(2026, 3, 23),
            endDate = LocalDate(2026, 3, 23),
            isHoliday = true,
            description = "Public holiday"
        ),
        CalendarEvent(
            id = "event-2",
            eventType = CalendarEventType.GENERAL_EVENT,
            title = "Project Kickoff Meeting",
            date = LocalDate(2026, 3, 28),
            startDate = LocalDate(2026, 3, 28),
            endDate = LocalDate(2026, 3, 28),
            startTime = "09:00",
            endTime = "11:00",
            isAllDay = false,
            description = "Project alignment meeting and task review."
        ),
        CalendarEvent(
            id = "event-3",
            eventType = CalendarEventType.HOLIDAY,
            title = "Labour Day Observed",
            date = LocalDate(2026, 5, 1),
            startDate = LocalDate(2026, 5, 1),
            endDate = LocalDate(2026, 5, 1),
            isHoliday = true,
            description = "Company holiday"
        )
    )

    override fun getCalendarEvents(): List<CalendarEvent> =
        (leaveRequestEvents() + specialEvents)
            .sortedWith(compareBy<CalendarEvent> { it.date }.thenBy { it.employeeName ?: it.title })

    override fun getAgendaForMonth(monthAnchor: LocalDate): List<CalendarDaySection> {
        val monthStart = LocalDate(monthAnchor.year, monthAnchor.month, 1)
        val monthEnd = monthStart.plus(DatePeriod(months = 1)).minus(DatePeriod(days = 1))

        return getCalendarEvents()
            .flatMap { it.expandForMonth(monthStart, monthEnd) }
            .sortedWith(
                compareBy<CalendarAgendaEntry> { it.date }
                    .thenBy { it.eventType.sortPriority() }
                    .thenBy { it.startTime ?: "23:59" }
                    .thenBy { it.employeeName ?: it.title }
            )
            .groupBy { it.date }
            .toSortedMap()
            .map { (date, entries) -> CalendarDaySection(date = date, entries = entries) }
    }

    override fun getCalendarEventById(eventId: String): CalendarEvent? =
        getCalendarEvents().firstOrNull { it.id == eventId }

    private fun leaveRequestEvents(): List<CalendarEvent> {
        return MockLeaveData.allLeaves
            .filter { it.status == LeaveStatus.APPROVED || it.status == LeaveStatus.PENDING }
            .map { request -> request.toCalendarEvent() }
    }

    private fun LeaveRequest.toCalendarEvent(): CalendarEvent {
        val start = LocalDate.parse(startDate)
        val end = LocalDate.parse(endDate)
        return CalendarEvent(
            id = "leave-$id",
            employeeName = employeeName,
            eventType = type.toCalendarEventType(),
            title = typeName,
            date = start,
            startDate = start,
            endDate = end,
            startTime = startTime,
            endTime = endTime,
            hours = hours,
            isAllDay = startTime == null && endTime == null,
            reason = reason
        )
    }

    private fun String.toCalendarEventType(): CalendarEventType = when (this) {
        "annual" -> CalendarEventType.ANNUAL_LEAVE
        "wfh" -> CalendarEventType.WORK_FROM_HOME
        "mc" -> CalendarEventType.MEDICAL_CLAIM
        "el" -> CalendarEventType.EMERGENCY_LEAVE
        "compassionate" -> CalendarEventType.COMPASSIONATE_LEAVE
        "comp_time_register" -> CalendarEventType.COMP_TIME_REGISTER
        "comp_time_claim" -> CalendarEventType.COMP_TIME_CLAIM
        else -> CalendarEventType.GENERAL_EVENT
    }

    private fun CalendarEvent.expandForMonth(
        monthStart: LocalDate,
        monthEnd: LocalDate
    ): List<CalendarAgendaEntry> {
        val rangeStart = startDate ?: date
        val rangeEnd = endDate ?: date

        if (rangeEnd < monthStart || rangeStart > monthEnd) {
            return emptyList()
        }

        val visibleStart = if (rangeStart < monthStart) monthStart else rangeStart
        val visibleEnd = if (rangeEnd > monthEnd) monthEnd else rangeEnd
        val totalDays = rangeStart.daysUntil(rangeEnd) + 1

        val entries = mutableListOf<CalendarAgendaEntry>()
        var cursor = visibleStart
        while (cursor <= visibleEnd) {
            val progressDay = if (totalDays > 1 && !isHoliday) rangeStart.daysUntil(cursor) + 1 else leaveProgressDay
            val progressTotal = if (totalDays > 1 && !isHoliday) totalDays else leaveProgressTotal

            entries += CalendarAgendaEntry(
                id = "$id-${cursor}",
                sourceEventId = id,
                date = cursor,
                employeeId = employeeId,
                employeeName = employeeName,
                eventType = eventType,
                title = title,
                startTime = if (cursor == date) startTime else null,
                endTime = if (cursor == date) endTime else null,
                hours = if (cursor == date) hours else null,
                leaveProgressDay = progressDay,
                leaveProgressTotal = progressTotal,
                isAllDay = isAllDay,
                isHoliday = isHoliday,
                description = description
            )

            cursor = cursor.plus(DatePeriod(days = 1))
        }

        return entries
    }

    private fun CalendarEventType.sortPriority(): Int = when (this) {
        CalendarEventType.HOLIDAY -> 0
        CalendarEventType.ANNUAL_LEAVE -> 1
        CalendarEventType.WORK_FROM_HOME -> 2
        CalendarEventType.EMERGENCY_LEAVE -> 3
        CalendarEventType.MEDICAL_CLAIM -> 4
        CalendarEventType.COMPASSIONATE_LEAVE -> 5
        CalendarEventType.GENERAL_EVENT -> 6
        CalendarEventType.COMP_TIME_REGISTER, CalendarEventType.COMP_TIME_CLAIM -> 7
    }
}