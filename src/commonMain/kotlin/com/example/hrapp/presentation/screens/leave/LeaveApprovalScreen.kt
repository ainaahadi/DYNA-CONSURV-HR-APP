package com.example.hrapp.presentation.screens.leave

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hrapp.data.repository.MockApprovalRepository
import com.example.hrapp.domain.model.LeaveApprovalRequest
import com.example.hrapp.presentation.components.StatusBadge
import com.example.hrapp.presentation.components.EmployeeAvatar

private val ApprovalBackground = Color(0xFFF5F7FD)
private val ApprovalBorder = Color(0xFFDCE3F0)
private val ApprovalPrimary = Color(0xFF234AA7)
private val ApprovalMuted = Color(0xFF7D8699)
private val ApprovalDanger = Color(0xFFEB6B55)
private val ApprovalSuccess = Color(0xFF7CB769)

@Composable
fun LeaveApprovalScreen(
    onOpenRequest: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    var searchQuery by remember { mutableStateOf("") }

    val requests = MockApprovalRepository.getPendingApprovalRequests(searchQuery)
    val pendingCount = MockApprovalRepository.pendingApprovalCount

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(ApprovalBackground),
        contentPadding = PaddingValues(bottom = 24.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                border = androidx.compose.foundation.BorderStroke(1.dp, ApprovalBorder)
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Text(
                        text = "Approval",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (pendingCount > 0) {
                            "$pendingCount pending leave request(s) are waiting for review."
                        } else {
                            "No pending leave approvals at the moment."
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = ApprovalMuted
                    )
                }
            }
        }

        item {
            ApprovalSearchBar(
                query = searchQuery,
                onQueryChange = { searchQuery = it }
            )
        }

        if (requests.isEmpty()) {
            item { ApprovalEmptyState() }
        } else {
            items(requests, key = { it.id }) { request ->
                LeaveApprovalCard(
                    request = request,
                    onOpen = { onOpenRequest(request.id) },
                    onApprove = { MockApprovalRepository.approveRequest(request.id) },
                    onReject = { MockApprovalRepository.rejectRequest(request.id) }
                )
            }
        }
    }
}

@Composable
private fun ApprovalSearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null, tint = ApprovalMuted)
        },
        placeholder = {
            Text("Search employee, leave type or reason")
        },
        singleLine = true
    )
}

@Composable
private fun LeaveApprovalCard(
    request: LeaveApprovalRequest,
    onOpen: () -> Unit,
    onApprove: () -> Unit,
    onReject: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onOpen),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, ApprovalBorder)
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                EmployeeAvatar(name = request.employeeName, size = 42.dp)
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(request.employeeName, style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Text(request.leaveType.displayName, color = ApprovalPrimary, style = MaterialTheme.typography.bodySmall)
                }
                StatusBadge(status = request.status)
            }

            Spacer(modifier = Modifier.height(14.dp))
            Text(request.leaveDateDisplay, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.SemiBold)
            Text("Applied on ${request.appliedDate}", style = MaterialTheme.typography.bodySmall, color = ApprovalMuted)
            Spacer(modifier = Modifier.height(8.dp))
            Text(request.purpose, style = MaterialTheme.typography.bodyMedium, color = ApprovalMuted)
            Spacer(modifier = Modifier.height(14.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                CompactApprovalButton(
                    text = "Reject",
                    borderColor = ApprovalDanger,
                    textColor = ApprovalDanger,
                    onClick = onReject,
                    modifier = Modifier.weight(1f)
                )
                CompactApprovalButton(
                    text = "Approve",
                    borderColor = ApprovalSuccess,
                    textColor = ApprovalSuccess,
                    onClick = onApprove,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun CompactApprovalButton(
    text: String,
    borderColor: Color,
    textColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier.height(42.dp),
        shape = RoundedCornerShape(12.dp),
        border = androidx.compose.foundation.BorderStroke(1.2.dp, borderColor)
    ) {
        Text(text = text, color = textColor, fontWeight = FontWeight.SemiBold)
    }
}

@Composable
private fun ApprovalEmptyState() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = androidx.compose.foundation.BorderStroke(1.dp, ApprovalBorder)
    ) {
        androidx.compose.foundation.layout.Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(28.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No pending approval requests match the current filter.",
                color = ApprovalMuted,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
