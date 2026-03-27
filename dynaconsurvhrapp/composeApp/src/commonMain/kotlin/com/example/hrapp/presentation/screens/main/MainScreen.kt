package com.example.hrapp.presentation.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.hrapp.presentation.screens.announcements.AnnouncementsScreen
import com.example.hrapp.presentation.screens.attendance.AttendanceScreen
import com.example.hrapp.presentation.screens.attendance.QRScannerScreen
import com.example.hrapp.presentation.screens.home.HomeScreen
import com.example.hrapp.presentation.screens.leave.ApplyLeaveScreen
import com.example.hrapp.presentation.screens.leave.LeaveScreen
import com.example.hrapp.presentation.screens.profile.ProfileScreen
import com.example.hrapp.presentation.screens.settings.SettingsScreen
import com.example.hrapp.presentation.screens.calendar.CalendarScreen

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Attendance : Screen("attendance", "Attend", Icons.Default.QrCodeScanner)
    object Leave : Screen("leave", "Leave", Icons.Default.DateRange)
    object Announcements : Screen("announcements", "News", Icons.Default.Announcement)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val items = listOf(
        Screen.Home,
        Screen.Attendance,
        Screen.Leave,
        Screen.Announcements,
        Screen.Settings
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val showBottomBar = items.any { it.route == currentDestination?.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    items.forEach { screen ->
                        NavigationBarItem(
                            icon = { Icon(screen.icon, contentDescription = null) },
                            label = { Text(screen.label) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Home.route, modifier = Modifier.padding(innerPadding)) {
            composable(Screen.Home.route) { 
                HomeScreen(
                    onNavigateToQRScanner = { navController.navigate("qr_scanner") },
                    onNavigateToApplyLeave = { navController.navigate("apply_leave") },
                    onNavigateToAnnouncements = { navController.navigate(Screen.Announcements.route) },
                    onNavigateToCalendar = { navController.navigate("calendar") }
                ) 
            }
            composable(Screen.Attendance.route) { 
                AttendanceScreen(
                    onNavigateToQRScanner = { navController.navigate("qr_scanner") }
                ) 
            }
            composable(Screen.Leave.route) { 
                LeaveScreen(
                    onApplyLeave = {
                        navController.navigate("apply_leave")
                    }
                ) 
            }
            composable("apply_leave") {
                ApplyLeaveScreen(onBack = { navController.popBackStack() })
            }
            composable("qr_scanner") {
                QRScannerScreen(onBack = { navController.popBackStack() })
            }
            composable(Screen.Announcements.route) { 
                AnnouncementsScreen() 
            }
            composable(Screen.Settings.route) { 
                SettingsScreen() 
            }
            composable(Screen.Home.route + "/profile") { 
                ProfileScreen() 
            }
            composable("calendar") {
                CalendarScreen(onBack = { navController.popBackStack() })
            }
        }
    }
}
