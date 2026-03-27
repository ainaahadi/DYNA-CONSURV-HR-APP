package com.example.hrapp.presentation.screens.leave

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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.hrapp.domain.model.LeaveRequest
import com.example.hrapp.domain.model.LeaveStatus
import com.example.hrapp.domain.model.MockLeaveData
import com.example.hrapp.presentation.components.HRPrimaryButton
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

private enum class LeaveDateField {
    FROM, TO
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplyLeaveScreen(onBack: () -> Unit) {
    var leaveType by remember { mutableStateOf("") }
    var expandedLeaveType by remember { mutableStateOf(false) }
    val leaveTypes = listOf(
        "Annual Leave",
        "Medical Claim",
        "Emergency Leave",
        "Compassionate Leave",
        "Comp Time (Claim)",
        "Comp Time (Register)"
    )
    val requiresCompTarget = leaveType == "Comp Time (Claim)" || leaveType == "Comp Time (Register)"

    var compCompany by remember { mutableStateOf("") }
    var expandedCompCompany by remember { mutableStateOf(false) }
    val compCompanies = listOf("Internal", "Client Alpha", "Client Beta")

    var fromDate by remember { mutableStateOf("Select Date") }
    var fromSession by remember { mutableStateOf("Full Day") }
    var expandedFromSession by remember { mutableStateOf(false) }

    var toDate by remember { mutableStateOf("Select Date") }
    var toSession by remember { mutableStateOf("Full Day") }
    var expandedToSession by remember { mutableStateOf(false) }

    val sessions = listOf("Full Day", "Morning (AM)", "Evening (PM)")
    val dateOptions = remember {
        listOf(
            "Apr 10, 2024",
            "Apr 11, 2024",
            "Apr 12, 2024",
            "Apr 15, 2024",
            "Apr 16, 2024",
            "Apr 17, 2024"
        )
    }

    var reason by remember { mutableStateOf("") }
    var activeDateField by remember { mutableStateOf<LeaveDateField?>(null) }
    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Apply Leave",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
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
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Leave Type",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    ExposedDropdownMenuBox(
                        expanded = expandedLeaveType,
                        onExpandedChange = { expandedLeaveType = !expandedLeaveType }
                    ) {
                        OutlinedTextField(
                            value = leaveType,
                            onValueChange = {},
                            readOnly = true,
                            placeholder = { Text("Select application type") },
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedLeaveType) },
                            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                            shape = RoundedCornerShape(12.dp)
                        )
                        ExposedDropdownMenu(
                            expanded = expandedLeaveType,
                            onDismissRequest = { expandedLeaveType = false }
                        ) {
                            leaveTypes.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(type) },
                                    onClick = {
                                        leaveType = type
                                        expandedLeaveType = false
                                    }
                                )
                            }
                        }
                    }

                    if (requiresCompTarget) {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            "Target Company / Project",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        ExposedDropdownMenuBox(
                            expanded = expandedCompCompany,
                            onExpandedChange = { expandedCompCompany = !expandedCompCompany }
                        ) {
                            OutlinedTextField(
                                value = compCompany,
                                onValueChange = {},
                                readOnly = true,
                                placeholder = { Text("Select target") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCompCompany) },
                                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                                shape = RoundedCornerShape(12.dp)
                            )
                            ExposedDropdownMenu(
                                expanded = expandedCompCompany,
                                onDismissRequest = { expandedCompCompany = false }
                            ) {
                                compCompanies.forEach { company ->
                                    DropdownMenuItem(
                                        text = { Text(company) },
                                        onClick = {
                                            compCompany = company
                                            expandedCompCompany = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Timeline", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("From", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = fromDate,
                                onValueChange = {},
                                readOnly = true,
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.CalendarToday,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { activeDateField = LeaveDateField.FROM },
                                shape = RoundedCornerShape(12.dp)
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Session", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(8.dp))
                            ExposedDropdownMenuBox(
                                expanded = expandedFromSession,
                                onExpandedChange = { expandedFromSession = !expandedFromSession }
                            ) {
                                OutlinedTextField(
                                    value = fromSession,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedFromSession) },
                                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                ExposedDropdownMenu(
                                    expanded = expandedFromSession,
                                    onDismissRequest = { expandedFromSession = false }
                                ) {
                                    sessions.forEach { session ->
                                        DropdownMenuItem(
                                            text = { Text(session) },
                                            onClick = {
                                                fromSession = session
                                                expandedFromSession = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("To", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = toDate,
                                onValueChange = {},
                                readOnly = true,
                                leadingIcon = {
                                    Icon(
                                        Icons.Default.CalendarToday,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp)
                                    )
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { activeDateField = LeaveDateField.TO },
                                shape = RoundedCornerShape(12.dp)
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Session", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(8.dp))
                            ExposedDropdownMenuBox(
                                expanded = expandedToSession,
                                onExpandedChange = { expandedToSession = !expandedToSession }
                            ) {
                                OutlinedTextField(
                                    value = toSession,
                                    onValueChange = {},
                                    readOnly = true,
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedToSession) },
                                    colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .menuAnchor(MenuAnchorType.PrimaryNotEditable),
                                    shape = RoundedCornerShape(12.dp)
                                )
                                ExposedDropdownMenu(
                                    expanded = expandedToSession,
                                    onDismissRequest = { expandedToSession = false }
                                ) {
                                    sessions.forEach { session ->
                                        DropdownMenuItem(
                                            text = { Text(session) },
                                            onClick = {
                                                toSession = session
                                                expandedToSession = false
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.Info,
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                "Public holidays are excluded automatically in this prototype.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(
                    1.dp,
                    MaterialTheme.colorScheme.outline.copy(alpha = 0.12f)
                )
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Reason",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = reason,
                        onValueChange = { reason = it },
                        placeholder = { Text("Provide details for your request...") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = 120.dp),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            if (showError) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            HRPrimaryButton(
                text = "APPLY",
                onClick = {
                    if (leaveType.isBlank() || fromDate == "Select Date" || toDate == "Select Date" || reason.length < 10) {
                        errorMessage = "Please complete leave type, dates, and a reason with at least 10 characters."
                        showError = true
                        return@HRPrimaryButton
                    }

                    if (requiresCompTarget && compCompany.isBlank()) {
                        errorMessage = "Please select a target company for Comp Time."
                        showError = true
                        return@HRPrimaryButton
                    }

                    showError = false

                    val newId = (MockLeaveData.requests.maxOfOrNull { it.id } ?: 0) + 1
                    val typeCode = when (leaveType) {
                        "Annual Leave" -> "annual"
                        "Medical Claim" -> "medical_claim"
                        "Emergency Leave" -> "emergency"
                        "Compassionate Leave" -> "compassionate"
                        "Comp Time (Claim)" -> "comp_time_claim"
                        "Comp Time (Register)" -> "comp_time_register"
                        else -> "other"
                    }

                    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    val appliedDate = buildString {
                        append(today.year)
                        append("-")
                        append(today.monthNumber.toString().padStart(2, '0'))
                        append("-")
                        append(today.dayOfMonth.toString().padStart(2, '0'))
                    }

                    val newRequest = LeaveRequest(
                        id = newId,
                        type = typeCode,
                        typeName = leaveType,
                        icon = typeCode,
                        startDate = fromDate,
                        endDate = toDate,
                        dateDisplay = "$fromDate - $toDate",
                        days = 1,
                        reason = if (requiresCompTarget) {
                            "[$compCompany] $reason"
                        } else {
                            reason
                        },
                        status = LeaveStatus.PENDING,
                        appliedDate = appliedDate
                    )

                    MockLeaveData.addRequest(newRequest)
                    onBack()
                }
            )

            Spacer(modifier = Modifier.height(20.dp))
        }
    }

    if (activeDateField != null) {
        DateSelectionDialog(
            title = if (activeDateField == LeaveDateField.FROM) "Select From Date" else "Select To Date",
            options = dateOptions,
            onDismiss = { activeDateField = null },
            onSelect = { option ->
                if (activeDateField == LeaveDateField.FROM) {
                    fromDate = option
                    if (toDate == "Select Date") {
                        toDate = option
                    }
                } else {
                    toDate = option
                }
                activeDateField = null
            }
        )
    }
}

@Composable
private fun DateSelectionDialog(
    title: String,
    options: List<String>,
    onDismiss: () -> Unit,
    onSelect: (String) -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(title, fontWeight = FontWeight.Bold) },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                options.forEach { option ->
                    Surface(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onSelect(option) },
                        shape = RoundedCornerShape(10.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)
                    ) {
                        Text(
                            text = option,
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 12.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}
