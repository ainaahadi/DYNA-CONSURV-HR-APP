package com.example.hrapp.presentation.screens.leave

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hrapp.domain.model.LeaveRequest
import com.example.hrapp.domain.model.LeaveStatus
import com.example.hrapp.domain.model.MockLeaveData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaveScreen(
    onApplyLeave: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Leave Management") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onApplyLeave,
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                shape = CircleShape,
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Apply Leave")
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Text(
                    "Your Balances (2026)",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            item { BalanceGrid() }
            item { LeaveHistoryTabs() }
            item {
                val requests = MockLeaveData.requests
                if (requests.isEmpty()) {
                    Box(modifier = Modifier.fillMaxWidth().padding(32.dp), contentAlignment = Alignment.Center) {
                        Text("No leave history found.", color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        requests.forEach { request ->
                            LeaveHistoryCard(request)
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun BalanceGrid() {
    val balances = listOf(
        BalanceInfo("12", "AL Left", Color(0xFF42A5F5), Color(0xFF1565C0)),
        BalanceInfo("03", "WFH Mth Left", Color(0xFFAB47BC), Color(0xFF6A1B9A)),
        BalanceInfo("05", "MC Taken", Color(0xFFEF5350), Color(0xFFC62828)),
        BalanceInfo("01", "EL Taken", Color(0xFFFFA726), Color(0xFFEF6C00)),
        BalanceInfo("00", "CL Taken", Color(0xFF26C6DA), Color(0xFF00838F)),
        BalanceInfo("08", "Comp Hrs", Color(0xFF8D6E63), Color(0xFF4E342E))
    )

    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
        for (i in balances.indices step 3) {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                for (j in 0..2) {
                    if (i + j < balances.size) {
                        BalanceCard(balances[i + j], modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

@Composable
fun BalanceCard(info: BalanceInfo, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .background(Brush.linearGradient(listOf(info.startColor, info.endColor)))
                .padding(vertical = 12.dp, horizontal = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(info.value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = Color.White)
                Text(info.label, fontSize = 10.sp, fontWeight = FontWeight.Medium, color = Color.White.copy(alpha = 0.9f))
            }
        }
    }
}

@Composable
fun LeaveHistoryTabs() {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            TabItem("My History", true, modifier = Modifier.weight(1f))
            TabItem("To Approve (1)", false, modifier = Modifier.weight(1f))
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
    }
}

@Composable
fun TabItem(label: String, selected: Boolean, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (selected) {
            Spacer(modifier = Modifier.height(8.dp))
            Box(modifier = Modifier.height(2.dp).fillMaxWidth().background(MaterialTheme.colorScheme.primary))
        }
    }
}

@Composable
fun LeaveHistoryCard(request: LeaveRequest) {
    val iconColor = when (request.typeName) {
        "Annual Leave" -> Color(0xFF42A5F5)
        "WFH" -> Color(0xFFAB47BC)
        "Medical Certificate" -> Color(0xFFEF5350)
        else -> Color(0xFFFFA726)
    }

    val icon = when (request.typeName) {
        "Annual Leave" -> Icons.Default.Park
        "WFH" -> Icons.Default.House
        "Medical Certificate" -> Icons.Default.MedicalServices
        else -> Icons.Default.Warning
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.05f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(iconColor.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, tint = iconColor, modifier = Modifier.size(24.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(request.typeName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    StatusBadge(request.status)
                }
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(vertical = 4.dp)) {
                    Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(14.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(request.dateDisplay, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
                Text("Session: ${request.reason}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f))
            }
        }
    }
}

@Composable
fun StatusBadge(status: LeaveStatus) {
    val (color, text) = when (status) {
        LeaveStatus.PENDING -> Color(0xFFFFA726) to "PENDING"
        LeaveStatus.APPROVED -> Color(0xFF66BB6A) to "APPROVED"
        LeaveStatus.REJECTED -> Color(0xFFEF5350) to "REJECTED"
    }

    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(4.dp))
            .background(color.copy(alpha = 0.2f))
            .padding(horizontal = 8.dp, vertical = 4.dp)
    ) {
        Text(text, color = color, fontWeight = FontWeight.Bold, fontSize = 10.sp)
    }
}

data class BalanceInfo(val value: String, val label: String, val startColor: Color, val endColor: Color)
