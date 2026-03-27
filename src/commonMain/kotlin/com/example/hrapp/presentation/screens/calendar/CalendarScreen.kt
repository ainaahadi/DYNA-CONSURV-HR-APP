package com.example.hrapp.presentation.screens.calendar

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.hrapp.data.repository.CalendarRepository
import com.example.hrapp.data.repository.MockCalendarRepository
import com.example.hrapp.domain.model.CalendarEvent
import com.example.hrapp.domain.model.CalendarAgendaEntry
import com.example.hrapp.domain.model.CalendarDaySection
import com.example.hrapp.domain.model.CalendarEventType
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.DayOfWeek
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

private val CalendarBackground = Color(0xFFF5F7FD)
private val CalendarBorder = Color(0xFFDCE3F0)
private val CalendarMuted = Color(0xFF7D8699)
private val CalendarSelectedTone = Color(0xFFE5EDFF)
private val CalendarTodayTone = Color(0xFFE8F5E9)
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun CalendarScreen(onBack: () -> Unit) {
    val repository: CalendarRepository = remember { MockCalendarRepository }
    val today = remember { Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date }
    var visibleMonth by remember { mutableStateOf(today.startOfMonth()) }
    var selectedDate by remember { mutableStateOf(today) }
    var showDatePicker by remember { mutableStateOf(false) }
    var selectedCalendarEvent by remember { mutableStateOf<CalendarEvent?>(null) }
    val listState = rememberLazyListState()

    val monthSections = remember(visibleMonth) {
        repository.getAgendaForMonth(visibleMonth)
    }
    val monthEventCount = remember(monthSections) { monthSections.sumOf { it.entries.size } }
    val eventTypesByDate = remember(monthSections) {
        monthSections.associate { section ->
            section.date to section.entries.map { it.eventType }.distinct()
        }
    }

    LaunchedEffect(selectedDate, monthSections) {
        val targetIndex = monthSections.indexOfFirst { it.date == selectedDate }
        if (targetIndex >= 0) {
            listState.animateScrollToItem(targetIndex)
        }
    }

    if (showDatePicker) {
        CalendarPickerDialog(
            monthAnchor = visibleMonth,
            selectedDate = selectedDate,
            today = today,
            eventTypesByDate = eventTypesByDate,
            onDismiss = { showDatePicker = false },
            onPreviousMonth = {
                val newMonth = visibleMonth.plus(DatePeriod(months = -1)).startOfMonth()
                visibleMonth = newMonth
                selectedDate = selectedDate.alignToMonth(newMonth)
            },
            onNextMonth = {
                val newMonth = visibleMonth.plus(DatePeriod(months = 1)).startOfMonth()
                visibleMonth = newMonth
                selectedDate = selectedDate.alignToMonth(newMonth)
            },
            onSelectDate = { date ->
                visibleMonth = date.startOfMonth()
                selectedDate = date
                showDatePicker = false
            }
        )
    }

    selectedCalendarEvent?.let { event ->
        androidx.compose.material3.AlertDialog(
            onDismissRequest = { selectedCalendarEvent = null },
            title = { Text(event.detailTitle(), fontWeight = FontWeight.Bold) },
            text = { Text(event.detailBodyText(), style = MaterialTheme.typography.bodyMedium) },
            confirmButton = { TextButton(onClick = { selectedCalendarEvent = null }) { Text("Close") } }
        )
    }

    Scaffold(
        containerColor = CalendarBackground,
        topBar = {
            TopAppBar(
                title = { Text("Calendar", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = CalendarBackground)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            CalendarHeaderCard(
                visibleMonth = visibleMonth,
                selectedDate = selectedDate,
                monthEventCount = monthEventCount,
                onPreviousMonth = {
                    val newMonth = visibleMonth.plus(DatePeriod(months = -1)).startOfMonth()
                    visibleMonth = newMonth
                    selectedDate = selectedDate.alignToMonth(newMonth)
                },
                onNextMonth = {
                    val newMonth = visibleMonth.plus(DatePeriod(months = 1)).startOfMonth()
                    visibleMonth = newMonth
                    selectedDate = selectedDate.alignToMonth(newMonth)
                },
                onOpenPicker = { showDatePicker = true },
                onJumpToToday = {
                    visibleMonth = today.startOfMonth()
                    selectedDate = today
                }
            )

            if (monthSections.isEmpty()) {
                EmptyMonthState(visibleMonth = visibleMonth)
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    state = listState,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(monthSections, key = { it.date.toString() }) { section ->
                        CalendarDaySectionCard(
                            section = section,
                            selectedDate = selectedDate,
                            today = today,
                            onEntryClick = { entry ->
                                selectedCalendarEvent = repository.getCalendarEventById(entry.sourceEventId)
                            }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(80.dp)) }
                }
            }
        }
    }
}

@Composable
private fun CalendarHeaderCard(
    visibleMonth: LocalDate,
    selectedDate: LocalDate,
    monthEventCount: Int,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onOpenPicker: () -> Unit,
    onJumpToToday: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, CalendarBorder)
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onPreviousMonth) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = "Previous month")
                }
                Surface(
                    modifier = Modifier
                        .weight(1f)
                        .clickable(onClick = onOpenPicker),
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            imageVector = Icons.Default.CalendarMonth,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = visibleMonth.monthYearLabel(),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                IconButton(onClick = onNextMonth) {
                    Icon(Icons.Default.ChevronRight, contentDescription = "Next month")
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = selectedDate.fullDateLabel(),
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = if (monthEventCount == 1) {
                            "1 calendar entry this month"
                        } else {
                            "$monthEventCount calendar entries this month"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = CalendarMuted
                    )
                }
                TextButton(onClick = onJumpToToday) {
                    Text("Today")
                }
            }
        }
    }
}
@Composable
private fun EmptyMonthState(visibleMonth: LocalDate) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, CalendarBorder)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 40.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "No calendar events for ${visibleMonth.monthYearLabel().lowercase()}.",
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.SemiBold,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(6.dp))
                Text(
                    text = "Change the month or pick another date to review employee leave and activity entries.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = CalendarMuted,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
private fun CalendarDaySectionCard(
    section: CalendarDaySection,
    selectedDate: LocalDate,
    today: LocalDate,
    onEntryClick: (CalendarAgendaEntry) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, CalendarBorder)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        when {
                            section.date == selectedDate -> CalendarSelectedTone
                            section.date == today -> CalendarTodayTone
                            else -> Color.Transparent
                        }
                    )
                    .padding(horizontal = 16.dp, vertical = 14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = section.date.dayHeaderLabel(),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                if (section.date == today) {
                    DayTag(label = "Today", background = Color(0xFFE8F5E9), content = Color(0xFF2E7D32))
                    Spacer(modifier = Modifier.width(8.dp))
                }
                if (section.date == selectedDate) {
                    DayTag(label = "Selected", background = Color(0xFFE5EDFF), content = Color(0xFF234AA7))
                }
            }

            section.entries.forEachIndexed { index, entry ->
                if (index > 0) {
                    HorizontalDivider(color = CalendarBorder)
                }
                AgendaEntryRow(
                    entry = entry,
                    onClick = { onEntryClick(entry) }
                )
            }
        }
    }
}
@Composable
private fun AgendaEntryRow(
    entry: CalendarAgendaEntry,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 14.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .padding(top = 6.dp)
                .size(10.dp)
                .clip(CircleShape)
                .background(getEventColor(entry.eventType))
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                entry.startTime?.let { startTime ->
                    Surface(
                        shape = RoundedCornerShape(999.dp),
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)
                    ) {
                        Text(
                            text = startTime.toDisplayTime(),
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }
                Text(
                    text = entry.primaryLine(),
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }
            entry.secondaryLine()?.let { secondary ->
                Text(
                    text = secondary,
                    style = MaterialTheme.typography.bodySmall,
                    color = CalendarMuted
                )
            }
        }
    }
}

