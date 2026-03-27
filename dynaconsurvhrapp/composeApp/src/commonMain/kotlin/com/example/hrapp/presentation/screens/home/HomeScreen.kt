package com.example.hrapp.presentation.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import com.example.hrapp.theme.ThemeState

// Design tokens matching PDF for light mode
private val LightPageBg    = Color(0xFFF0F4FA)
private val LightCardBg    = Color(0xFFD6E4F0)
private val NavyBlue  = Color(0xFF1A3C8F)
private val CrimsonRed = Color(0xFFC62828)

data class SummaryItem(val label: String, val count: Int, val icon: ImageVector)

@Composable
fun HomeScreen(
    onNavigateToQRScanner: () -> Unit = {},
    onNavigateToApplyLeave: () -> Unit = {},
    onNavigateToAnnouncements: () -> Unit = {},
    onNavigateToCalendar: () -> Unit = {}
) {
    val summaryItems = listOf(
        SummaryItem("Annual\nLeave",      4,  Icons.Default.EventAvailable),
        SummaryItem("Work From\nHome",    7,  Icons.Default.HomeWork),
        SummaryItem("Medical\nLeave",     2,  Icons.Default.MedicalServices),
        SummaryItem("Emergency\nLeave",   2,  Icons.Default.Warning),
        SummaryItem("Unpaid\nLeave",      7,  Icons.Default.MoneyOff),
        SummaryItem("Compassio-\nnate Leave", 0, Icons.Default.Favorite)
    )

    val isDark = ThemeState.isDarkModeEnabled ?: androidx.compose.foundation.isSystemInDarkTheme()
    val pageBg = if (isDark) MaterialTheme.colorScheme.background else LightPageBg
    val cardBg = if (isDark) MaterialTheme.colorScheme.surfaceVariant else LightCardBg
    val textColor = if (isDark) Color.LightGray else Color.DarkGray
    val emphasisTextColor = if (isDark) Color.White else Color(0xFF222222)
    val dividerColor = if (isDark) Color.DarkGray else Color.LightGray.copy(alpha = 0.6f)

    Scaffold(containerColor = pageBg) { pv ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(pv)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item { Spacer(Modifier.height(4.dp)) }

            // ── Header: Menu | Notification (red dot) | Settings ──
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Default.Menu, contentDescription = "Menu", tint = Color.Gray, modifier = Modifier.size(28.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(18.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box {
                            Icon(Icons.Default.NotificationsNone, contentDescription = "Notifications", tint = Color.Gray, modifier = Modifier.size(28.dp))
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(CrimsonRed)
                                    .align(Alignment.TopEnd)
                                    .offset(x = (-2).dp, y = 2.dp)
                            )
                        }
                        IconButton(onClick = onNavigateToCalendar, modifier = Modifier.size(28.dp)) {
                            Icon(Icons.Default.Event, contentDescription = "Calendar", tint = Color.Gray, modifier = Modifier.size(28.dp))
                        }
                        Icon(Icons.Default.Settings, contentDescription = "Settings", tint = Color.Gray, modifier = Modifier.size(26.dp))
                    }
                }
            }

            // ── Leave count labels ──
            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    LeaveIndicator("Total Leaves", 20, NavyBlue, textColor)
                    LeaveIndicator("Leaves Used", 5, NavyBlue, textColor)
                }
            }

            // ── Donut chart (Remaining 15/20) ──
            item { DonutChart(remaining = 15, total = 20, textColor = textColor, emphasisTextColor = emphasisTextColor) }

            // Thin divider
            item { HorizontalDivider(color = dividerColor) }

            // ── Summary section ──
            item {
                Text("Summary", fontWeight = FontWeight.SemiBold, fontSize = 16.sp, color = textColor)
                Spacer(Modifier.height(12.dp))
                val rows = summaryItems.chunked(3)
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    rows.forEach { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            rowItems.forEach { item -> SummaryCard(Modifier.weight(1f), item, cardBg, isDark) }
                            repeat(3 - rowItems.size) { Spacer(Modifier.weight(1f)) }
                        }
                    }
                }
            }
            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun LeaveIndicator(label: String, count: Int, color: Color, textColor: Color) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.size(8.dp).clip(CircleShape).background(color))
        Spacer(Modifier.width(6.dp))
        Text("$label\n$count", fontSize = 13.sp, color = textColor, lineHeight = 18.sp)
    }
}

@Composable
private fun DonutChart(remaining: Int, total: Int, size: Dp = 170.dp, textColor: Color, emphasisTextColor: Color) {
    val used = total - remaining
    val remainAngle = 360f * remaining / total
    val usedAngle = 360f - remainAngle

    Box(
        modifier = Modifier.fillMaxWidth().height(size + 40.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val stroke = Stroke(width = 22.dp.toPx(), cap = StrokeCap.Round)
            // Blue arc = remaining
            drawArc(color = NavyBlue,   startAngle = -90f,                sweepAngle = remainAngle, useCenter = false, style = stroke)
            // Red arc = used
            drawArc(color = CrimsonRed, startAngle = -90f + remainAngle,  sweepAngle = usedAngle,   useCenter = false, style = stroke)
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Remaining\nLeaves", textAlign = TextAlign.Center, fontSize = 13.sp, color = textColor, lineHeight = 18.sp)
            Text("$remaining", fontSize = 48.sp, fontWeight = FontWeight.Bold, color = emphasisTextColor)
        }
    }
}

@Composable
private fun SummaryCard(modifier: Modifier, item: SummaryItem, cardBg: Color, isDark: Boolean) {
    Card(
        modifier = modifier.height(96.dp),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier.size(26.dp).clip(RoundedCornerShape(6.dp)).background(if (isDark) Color.DarkGray else Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(item.icon, contentDescription = null, tint = if (isDark) Color(0xFF64B5F6) else NavyBlue, modifier = Modifier.size(16.dp))
                }
                Text("${item.count}", fontSize = 20.sp, fontWeight = FontWeight.Bold, color = if (isDark) Color.White else NavyBlue)
            }
            Spacer(Modifier.weight(1f))
            Text(item.label, fontSize = 10.sp, color = if (isDark) Color(0xFF90CAF9) else NavyBlue, lineHeight = 13.sp)
        }
    }
}
