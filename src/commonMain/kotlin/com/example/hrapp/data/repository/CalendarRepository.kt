package com.example.hrapp.data.repository

import com.example.hrapp.domain.model.CalendarDaySection
import com.example.hrapp.domain.model.CalendarEvent
import kotlinx.datetime.LocalDate

interface CalendarRepository {
    fun getCalendarEvents(): List<CalendarEvent>

    fun getAgendaForMonth(monthAnchor: LocalDate): List<CalendarDaySection>

    fun getCalendarEventById(eventId: String): CalendarEvent?
}