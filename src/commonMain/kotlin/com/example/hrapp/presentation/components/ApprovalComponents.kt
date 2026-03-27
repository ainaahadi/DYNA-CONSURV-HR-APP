package com.example.hrapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.hrapp.domain.model.ApprovalStatus
import com.example.hrapp.domain.model.LeaveStatus

@Composable
fun ApprovalBadgedBox(
    count: Int,
    content: @Composable () -> Unit
) {
    if (count > 0) {
        BadgedBox(
            badge = {
                ApprovalCountBadge(count = count)
            }
        ) {
            content()
        }
    } else {
        content()
    }
}

@Composable
fun ApprovalCountBadge(
    count: Int,
    modifier: Modifier = Modifier
) {
    Badge(
        modifier = modifier,
        containerColor = Color(0xFFD5394D),
        contentColor = Color.White
    ) {
        Text(
            text = if (count > 99) "99+" else count.toString(),
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold
        )
    }
}


@Composable
fun EmployeeAvatar(
    name: String,
    modifier: Modifier = Modifier,
    size: Dp = 46.dp
) {
    val initials = name
        .split(" ")
        .filter { it.isNotBlank() }
        .take(2)
        .joinToString("") { it.first().uppercase() }

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.14f)),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initials,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun StatusBadge(
    status: ApprovalStatus,
    modifier: Modifier = Modifier
) {
    StatusBadge(
        style = status.toBadgeStyle(),
        modifier = modifier
    )
}

@Composable
fun StatusBadge(
    status: LeaveStatus,
    modifier: Modifier = Modifier
) {
    StatusBadge(
        style = status.toBadgeStyle(),
        modifier = modifier
    )
}

private fun ApprovalStatus.toBadgeStyle(): StatusBadgeStyle = when (this) {
    ApprovalStatus.PENDING -> StatusBadgeStyle(
        label = "Pending",
        backgroundColor = Color(0xFFFFF3E0),
        contentColor = Color(0xFFEF6C00),
        icon = Icons.Default.Schedule
    )
    ApprovalStatus.APPROVED -> StatusBadgeStyle(
        label = "Approved",
        backgroundColor = Color(0xFFE8F5E9),
        contentColor = Color(0xFF2E7D32),
        icon = Icons.Default.Check
    )
    ApprovalStatus.REJECTED -> StatusBadgeStyle(
        label = "Declined",
        backgroundColor = Color(0xFFFDECEA),
        contentColor = Color(0xFFC62828),
        icon = Icons.Default.Close
    )
    ApprovalStatus.CANCELLED -> StatusBadgeStyle(
        label = "Cancelled",
        backgroundColor = Color(0xFFF2F2F2),
        contentColor = Color(0xFF616161),
        icon = Icons.Default.Remove
    )
}

private fun LeaveStatus.toBadgeStyle(): StatusBadgeStyle = when (this) {
    LeaveStatus.PENDING -> StatusBadgeStyle(
        label = "Pending",
        backgroundColor = Color(0xFFFFF3E0),
        contentColor = Color(0xFFEF6C00),
        icon = Icons.Default.Schedule
    )
    LeaveStatus.APPROVED -> StatusBadgeStyle(
        label = "Approved",
        backgroundColor = Color(0xFFE8F5E9),
        contentColor = Color(0xFF2E7D32),
        icon = Icons.Default.Check
    )
    LeaveStatus.REJECTED -> StatusBadgeStyle(
        label = "Declined",
        backgroundColor = Color(0xFFFDECEA),
        contentColor = Color(0xFFC62828),
        icon = Icons.Default.Close
    )
    LeaveStatus.CANCELLED -> StatusBadgeStyle(
        label = "Cancelled",
        backgroundColor = Color(0xFFF2F2F2),
        contentColor = Color(0xFF616161),
        icon = Icons.Default.Remove
    )
}

@Composable
private fun StatusBadge(
    style: StatusBadgeStyle,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(999.dp),
        color = style.backgroundColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = style.icon,
                contentDescription = null,
                tint = style.contentColor,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.size(6.dp))
            Text(
                text = style.label,
                color = style.contentColor,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp
            )
        }
    }
}

private data class StatusBadgeStyle(
    val label: String,
    val backgroundColor: Color,
    val contentColor: Color,
    val icon: ImageVector
)
