package com.example.hrapp.presentation.screens.home

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.HomeWork
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.NotificationsNone
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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

private val HomeBg = Color(0xFFF5F7FD)
private val HomeSurface = Color(0xFFFFFFFF)
private val HomeTint = Color(0xFFE4EAF8)
private val HomeBorder = Color(0xFFD6DDEC)
private val HomePrimary = Color(0xFF234AA7)
private val HomeAccent = Color(0xFFD23545)
private val HomeText = Color(0xFF222938)
private val HomeMuted = Color(0xFF7D8699)

data class SummaryItem(
    val label: String,
    val count: Int,
    val icon: ImageVector
)

@Composable
fun HomeScreen(
    onNavigateToAnnouncements: () -> Unit = {},
    onNavigateToSettings: () -> Unit = {},
    onNavigateToMenu: () -> Unit = {}
) {
    val isDark = ThemeState.isDarkModeEnabled ?: androidx.compose.foundation.isSystemInDarkTheme()
    val pageBg = if (isDark) MaterialTheme.colorScheme.background else HomeBg
    val cardBg = if (isDark) MaterialTheme.colorScheme.surface else HomeSurface
    val cardTint = if (isDark) MaterialTheme.colorScheme.surfaceVariant else HomeTint
    val textColor = if (isDark) MaterialTheme.colorScheme.onBackground else HomeText
    val mutedColor = if (isDark) MaterialTheme.colorScheme.onSurfaceVariant else HomeMuted
    val borderColor = if (isDark) MaterialTheme.colorScheme.outline.copy(alpha = 0.20f) else HomeBorder
    val accentColor = if (isDark) MaterialTheme.colorScheme.primary else HomePrimary

    val summaryItems = listOf(
        SummaryItem("Annual Leave", 4, Icons.Default.EventAvailable),
        SummaryItem("Work From Home", 7, Icons.Default.HomeWork),
        SummaryItem("Medical Leave", 2, Icons.Default.MedicalServices),
        SummaryItem("Emergency Leave", 2, Icons.Default.WarningAmber),
        SummaryItem("Unpaid Leave", 7, Icons.Default.SwapHoriz),
        SummaryItem("Compassionate Leave", 0, Icons.Default.Favorite)
    )

    Scaffold(containerColor = pageBg) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            contentPadding = PaddingValues(top = 16.dp, bottom = 28.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                HomeHeader(
                    onOpenMenu = onNavigateToMenu,
                    onOpenSettings = onNavigateToSettings,
                    onOpenAnnouncements = onNavigateToAnnouncements,
                    accentColor = accentColor,
                    mutedColor = mutedColor,
                    borderColor = borderColor
                )
            }

            item {
                OverviewCard(
                    cardBg = cardBg,
                    borderColor = borderColor,
                    mutedColor = mutedColor,
                    textColor = textColor,
                    accentColor = accentColor
                )
            }

            item {
                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Summary",
                            color = textColor,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = "Quick balances overview",
                            color = mutedColor,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    summaryItems.chunked(3).forEach { rowItems ->
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            rowItems.forEach { item ->
                                SummaryCard(
                                    modifier = Modifier.weight(1f),
                                    item = item,
                                    containerColor = cardTint,
                                    accentColor = accentColor,
                                    textColor = textColor,
                                    borderColor = borderColor
                                )
                            }
                            repeat(3 - rowItems.size) {
                                Spacer(modifier = Modifier.weight(1f))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun HomeHeader(
    onOpenMenu: () -> Unit,
    onOpenSettings: () -> Unit,
    onOpenAnnouncements: () -> Unit,
    accentColor: Color,
    mutedColor: Color,
    borderColor: Color
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        HomeActionIcon(
            icon = Icons.Default.Menu,
            contentDescription = "Menu",
            onClick = onOpenMenu,
            borderColor = borderColor,
            iconTint = mutedColor
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            HomeActionIcon(
                icon = Icons.Default.NotificationsNone,
                contentDescription = "Notifications",
                onClick = onOpenAnnouncements,
                borderColor = borderColor,
                iconTint = mutedColor,
                badgeColor = HomeAccent
            )
            HomeActionIcon(
                icon = Icons.Default.Settings,
                contentDescription = "Settings",
                onClick = onOpenSettings,
                borderColor = borderColor,
                iconTint = accentColor
            )
        }
    }
}

@Composable
private fun HomeActionIcon(
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
    borderColor: Color,
    iconTint: Color,
    badgeColor: Color? = null
) {
    Surface(
        modifier = Modifier
            .size(42.dp)
            .clickable(onClick = onClick),
        shape = CircleShape,
        color = Color.White.copy(alpha = 0.82f),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
        tonalElevation = 0.dp
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(
                imageVector = icon,
                contentDescription = contentDescription,
                tint = iconTint,
                modifier = Modifier.size(22.dp)
            )
            if (badgeColor != null) {
                Box(
                    modifier = Modifier
                        .size(9.dp)
                        .clip(CircleShape)
                        .background(badgeColor)
                        .align(Alignment.TopEnd)
                        .offset(x = (-7).dp, y = 7.dp)
                )
            }
        }
    }
}

@Composable
private fun OverviewCard(
    cardBg: Color,
    borderColor: Color,
    mutedColor: Color,
    textColor: Color,
    accentColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = cardBg),
        shape = RoundedCornerShape(24.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                LeaveIndicator("Total Leaves", 20, accentColor, mutedColor)
                LeaveIndicator("Leaves Used", 5, accentColor, mutedColor)
            }

            Spacer(modifier = Modifier.height(10.dp))

            DonutChart(
                remaining = 15,
                total = 20,
                textColor = mutedColor,
                emphasisTextColor = textColor,
                accentColor = accentColor
            )

            HorizontalDivider(color = borderColor)
        }
    }
}

