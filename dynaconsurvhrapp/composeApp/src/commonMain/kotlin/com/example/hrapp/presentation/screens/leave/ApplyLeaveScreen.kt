package com.example.hrapp.presentation.screens.leave

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hrapp.presentation.components.HRPrimaryButton
import com.example.hrapp.domain.model.LeaveRequest
import com.example.hrapp.domain.model.LeaveStatus
import com.example.hrapp.domain.model.MockLeaveData
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApplyLeaveScreen(onBack: () -> Unit) {
    var leaveType by remember { mutableStateOf("") }
    var expandedLeaveType by remember { mutableStateOf(false) }
    val leaveTypes = listOf("Annual Leave", "Work From Home", "Medical Certificate", "Emergency Leave", "Comptime")

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

    var reason by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Submit Request", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold) },
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
            // Leave Type Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Leave Type *", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
                            modifier = Modifier.fillMaxWidth().menuAnchor(),
                            shape = RoundedCornerShape(8.dp)
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

                    if (leaveType == "Comptime") {
                        Spacer(modifier = Modifier.height(16.dp))
                        Text("Target Company/Project *", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        Spacer(modifier = Modifier.height(8.dp))
                        ExposedDropdownMenuBox(
                            expanded = expandedCompCompany,
                            onExpandedChange = { expandedCompCompany = !expandedCompCompany }
                        ) {
                            OutlinedTextField(
                                value = compCompany,
                                onValueChange = {},
                                readOnly = true,
                                placeholder = { Text("Select Target") },
                                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedCompCompany) },
                                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                                modifier = Modifier.fillMaxWidth().menuAnchor(),
                                shape = RoundedCornerShape(8.dp)
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
                        Text(
                            "Select which project accounts for this comptime balance.",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                            modifier = Modifier.padding(top = 4.dp)
                        )
                    }
                }
            }

            // Timeline Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Timeline", style = MaterialTheme.typography.titleSmall, fontWeight = FontWeight.Bold)
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text("From Date *", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = fromDate,
                                onValueChange = {},
                                readOnly = true,
                                leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(18.dp)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                textStyle = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Session *", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
                                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                                    shape = RoundedCornerShape(8.dp),
                                    textStyle = MaterialTheme.typography.bodyMedium
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
                            Text("To Date *", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Spacer(modifier = Modifier.height(8.dp))
                            OutlinedTextField(
                                value = toDate,
                                onValueChange = {},
                                readOnly = true,
                                leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null, modifier = Modifier.size(18.dp)) },
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(8.dp),
                                textStyle = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Column(modifier = Modifier.weight(1f)) {
                            Text("Session *", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
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
                                    modifier = Modifier.fillMaxWidth().menuAnchor(),
                                    shape = RoundedCornerShape(8.dp),
                                    textStyle = MaterialTheme.typography.bodyMedium
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
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Info, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer, modifier = Modifier.size(20.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                "Standard public holidays will automatically be excluded from duration.",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                fontWeight = FontWeight.Medium
                            )
                        }
                    }
                }
            }

            // Reason Section
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Reason / Justification *", style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = reason,
                        onValueChange = { reason = it },
                        placeholder = { Text("Provide details for your request...") },
                        modifier = Modifier.fillMaxWidth().heightIn(min = 120.dp),
                        shape = RoundedCornerShape(8.dp)
                    )
                    Text(
                        "Min 10 chars",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
                    )
                }
            }

            var showError by remember { mutableStateOf(false) }
            var errorMessage by remember { mutableStateOf("") }
            
            if (showError) {
                Text(
                    text = errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
            }

            HRPrimaryButton(
                text = "SUBMIT REQUEST",
                onClick = {
                    if (leaveType.isBlank() || fromDate == "Select Date" || toDate == "Select Date" || reason.length < 10) {
                        errorMessage = "Please fill in all required fields and ensure reason is at least 10 characters."
                        showError = true
                        return@HRPrimaryButton
                    }
                    if (leaveType == "Comptime" && compCompany.isBlank()) {
                        errorMessage = "Please select a target company for Comptime."
                        showError = true
                        return@HRPrimaryButton
                    }

                    showError = false

                    // Simple mock ID generation
                    val newId = (MockLeaveData.requests.maxOfOrNull { it.id } ?: 0) + 1
                    
                    val icon = when (leaveType) {
                        "Annual Leave" -> "🌴"
                        "Work From Home" -> "🏠"
                        "Medical Certificate" -> "🤒"
                        "Emergency Leave" -> "⚠️"
                        "Comptime" -> "⏳"
                        else -> "📝"
                    }

                    val typeCode = when (leaveType) {
                        "Annual Leave" -> "annual"
                        "Work From Home" -> "wfh"
                        "Medical Certificate" -> "mc"
                        "Emergency Leave" -> "el"
                        "Comptime" -> "comp"
                        else -> "other"
                    }
                    
                    val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                    val appliedDateStr = "${today.year}-${today.monthNumber.toString().padStart(2, '0')}-${today.dayOfMonth.toString().padStart(2, '0')}"

                    val newRequest = LeaveRequest(
                        id = newId,
                        type = typeCode,
                        typeName = leaveType,
                        icon = icon,
                        startDate = fromDate, // In a real app we'd parse this
                        endDate = toDate,
                        dateDisplay = "$fromDate - $toDate", 
                        days = 1, // Mocked duration
                        // If it's comptime we append the target company
                        reason = if (leaveType == "Comptime") "[$compCompany] $reason" else reason,
                        status = LeaveStatus.PENDING,
                        appliedDate = appliedDateStr
                    )
                    
                    MockLeaveData.addRequest(newRequest)
                    onBack()
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}
