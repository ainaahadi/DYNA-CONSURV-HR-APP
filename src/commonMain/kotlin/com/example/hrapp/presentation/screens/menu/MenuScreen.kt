package com.example.hrapp.presentation.screens.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

private val MenuBackground = Color(0xFFF4F7FE)
private val MenuSelected = Color(0xFFD9E4FB)
private val MenuText = Color(0xFF3D4B66)
private val MenuAccent = Color(0xFF3E61AD)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MenuScreen(
    onBack: () -> Unit = {},
    onDashboard: () -> Unit = {},
    onApplyLeave: () -> Unit = {},
    onLeaveList: () -> Unit = {},
    onCalendar: () -> Unit = {},
    onSettings: () -> Unit = {},
    onLogout: () -> Unit = {},
    selectedItem: String = "Dashboard"
) {
    val menuItems = listOf(
        "Dashboard" to onDashboard,
        "Apply Leave" to onApplyLeave,
        "List of Leaves" to onLeaveList,
        "Calendar" to onCalendar,
        "Settings" to onSettings,
        "Log Out" to onLogout
    )

    Scaffold(
        containerColor = MenuBackground,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Menu and settings",
                        fontWeight = FontWeight.SemiBold,
                        color = MenuAccent
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MenuBackground)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MenuBackground)
                .padding(padding)
                .padding(horizontal = 20.dp, vertical = 24.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            menuItems.forEach { (label, action) ->
                MenuRow(
                    label = label,
                    selected = label == selectedItem,
                    onClick = action
                )
            }
        }
    }
}

@Composable
private fun MenuRow(
    label: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (selected) MenuSelected else Color.Transparent
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp, vertical = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.bodyLarge,
                color = MenuText,
                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
            )
        }
    }
}
