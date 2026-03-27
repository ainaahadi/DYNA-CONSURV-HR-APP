package com.example.hrapp.presentation.screens.leave

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hrapp.domain.model.LeaveRequest
import com.example.hrapp.presentation.components.StatusBadge

private val Primary = Color(0xFF234AA7)
private val Muted = Color(0xFF7D8699)
private val Border = Color(0xFFDCE3F0)

data class BalanceInfo(val value: String, val label: String, val color: Color)

@Composable
fun BalanceGrid() {
    val balances = listOf(
        BalanceInfo("12", "AL Left", Color(0xFF4F93FF)),
        BalanceInfo("03", "WFH Left", Color(0xFFB65BCF)),
        BalanceInfo("05", "MC Taken", Color(0xFFFF6D63)),
        BalanceInfo("01", "EL Taken", Color(0xFFFFB547)),
        BalanceInfo("00", "CL Taken", Color(0xFF28C3D4)),
        BalanceInfo("08", "Comp Hrs", Color(0xFF987765))
    )
    Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
        balances.chunked(3).forEach { row ->
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                row.forEach { info -> BalanceCard(info, Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
fun BalanceCard(info: BalanceInfo, modifier: Modifier = Modifier) {
    Card(modifier = modifier, colors = CardDefaults.cardColors(containerColor = info.color)) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .height(78.dp)
                .padding(horizontal = 6.dp, vertical = 10.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = info.value,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = info.label,
                color = Color.White.copy(alpha = 0.92f),
                fontSize = 11.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}

@Composable
fun LeaveHistoryCard(request: LeaveRequest, onViewDetails: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        border = BorderStroke(1.dp, Border),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(request.typeName, fontWeight = FontWeight.Bold, modifier = Modifier.weight(1f))
                StatusBadge(request.status)
            }
            Spacer(modifier = Modifier.height(6.dp))
            Text(request.dateDisplay)
            Text(request.appliedDate, color = Muted, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(request.purpose, color = Muted, fontSize = 12.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text("Details", color = Primary, fontWeight = FontWeight.Medium, modifier = Modifier.clickable(onClick = onViewDetails))
        }
    }
}
