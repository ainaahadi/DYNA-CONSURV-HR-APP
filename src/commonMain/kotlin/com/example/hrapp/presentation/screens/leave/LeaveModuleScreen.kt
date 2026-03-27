package com.example.hrapp.presentation.screens.leave

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hrapp.data.repository.MockApprovalRepository
import com.example.hrapp.domain.model.LeaveRequest
import com.example.hrapp.domain.model.MockLeaveData
import com.example.hrapp.presentation.components.ApprovalBadgedBox

private val LeaveModuleBg = Color(0xFFF5F7FD)
private val LeaveModulePrimary = Color(0xFF234AA7)
private val LeaveModuleMuted = Color(0xFF7D8699)
private val LeaveModuleBorder = Color(0xFFDCE3F0)

enum class LeaveModuleSection {
    PERSONAL,
    APPROVAL
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeaveModuleScreen(
    onApplyLeave: () -> Unit,
    onOpenApprovalDetails: (Int) -> Unit,
    onBack: () -> Unit = {},
    initialSection: LeaveModuleSection = LeaveModuleSection.PERSONAL
) {
    var selectedSection by remember { mutableStateOf(initialSection) }
    val pendingApprovalCount = MockApprovalRepository.pendingApprovalCount

    Scaffold(
        containerColor = LeaveModuleBg,
        topBar = {
            TopAppBar(
                title = { Text("Leave", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = LeaveModuleBg)
            )
        },
        floatingActionButton = {
            if (selectedSection == LeaveModuleSection.PERSONAL) {
                FloatingActionButton(
                    onClick = onApplyLeave,
                    containerColor = LeaveModulePrimary,
                    contentColor = Color.White,
                    shape = CircleShape
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Apply Leave")
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(LeaveModuleBg)
        ) {
            LeaveModuleTabs(
                selectedSection = selectedSection,
                pendingCount = pendingApprovalCount,
                onSelectSection = { selectedSection = it }
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
            ) {
                when (selectedSection) {
                    LeaveModuleSection.PERSONAL -> PersonalLeaveSection()
                    LeaveModuleSection.APPROVAL -> LeaveApprovalScreen(
                        onOpenRequest = onOpenApprovalDetails,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = 20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun LeaveModuleTabs(
    selectedSection: LeaveModuleSection,
    pendingCount: Int,
    onSelectSection: (LeaveModuleSection) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        LeaveModuleTab(
            label = "Personal",
            selected = selectedSection == LeaveModuleSection.PERSONAL,
            onClick = { onSelectSection(LeaveModuleSection.PERSONAL) },
            modifier = Modifier.weight(1f)
        )
        LeaveModuleTab(
            label = "Approval",
            selected = selectedSection == LeaveModuleSection.APPROVAL,
            badgeCount = pendingCount,
            onClick = { onSelectSection(LeaveModuleSection.APPROVAL) },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun LeaveModuleTab(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    badgeCount: Int = 0
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(14.dp))
            .background(if (selected) LeaveModulePrimary else Color.White)
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        contentAlignment = Alignment.Center
    ) {
        ApprovalBadgedBox(count = badgeCount) {
            Text(
                text = label,
                color = if (selected) Color.White else LeaveModulePrimary,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun PersonalLeaveSection() {
    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedRequest by remember { mutableStateOf<LeaveRequest?>(null) }

    val historyRequests = MockLeaveData.leaveHistoryList
    val leaveRequestList = MockLeaveData.leaveRequestList
    val visibleRequests = if (selectedTab == 0) historyRequests else leaveRequestList
    val emptyMessage = if (selectedTab == 0) {
        "No leave history available"
    } else {
        "No pending leave requests"
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp)
    ) {
        item {
            Text(
                text = "Your Balances (2026)",
                style = MaterialTheme.typography.titleSmall,
                color = LeaveModuleMuted
            )
        }
        item { BalanceGrid() }
        item {
            PersonalSectionTabs(
                selectedTab = selectedTab,
                onSelectTab = { selectedTab = it }
            )
        }
        item {
            Text(
                text = if (selectedTab == 0) "Leave History" else "Leave Requests",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
        }
        item {
            if (visibleRequests.isEmpty()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = BorderStroke(1.dp, LeaveModuleBorder)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(28.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(emptyMessage, color = LeaveModuleMuted)
                    }
                }
            } else {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    visibleRequests.forEach { request ->
                        LeaveHistoryCard(
                            request = request,
                            onViewDetails = { selectedRequest = request }
                        )
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(80.dp)) }
    }

    selectedRequest?.let { request ->
        AlertDialog(
            onDismissRequest = { selectedRequest = null },
            title = { Text(request.typeName, fontWeight = FontWeight.Bold) },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Dates: ${request.dateDisplay}", color = LeaveModuleMuted)
                    Text("Duration: ${request.duration} day(s)", color = LeaveModuleMuted)
                    Text("Reason: ${request.purpose}", color = LeaveModuleMuted)
                    Text("Status: ${request.status.label}", color = LeaveModuleMuted)
                }
            },
            confirmButton = {
                TextButton(onClick = { selectedRequest = null }) {
                    Text("Close")
                }
            }
        )
    }
}

@Composable
private fun PersonalSectionTabs(
    selectedTab: Int,
    onSelectTab: (Int) -> Unit
) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            PersonalSectionTab(
                label = "Leave History",
                selected = selectedTab == 0,
                onClick = { onSelectTab(0) },
                modifier = Modifier.weight(1f)
            )
            PersonalSectionTab(
                label = "Leave Requests",
                selected = selectedTab == 1,
                onClick = { onSelectTab(1) },
                modifier = Modifier.weight(1f)
            )
        }
        HorizontalDivider(color = LeaveModuleBorder)
    }
}

@Composable
private fun PersonalSectionTab(
    label: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
            color = if (selected) LeaveModulePrimary else LeaveModuleMuted
        )
        Spacer(modifier = Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .height(3.dp)
                .fillMaxWidth()
                .background(if (selected) LeaveModulePrimary else Color.Transparent)
        )
    }
}
