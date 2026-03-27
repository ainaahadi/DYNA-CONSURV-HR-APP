package com.example.hrapp.presentation.screens.attendance

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hrapp.presentation.components.HRSecondaryButton

private val AttendBackground = Color(0xFFF5F7FD)
private val AttendSuccessStart = Color(0xFF55C878)
private val AttendSuccessEnd = Color(0xFF3FAE69)
private val AttendInfo = Color(0xFF4A84E6)
private val AttendMuted = Color(0xFF7D8699)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AttendanceScreen(
    onNavigateToQRScanner: () -> Unit,
    onBack: () -> Unit = {}
) {
    var checkedIn by remember { mutableStateOf(true) }
    var selectedMonth by remember { mutableStateOf("Jan 2026") }
    var showMenu by remember { mutableStateOf(false) }
    val monthOptions = listOf("Jan 2026", "Dec 2025", "Nov 2025")

    Scaffold(
        containerColor = AttendBackground,
        topBar = {
            TopAppBar(
                title = { Text("Attendance", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    Box {
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = "More")
                        }
                        DropdownMenu(
                            expanded = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text = { Text(if (checkedIn) "Mark as checked out" else "Mark as checked in") },
                                onClick = {
                                    checkedIn = !checkedIn
                                    showMenu = false
                                }
                            )
                            DropdownMenuItem(
                                text = { Text("Refresh records") },
                                onClick = { showMenu = false }
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = AttendBackground)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(AttendBackground)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            item {
                StatusHero(
                    checkedIn = checkedIn,
                    onToggleStatus = { checkedIn = !checkedIn }
                )
            }
            item {
                QRScanCard(onNavigateToQRScanner)
            }
            item {
                HistorySectionHeader(
                    selectedMonth = selectedMonth,
                    monthOptions = monthOptions,
                    onMonthSelected = { selectedMonth = it }
                )
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, Color(0xFFDCE3F0))
                ) {
                    Column {
                        getMockAttendanceRecords().forEachIndexed { index, record ->
                            AttendanceHistoryItem(record)
                            if (index < getMockAttendanceRecords().lastIndex) {
                                HorizontalDivider(
                                    modifier = Modifier.padding(horizontal = 16.dp),
                                    color = Color(0xFFE7EBF5)
                                )
                            }
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun StatusHero(
    checkedIn: Boolean,
    onToggleStatus: () -> Unit
) {
    val gradientColors = if (checkedIn) {
        listOf(AttendSuccessStart, AttendSuccessEnd)
    } else {
        listOf(Color(0xFFFFB547), Color(0xFFF28A34))
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Brush.linearGradient(gradientColors))
                .padding(vertical = 28.dp, horizontal = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.20f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(32.dp)
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = if (checkedIn) "Checked In" else "Checked Out",
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = if (checkedIn) "Since 09:00 AM - Duration 6h 30m" else "Last shift completed at 06:00 PM",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.92f),
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(22.dp))
                Button(
                    onClick = onToggleStatus,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = gradientColors.last()
                    ),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = if (checkedIn) "CHECK OUT" else "CHECK IN",
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

@Composable
fun QRScanCard(onScanQR: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF0F4FC)),
        border = BorderStroke(1.dp, Color(0xFFD9E2F1))
    ) {
        Column(
            modifier = Modifier.padding(22.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(74.dp)
                    .clip(RoundedCornerShape(22.dp))
                    .background(Color(0xFFE3ECFF)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.QrCode2,
                    contentDescription = null,
                    modifier = Modifier.size(42.dp),
                    tint = AttendInfo
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Scan QR Code",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = "Align the office QR code within the frame to mark your attendance manually.",
                style = MaterialTheme.typography.bodyMedium,
                color = AttendMuted,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(20.dp))
            HRSecondaryButton(
                text = "SCAN QR CODE",
                onClick = onScanQR,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun HistorySectionHeader(
    selectedMonth: String,
    monthOptions: List<String>,
    onMonthSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "This Month",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Box {
            Surface(
                shape = RoundedCornerShape(12.dp),
                color = Color.White,
                border = BorderStroke(1.dp, Color(0xFFDCE3F0)),
                modifier = Modifier.clickable { expanded = true }
            ) {
                Row(
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = selectedMonth,
                        style = MaterialTheme.typography.bodyMedium,
                        color = AttendMuted
                    )
                    Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = AttendMuted)
                }
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                monthOptions.forEach { option ->
                    DropdownMenuItem(
                        text = { Text(option) },
                        onClick = {
                            onMonthSelected(option)
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun AttendanceHistoryItem(record: AttendanceRecord) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .width(60.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(Color(0xFFF3F6FB))
                .padding(vertical = 8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(record.month, style = MaterialTheme.typography.labelSmall, color = AttendMuted)
            Text(record.day, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(record.type, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                StatusBadge(record.status)
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Icon(
                    Icons.Default.AccessTime,
                    contentDescription = null,
                    modifier = Modifier.size(14.dp),
                    tint = AttendMuted
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = "In: ${record.checkIn} - Out: ${record.checkOut}",
                    style = MaterialTheme.typography.bodySmall,
                    color = AttendMuted
                )
            }
            Text(
                text = "Duration: ${record.duration} - ${record.location}",
                style = MaterialTheme.typography.labelSmall,
                color = AttendMuted
            )
        }
    }
}

@Composable
fun StatusBadge(status: String) {
    val color = when (status) {
        "Complete" -> AttendSuccessEnd
        "Half Day" -> Color(0xFFFFA726)
        else -> Color(0xFFEF5350)
    }
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            Icons.Default.CheckCircle,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(status, color = color, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}

data class AttendanceRecord(
    val month: String,
    val day: String,
    val type: String,
    val checkIn: String,
    val checkOut: String,
    val duration: String,
    val location: String,
    val status: String
)

fun getMockAttendanceRecords() = listOf(
    AttendanceRecord("JAN", "14", "Regular Shift", "09:00 AM", "06:00 PM", "8h 30m", "Office", "Complete"),
    AttendanceRecord("JAN", "13", "Regular Shift", "08:45 AM", "05:45 PM", "8h 30m", "Office", "Complete"),
    AttendanceRecord("JAN", "12", "Half Day", "09:00 AM", "01:00 PM", "4h 00m", "Office", "Half Day")
)