@Composable
private fun LeaveIndicator(
    label: String,
    count: Int,
    color: Color,
    textColor: Color
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(color)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = "$label\n$count",
            fontSize = 13.sp,
            color = textColor,
            lineHeight = 18.sp
        )
    }
}

@Composable
private fun DonutChart(
    remaining: Int,
    total: Int,
    textColor: Color,
    emphasisTextColor: Color,
    accentColor: Color,
    size: Dp = 190.dp
) {
    val remainAngle = 360f * remaining / total
    val usedAngle = 360f - remainAngle

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(size + 28.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.size(size)) {
            val stroke = Stroke(width = 18.dp.toPx(), cap = StrokeCap.Round)
            drawArc(
                color = Color(0xFFE8EDF7),
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = stroke
            )
            drawArc(
                color = HomeAccent,
                startAngle = -90f,
                sweepAngle = usedAngle,
                useCenter = false,
                style = stroke
            )
            drawArc(
                color = accentColor,
                startAngle = -90f + usedAngle,
                sweepAngle = remainAngle,
                useCenter = false,
                style = stroke
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Remaining\nLeaves",
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                color = textColor,
                lineHeight = 18.sp
            )
            Text(
                text = remaining.toString(),
                fontSize = 56.sp,
                fontWeight = FontWeight.Bold,
                color = emphasisTextColor
            )
        }
    }
}

@Composable
private fun SummaryCard(
    modifier: Modifier,
    item: SummaryItem,
    containerColor: Color,
    accentColor: Color,
    textColor: Color,
    borderColor: Color
) {
    Card(
        modifier = modifier
            .aspectRatio(0.95f)
            .shadow(2.dp, RoundedCornerShape(16.dp)),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        shape = RoundedCornerShape(16.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = null,
                        tint = accentColor,
                        modifier = Modifier.size(18.dp)
                    )
                }
                Text(
                    text = item.count.toString(),
                    color = accentColor,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = item.label,
                color = textColor,
                style = MaterialTheme.typography.bodySmall,
                lineHeight = 16.sp
            )
        }
    }
}