@Composable
private fun DayTag(
    label: String,
    background: Color,
    content: Color
) {
    Surface(
        shape = RoundedCornerShape(999.dp),
        color = background
    ) {
        Text(
            text = label,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.SemiBold,
            color = content
        )
    }
}
@Composable
private fun CalendarPickerDialog(
    monthAnchor: LocalDate,
    selectedDate: LocalDate,
    today: LocalDate,
    eventTypesByDate: Map<LocalDate, List<CalendarEventType>>,
    onDismiss: () -> Unit,
    onPreviousMonth: () -> Unit,
    onNextMonth: () -> Unit,
    onSelectDate: (LocalDate) -> Unit
) {
    val monthDates = remember(monthAnchor) { getMonthGridDates(monthAnchor) }

    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White),
            border = BorderStroke(1.dp, CalendarBorder)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(onClick = onPreviousMonth) {
                        Icon(Icons.Default.ChevronLeft, contentDescription = "Previous month")
                    }
                    Text(
                        text = monthAnchor.monthYearLabel(),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = onNextMonth) {
                        Icon(Icons.Default.ChevronRight, contentDescription = "Next month")
                    }
                }

                Row(modifier = Modifier.fillMaxWidth()) {
                    weekDayLabels().forEach { label ->
                        Text(
                            text = label,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.labelMedium,
                            color = CalendarMuted
                        )
                    }
                }

                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    monthDates.chunked(7).forEach { week ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            week.forEach { date ->
                                CalendarDateCell(
                                    date = date,
                                    monthAnchor = monthAnchor,
                                    selectedDate = selectedDate,
                                    today = today,
                                    eventTypes = eventTypesByDate[date].orEmpty(),
                                    modifier = Modifier.weight(1f),
                                    onClick = { onSelectDate(date) }
                                )
                            }
                        }
                    }
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text("Close")
                    }
                }
            }
        }
    }
}
@Composable
private fun CalendarDateCell(
    date: LocalDate,
    monthAnchor: LocalDate,
    selectedDate: LocalDate,
    today: LocalDate,
    eventTypes: List<CalendarEventType>,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val isSelected = date == selectedDate
    val isToday = date == today
    val isCurrentMonth = date.month == monthAnchor.month && date.year == monthAnchor.year

    Surface(
        modifier = modifier
            .aspectRatio(1f)
            .clip(RoundedCornerShape(18.dp))
            .clickable(onClick = onClick),
        color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
        border = if (isToday && !isSelected) BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)) else null
    ) {
        Column(
            modifier = Modifier.padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = date.dayOfMonth.toString(),
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = if (isSelected || isToday) FontWeight.Bold else FontWeight.Normal,
                color = when {
                    isSelected -> MaterialTheme.colorScheme.onPrimary
                    isCurrentMonth -> MaterialTheme.colorScheme.onSurface
                    else -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.35f)
                }
            )
            if (eventTypes.isNotEmpty()) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(3.dp)) {
                    eventTypes.take(3).forEach { type ->
                        Box(
                            modifier = Modifier
                                .size(5.dp)
                                .clip(CircleShape)
                                .background(
                                    if (isSelected) {
                                        MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                                    } else {
                                        getEventColor(type)
                                    }
                                )
                        )
                    }
                }
            }
        }
    }
}
private fun CalendarAgendaEntry.primaryLine(): String = when {
    isHoliday -> title
    eventType == CalendarEventType.GENERAL_EVENT -> title
    eventType == CalendarEventType.COMP_TIME_REGISTER || eventType == CalendarEventType.COMP_TIME_CLAIM -> buildString {
        append(employeeName ?: title)
        append(" - ")
        append(eventType.displayName)
        if (hours != null) {
            append(" (")
            append(hours.toHoursLabel())
            append(")")
        }
    }
    else -> buildString {
        append(employeeName ?: title)
        append(" - ")
        append(eventType.shortLabel)
        if (leaveProgressDay != null && leaveProgressTotal != null) {
            append(" (Day ")
            append(leaveProgressDay)
            append("/")
            append(leaveProgressTotal)
            append(")")
        }
    }
}

