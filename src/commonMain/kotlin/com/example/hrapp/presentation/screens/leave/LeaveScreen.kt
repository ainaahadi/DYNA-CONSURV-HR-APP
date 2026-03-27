package com.example.hrapp.presentation.screens.leave

import androidx.compose.runtime.Composable

@Composable
fun LeaveScreen(
    onApplyLeave: () -> Unit,
    onBack: () -> Unit = {}
) {
    LeaveModuleScreen(
        onApplyLeave = onApplyLeave,
        onOpenApprovalDetails = {},
        onBack = onBack,
        initialSection = LeaveModuleSection.PERSONAL
    )
}
