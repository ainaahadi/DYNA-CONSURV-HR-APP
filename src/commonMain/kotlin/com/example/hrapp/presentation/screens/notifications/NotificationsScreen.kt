package com.example.hrapp.presentation.screens.notifications

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocalHospital
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hrapp.data.repository.MockApprovalRepository
import com.example.hrapp.domain.model.LeaveApprovalRequest
import com.example.hrapp.domain.model.LeaveType
import com.example.hrapp.presentation.components.ApprovalBadgedBox

private val NotifyBackground = Color(0xFFF4F7FE)
private val NotifyBorder = Color(0xFFD7DFF1)
private val NotifyPrimary = Color(0xFF3E61AD)
private val NotifyMuted = Color(0xFF6E7688)
private val NotifyApprove = Color(0xFF79B56A)
private val NotifyDecline = Color(0xFFE17358)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationsScreen(
    onBack: () -> Unit = {},
    onOpenApprovalCenter: () -> Unit = {},
    onOpenApprovalDetails: (Int) -> Unit = {}
) {
    val pendingRequests = MockApprovalRepository.getPendingApprovalRequests()
    val buckets = listOf("Today", "Yesterday", "Earlier")
    var expandedRequestIds by remember { mutableStateOf(setOf<Int>()) }

    Scaffold(
        containerColor = NotifyBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Notifications",
                        color = NotifyPrimary,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    ApprovalBadgedBox(count = pendingRequests.size) {
                        IconButton(onClick = onOpenApprovalCenter) {
                            Icon(
                                Icons.Default.DoneAll,
                                contentDescription = "Open approval center",
                                tint = NotifyPrimary
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = NotifyBackground)
            )
        }
    ) { padding ->
        if (pendingRequests.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(NotifyBackground),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "No pending notifications.",
                        color = NotifyMuted,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    TextButton(onClick = onOpenApprovalCenter) {
                        Text("Open approval center")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(NotifyBackground),
                contentPadding = PaddingValues(bottom = 24.dp),
                verticalArrangement = Arrangement.spacedBy(0.dp)
            ) {
                item {
                    ApprovalSummaryCard(
                        pendingCount = pendingRequests.size,
                        onOpenApprovalCenter = onOpenApprovalCenter
                    )
                }

                buckets.forEach { bucket ->
                    val bucketItems = pendingRequests.filter { it.notificationBucket == bucket }
                    if (bucketItems.isNotEmpty()) {
                        item {
                            Text(
                                text = bucket,
                                color = NotifyMuted,
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
                            )
                        }
                        items(bucketItems, key = { it.id }) { request ->
                            NotificationRow(
                                request = request,
                                expanded = expandedRequestIds.contains(request.id),
                                onToggleExpand = {
                                    expandedRequestIds = if (expandedRequestIds.contains(request.id)) {
                                        expandedRequestIds - request.id
                                    } else {
                                        expandedRequestIds + request.id
                                    }
                                },
                                onOpenDetails = { onOpenApprovalDetails(request.id) },
                                onApprove = { MockApprovalRepository.approveRequest(request.id) },
                                onDecline = { MockApprovalRepository.rejectRequest(request.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ApprovalSummaryCard(
    pendingCount: Int,
    onOpenApprovalCenter: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 10.dp),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, NotifyBorder)
    ) {
        Row(
            modifier = Modifier.padding(18.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(NotifyPrimary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(Icons.Default.DoneAll, contentDescription = null, tint = NotifyPrimary)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Pending approvals",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "$pendingCount leave request(s) are waiting for review.",
                    color = NotifyMuted,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
            TextButton(onClick = onOpenApprovalCenter) {
                Text("Open")
            }
        }
    }
}
@Composable
private fun NotificationRow(
    request: LeaveApprovalRequest,
    expanded: Boolean,
    onToggleExpand: () -> Unit,
    onOpenDetails: () -> Unit,
    onApprove: () -> Unit,
    onDecline: () -> Unit
) {
    val (icon, iconTint) = notificationIconFor(request.leaveType)

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(0.dp),
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(0.6.dp, NotifyBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp)
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(42.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = iconTint,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(14.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "${request.employeeName} requested ${request.leaveType.displayName.lowercase()}.",
                        color = NotifyPrimary,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = request.leaveDateDisplay,
                        color = NotifyMuted,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                        ApprovalButton(
                            text = "Decline",
                            color = NotifyDecline,
                            onClick = onDecline
                        )
                        ApprovalButton(
                            text = "Approve",
                            color = NotifyApprove,
                            onClick = onApprove
                        )
                    }
                }
                IconButton(onClick = onToggleExpand) {
                    Icon(
                        Icons.Default.ExpandMore,
                        contentDescription = "Expand",
                        tint = NotifyMuted
                    )
                }
            }

            if (expanded) {
                Spacer(modifier = Modifier.height(12.dp))
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "Applied on ${request.appliedDate}",
                        color = NotifyMuted,
                        style = MaterialTheme.typography.bodySmall,
                        lineHeight = 18.sp
                    )
                    Text(
                        text = request.purpose,
                        color = NotifyMuted,
                        style = MaterialTheme.typography.bodySmall,
                        lineHeight = 18.sp
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "View full approval details",
                    color = NotifyPrimary,
                    style = MaterialTheme.typography.labelLarge,
                    modifier = Modifier.clickable(onClick = onOpenDetails)
                )
            }
        }
    }
}
@Composable
private fun ApprovalButton(
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    OutlinedButton(
        onClick = onClick,
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.2.dp, color),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 0.dp),
        modifier = Modifier.height(34.dp)
    ) {
        Text(
            text = text,
            color = color,
            fontWeight = FontWeight.Medium
        )
    }
}

private fun notificationIconFor(leaveType: LeaveType): Pair<ImageVector, Color> {
    return when (leaveType) {
        LeaveType.ANNUAL -> Icons.Default.CalendarMonth to NotifyPrimary
        LeaveType.MEDICAL_CLAIM -> Icons.Default.LocalHospital to Color(0xFF4D8EE8)
        LeaveType.EMERGENCY -> Icons.Default.WarningAmber to Color(0xFF8B6BD8)
        LeaveType.COMPASSIONATE -> Icons.Default.WarningAmber to Color(0xFF9C6ADE)
        LeaveType.COMP_TIME_CLAIM -> Icons.Default.CalendarMonth to Color(0xFF8A94A6)
        LeaveType.COMP_TIME_REGISTER -> Icons.Default.CalendarMonth to Color(0xFF5C7BD9)
    }
}