private fun CalendarAgendaEntry.secondaryLine(): String? = null

private fun getEventColor(eventType: CalendarEventType): Color = when (eventType) {
    CalendarEventType.ANNUAL_LEAVE, CalendarEventType.WORK_FROM_HOME -> Color(0xFF2F6BFF)
    CalendarEventType.MEDICAL_CLAIM, CalendarEventType.EMERGENCY_LEAVE, CalendarEventType.COMPASSIONATE_LEAVE -> Color(0xFF2E8B57)
    CalendarEventType.COMP_TIME_REGISTER, CalendarEventType.COMP_TIME_CLAIM -> Color(0xFFD84B4B)
    CalendarEventType.GENERAL_EVENT -> Color(0xFFF39C55)
    CalendarEventType.HOLIDAY -> Color(0xFF4AA56C)
}

private fun LocalDate.startOfMonth(): LocalDate = LocalDate(year, month, 1)

private fun LocalDate.alignToMonth(monthAnchor: LocalDate): LocalDate {
    val monthStart = monthAnchor.startOfMonth()
    val monthEnd = monthStart.plus(DatePeriod(months = 1)).minus(DatePeriod(days = 1))
    val day = minOf(dayOfMonth, monthEnd.dayOfMonth)
    return LocalDate(monthStart.year, monthStart.month, day)
}

