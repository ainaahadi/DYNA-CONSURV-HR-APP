package com.example.hrapp.presentation.screens.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Event
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.datetime.*

data class CalendarEvent(
    val id: String,
    val title: String,
    val date: LocalDate,
    val type: EventType
)

enum class EventType(val color: Color) {
    LEAVE_APPROVED(Color(0xFF66BB6A)), // Green
    LEAVE_PENDING(Color(0xFFFFA726)),  // Orange
    HOLIDAY(Color(0xFFEF5350))         // Red
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarScreen(onBack: () -> Unit) {
    var currentDate by remember { mutableStateOf(Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) }
    var selectedDate by remember { mutableStateOf(currentDate) }

    val currentMonthDates = remember(currentDate) {
        getDatesForMonth(currentDate.year, currentDate.month)
    }

    // Mock Events
    val mockEvents = remember {
        listOf(
            CalendarEvent("1", "Christmas Holiday", LocalDate(2025, 12, 25), EventType.HOLIDAY),
            CalendarEvent("2", "Annual Leave (Approved)", LocalDate(2025, 12, 26), EventType.LEAVE_APPROVED),
            CalendarEvent("3", "WFH (Pending)", LocalDate(2025, 12, 12), EventType.LEAVE_PENDING),
            // Few for current month relative to now
            CalendarEvent("4", "Company Offsite", currentDate.plus(DatePeriod(days = 4)), EventType.HOLIDAY),
            CalendarEvent("5", "Personal Time Off", currentDate.plus(DatePeriod(days = -2)), EventType.LEAVE_APPROVED)
        )
    }

    val selectedDateEvents = remember(selectedDate) {
        mockEvents.filter { it.date == selectedDate }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Calendar", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // Calendar Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { currentDate = currentDate.minus(DatePeriod(months = 1)) }) {
                    Icon(Icons.Default.ChevronLeft, contentDescription = "Previous Month")
                }
                Text(
                    text = "${currentDate.month.name} ${currentDate.year}".lowercase().replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                IconButton(onClick = { currentDate = currentDate.plus(DatePeriod(months = 1)) }) {
                    Icon(Icons.Default.ChevronRight, contentDescription = "Next Month")
                }
            }

            // Days of Week Header
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                listOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun").forEach { day ->
                    Text(
                        text = day,
                        modifier = Modifier.weight(1f),
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Calendar Grid
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
            ) {
                currentMonthDates.chunked(7).forEach { week ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        week.forEach { date ->
                            val isSelected = date == selectedDate
                            val isCurrentMonth = date?.month == currentDate.month
                            val eventsForDate = mockEvents.filter { it.date == date }

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .aspectRatio(1f)
                                    .padding(2.dp)
                                    .clip(CircleShape)
                                    .background(
                                        when {
                                            isSelected -> MaterialTheme.colorScheme.primary
                                            else -> Color.Transparent
                                        }
                                    )
                                    .clickable(enabled = date != null) {
                                        if (date != null) selectedDate = date
                                    },
                                contentAlignment = Alignment.Center
                            ) {
                                if (date != null) {
                                    Column(
                                        horizontalAlignment = Alignment.CenterHorizontally,
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = date.dayOfMonth.toString(),
                                            color = when {
                                                isSelected -> MaterialTheme.colorScheme.onPrimary
                                                !isCurrentMonth -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
                                                else -> MaterialTheme.colorScheme.onSurface
                                            },
                                            style = MaterialTheme.typography.bodyMedium,
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                        )
                                        
                                        // Event Indicator Dots
                                        if (eventsForDate.isNotEmpty()) {
                                            Spacer(modifier = Modifier.height(2.dp))
                                            Row(
                                                horizontalArrangement = Arrangement.spacedBy(2.dp),
                                                verticalAlignment = Alignment.CenterVertically
                                            ) {
                                                eventsForDate.take(3).forEach { event ->
                                                    Box(
                                                        modifier = Modifier
                                                            .size(4.dp)
                                                            .clip(CircleShape)
                                                            .background(
                                                                if (isSelected) MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f) else event.type.color
                                                            )
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))

            // Events List
            Text(
                text = if (selectedDate == Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date) "Today's Events" else "Events on ${selectedDate.dayOfMonth} ${selectedDate.month.name}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(20.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (selectedDateEvents.isEmpty()) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 32.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "No events scheduled for this day.",
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    items(selectedDateEvents) { event ->
                        EventCard(event)
                    }
                }
                item { Spacer(modifier = Modifier.height(80.dp)) }
            }
        }
    }
}

@Composable
fun EventCard(event: CalendarEvent) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(event.type.color)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Event,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = when(event.type) {
                            EventType.HOLIDAY -> "Public / Corporate Holiday"
                            EventType.LEAVE_APPROVED -> "Leave (Approved)"
                            EventType.LEAVE_PENDING -> "Leave (Pending)"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// Helper to generate calendar grid dates
fun getDatesForMonth(year: Int, month: Month): List<LocalDate?> {
    val firstDayOfMonth = LocalDate(year, month, 1)
    val lastDayOfMonth = firstDayOfMonth.plus(DatePeriod(months = 1)).minus(DatePeriod(days = 1))
    
    val daysInMonth = lastDayOfMonth.dayOfMonth
    // Kotlin DateTime DayOfWeek enum is 1 (Monday) to 7 (Sunday)
    val firstDayOfWeek = firstDayOfMonth.dayOfWeek.value 
    
    val grid = mutableListOf<LocalDate?>()
    
    // Add empty padding for days before the 1st of the month
    for (i in 1 until firstDayOfWeek) {
        val prevMonthDate = firstDayOfMonth.minus(DatePeriod(days = firstDayOfWeek - i))
        grid.add(prevMonthDate) // Optional: add previous month days, or use null for blank cells
    }
    
    // Add actual days of the month
    for (day in 1..daysInMonth) {
        grid.add(LocalDate(year, month, day))
    }
    
    // Fill remaining cells of the last week
    while (grid.size % 7 != 0) {
       val nextMonthDate = lastDayOfMonth.plus(DatePeriod(days = (grid.size % 7) + 1))
       grid.add(nextMonthDate) // Optional: add next month days
    }
    
    return grid
}
