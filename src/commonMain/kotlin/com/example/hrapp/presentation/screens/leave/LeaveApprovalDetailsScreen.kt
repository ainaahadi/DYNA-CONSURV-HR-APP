package com.example.hrapp.presentation.screens.leave

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hrapp.data.repository.MockApprovalRepository
import com.example.hrapp.domain.model.ApprovalStatus
import com.example.hrapp.domain.model.EmployeeLeaveRecord
import com.example.hrapp.domain.model.LeaveBalanceSummary
import com.example.hrapp.presentation.components.StatusBadge
import com.example.hrapp.presentation.components.EmployeeAvatar
import com.example.hrapp.presentation.components.HRPrimaryButton
import com.example.hrapp.presentation.components.HRSecondaryButton

private val ApprovalDetailsBg = Color(0xFFF5F7FD)
private val ApprovalDetailsBorder = Color(0xFFDCE3F0)
private val ApprovalDetailsMuted = Color(0xFF7D8699)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaveApprovalDetailsScreen(
    requestId: Int,
    onBack: () -> Unit = {}
) {
    val request = MockApprovalRepository.getApprovalRequestById(requestId)
    var showLeaveRecords by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = ApprovalDetailsBg,
        topBar = {
            TopAppBar(
                title = { Text("Approval Details", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = ApprovalDetailsBg)
            )
        }
    ) { padding ->
        if (request == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Approval request not found.", color = ApprovalDetailsMuted)
            }
        } else {
            val summary = MockApprovalRepository.getLeaveBalanceSummary(request.employeeId)
            val previousRecords = MockApprovalRepository.getEmployeeLeaveRecords(request.employeeId)

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ApprovalDetailsBorder)
                ) {
                    Row(
                        modifier = Modifier.padding(18.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        EmployeeAvatar(name = request.employeeName, size = 52.dp)
                        Spacer(modifier = Modifier.width(14.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(request.employeeName, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                            Text(request.employeeCode, color = ApprovalDetailsMuted)
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = "Leave Balance: ${request.leaveBalance} day(s)",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodySmall,
                                fontWeight = FontWeight.Medium
                            )
                        }
                        StatusBadge(status = request.status)
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ApprovalDetailsBorder)
                ) {
                    Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(14.dp)) {
                        Text("Leave Application", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        ApprovalDetailRow("Leave Type", request.leaveType.displayName)
                        ApprovalDetailRow("Leave Date", request.leaveDateDisplay)
                        ApprovalDetailRow("Reason", request.purpose)
                    }
                }

                Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    Box(modifier = Modifier.weight(1f)) {
                        HRSecondaryButton(
                            text = "REJECT",
                            onClick = { MockApprovalRepository.rejectRequest(request.id) },
                            enabled = request.status == ApprovalStatus.PENDING
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        HRPrimaryButton(
                            text = "APPROVE",
                            onClick = { MockApprovalRepository.approveRequest(request.id) },
                            enabled = request.status == ApprovalStatus.PENDING
                        )
                    }
                }

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = androidx.compose.foundation.BorderStroke(1.dp, ApprovalDetailsBorder)
                ) {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { showLeaveRecords = !showLeaveRecords }
                                .padding(horizontal = 18.dp, vertical = 16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Leave Records",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.weight(1f)
                            )
                            Icon(
                                imageVector = if (showLeaveRecords) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
                                contentDescription = null,
                                tint = ApprovalDetailsMuted
                            )
                        }

                        if (showLeaveRecords) {
                            HorizontalDivider(color = ApprovalDetailsBorder)
                            Column(
                                modifier = Modifier.padding(18.dp),
                                verticalArrangement = Arrangement.spacedBy(16.dp)
                            ) {
                                if (summary == null) {
                                    Text("No leave balance summary available.", color = ApprovalDetailsMuted)
                                } else {
                                    LeaveRecordsBalanceGrid(summary)
                                }

                                Text(
                                    text = "Previous Records",
                                    style = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold
                                )

                                if (previousRecords.isEmpty()) {
                                    Text("No previous leave records found.", color = ApprovalDetailsMuted)
                                } else {
                                    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                        previousRecords.forEach { record ->
                                            PreviousLeaveRecordCard(record)
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
}

@Composable
private fun ApprovalDetailRow(
    label: String,
    value: String
) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = ApprovalDetailsMuted)
        Text(value, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onBackground)
    }
}

@Composable
private fun LeaveRecordsBalanceGrid(summary: LeaveBalanceSummary) {
    val balances = listOf(
        ApprovalBalanceTileData(summary.alLeft.toString(), "AL Left"),
        ApprovalBalanceTileData(summary.wfhMonthlyLeft.toString(), "WFH (Monthly) Left"),
        ApprovalBalanceTileData(summary.mcTaken.toString(), "MC Taken"),
        ApprovalBalanceTileData(summary.elTaken.toString(), "EL Taken"),
        ApprovalBalanceTileData(summary.clTaken.toString(), "CL Taken"),
        ApprovalBalanceTileData(summary.comptimeHour.toString(), "Comptime Hour")
    )

    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        balances.chunked(3).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                row.forEach { tile ->
                    ApprovalBalanceTile(tile = tile, modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

private data class ApprovalBalanceTileData(
    val value: String,
    val label: String
)

@Composable
private fun ApprovalBalanceTile(
    tile: ApprovalBalanceTileData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.38f)
        ),
        border = androidx.compose.foundation.BorderStroke(
            1.dp,
            MaterialTheme.colorScheme.outline.copy(alpha = 0.10f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(82.dp)
                .padding(horizontal = 8.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = tile.value,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = tile.label,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 11.sp,
                lineHeight = 13.sp,
                textAlign = TextAlign.Center,
                color = ApprovalDetailsMuted,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
private fun PreviousLeaveRecordCard(record: EmployeeLeaveRecord) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, ApprovalDetailsBorder)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = record.leaveDateDisplay,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.weight(1f)
                )
                StatusBadge(status = record.status)
            }
            Text(
                text = record.leaveType.standardizedDisplayName,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Medium
            )
            if (!record.subtitle.isNullOrBlank()) {
                Text(
                    text = record.subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = ApprovalDetailsMuted
                )
            }
        }
    }
}