private fun LocalDate.monthYearLabel(): String {
    val monthLabel = month.name.lowercase().replaceFirstChar { it.uppercase() }
    return "$monthLabel $year"
}

private fun LocalDate.fullDateLabel(): String {
    val dayLabel = dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() }
    val monthLabel = month.name.lowercase().replaceFirstChar { it.uppercase() }
    return "$dayLabel, $dayOfMonth $monthLabel $year"
}

private fun LocalDate.dayHeaderLabel(): String {
    val monthLabel = month.name.take(3)
    val weekDayLabel = dayOfWeek.name.take(3)
    return "$dayOfMonth $monthLabel, $weekDayLabel"
}
private fun Double.toHoursLabel(): String {
    val wholeNumber = this % 1.0 == 0.0
    return if (wholeNumber) {
        "${toInt()} Hours"
    } else {
        "$this Hours"
    }
}

private fun String.toDisplayTime(): String {
    val parts = split(":")
    val hour24 = parts.getOrNull(0)?.toIntOrNull() ?: return this
    val minute = parts.getOrNull(1)?.toIntOrNull() ?: 0
    val suffix = if (hour24 >= 12) "PM" else "AM"
    val hour12 = when {
        hour24 == 0 -> 12
        hour24 > 12 -> hour24 - 12
        else -> hour24
    }
    return if (minute == 0) "$hour12 $suffix" else "$hour12:${minute.toString().padStart(2, '0')} $suffix"
}

private fun weekDayLabels(): List<String> = listOf(
    DayOfWeek.MONDAY,
    DayOfWeek.TUESDAY,
    DayOfWeek.WEDNESDAY,
    DayOfWeek.THURSDAY,
    DayOfWeek.FRIDAY,
    DayOfWeek.SATURDAY,
    DayOfWeek.SUNDAY
).map { it.name.take(3) }

private fun getMonthGridDates(monthAnchor: LocalDate): List<LocalDate> {
    val monthStart = monthAnchor.startOfMonth()
    val leadingDays = monthStart.dayOfWeek.value - 1
    val grid = mutableListOf<LocalDate>()

    var cursor = monthStart.minus(DatePeriod(days = leadingDays))
    while (grid.size < 42) {
        grid += cursor
        cursor = cursor.plus(DatePeriod(days = 1))
    }

    return grid
}

private fun CalendarEvent.detailTitle(): String = employeeName?.let { "$it - $title" } ?: title

private fun CalendarEvent.detailBodyText(): String = buildString {
    append(detailDateText())
    detailTimeText()?.let {
        append("\n")
        append(it)
    }
    val extra = reason ?: description
    if (!extra.isNullOrBlank()) {
        append("\n\n")
        append(if (reason.isNullOrBlank()) "Details: " else "Reason: ")
        append(extra)
    }
}

private fun CalendarEvent.detailDateText(): String {
    val rangeStart = startDate ?: date
    val rangeEnd = endDate ?: rangeStart
    return if (rangeStart == rangeEnd) rangeStart.longDateLabel() else "${rangeStart.longDateLabel()} - ${rangeEnd.longDateLabel()}"
}

private fun CalendarEvent.detailTimeText(): String? {
    if (!isCompTime()) return null
    val start = startTime?.toDisplayTime()
    val end = endTime?.toDisplayTime()
    val timeRange = when {
        start != null && end != null -> "$start - $end"
        start != null -> start
        end != null -> end
        else -> null
    }
    return when {
        timeRange != null && hours != null -> "$timeRange (${hours.toHoursLabel()})"
        timeRange != null -> timeRange
        hours != null -> hours.toHoursLabel()
        else -> null
    }
}

private fun CalendarEvent.isCompTime(): Boolean =
    eventType == CalendarEventType.COMP_TIME_REGISTER || eventType == CalendarEventType.COMP_TIME_CLAIM

private fun LocalDate.longDateLabel(): String {
    val monthLabel = month.name.lowercase().replaceFirstChar { it.uppercase() }
    return "$monthLabel $dayOfMonth, $year"
}