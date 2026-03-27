package com.example.hrapp.presentation.screens.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Announcement
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.hrapp.data.repository.MockApprovalRepository
import com.example.hrapp.presentation.components.ApprovalBadgedBox
import com.example.hrapp.presentation.screens.announcements.AnnouncementsScreen
import com.example.hrapp.presentation.screens.attendance.AttendanceScreen
import com.example.hrapp.presentation.screens.attendance.QRScannerScreen
import com.example.hrapp.presentation.screens.calendar.CalendarScreen
import com.example.hrapp.presentation.screens.home.HomeScreen
import com.example.hrapp.presentation.screens.leave.ApplyLeaveScreen
import com.example.hrapp.presentation.screens.leave.LeaveApprovalDetailsScreen
import com.example.hrapp.presentation.screens.leave.LeaveModuleScreen
import com.example.hrapp.presentation.screens.leave.LeaveModuleSection
import com.example.hrapp.presentation.screens.menu.MenuScreen
import com.example.hrapp.presentation.screens.notifications.NotificationsScreen
import com.example.hrapp.presentation.screens.profile.ProfileScreen
import com.example.hrapp.presentation.screens.settings.SettingsScreen

private const val LeaveApprovalRoute = "leave_approval"
private const val LeaveApprovalDetailsRoute = "leave_approval_details"

private fun leaveApprovalDetailsRoute(requestId: Int): String = "$LeaveApprovalDetailsRoute/$requestId"

sealed class Screen(val route: String, val label: String, val icon: ImageVector) {
    object Home : Screen("home", "Home", Icons.Default.Home)
    object Attendance : Screen("attendance", "Attend", Icons.Default.QrCodeScanner)
    object Leave : Screen("leave", "Leave", Icons.Default.DateRange)
    object Announcements : Screen("announcements", "News", Icons.AutoMirrored.Filled.Announcement)
    object Settings : Screen("settings", "Settings", Icons.Default.Settings)
}

@Composable
fun MainScreen(
    onLogout: () -> Unit = {}
) {
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
    val currentRoute = currentDestination?.route
    val leavePendingCount = MockApprovalRepository.pendingApprovalCount
    val showBottomBar = items.any { it.route == currentRoute } || currentRoute == LeaveApprovalRoute

    val navigateToRoot: (String) -> Unit = { route ->
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
            launchSingleTop = true
            restoreState = true
        }
    }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar(
                    containerColor = Color(0xFFF7F2FC),
                    tonalElevation = 0.dp
                ) {
                    items.forEach { screen ->
                        val selected = when (screen) {
                            Screen.Leave -> currentRoute == Screen.Leave.route || currentRoute == LeaveApprovalRoute
                            else -> currentDestination?.hierarchy?.any { it.route == screen.route } == true
                        }

                        NavigationBarItem(
                            icon = {
                                if (screen == Screen.Leave) {
                                    ApprovalBadgedBox(count = leavePendingCount) {
                                        Icon(screen.icon, contentDescription = null)
                                    }
                                } else {
                                    Icon(screen.icon, contentDescription = null)
                                }
                            },
                            label = { Text(screen.label) },
                            selected = selected,
                            onClick = { navigateToRoot(screen.route) },
                            colors = NavigationBarItemDefaults.colors(
                                selectedIconColor = Color(0xFF37225C),
                                selectedTextColor = Color(0xFF37225C),
                                unselectedIconColor = Color(0xFF655C73),
                                unselectedTextColor = Color(0xFF655C73),
                                indicatorColor = Color(0xFFE5D9F8)
                            )
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController, startDestination = Screen.Home.route, modifier = Modifier.padding(innerPadding)) {
            composable(Screen.Home.route) {
                HomeScreen(
                    onNavigateToAnnouncements = { navController.navigate("notifications") },
                    onNavigateToSettings = { navigateToRoot(Screen.Settings.route) },
                    onNavigateToMenu = { navController.navigate("menu") }
                )
            }
            composable(Screen.Attendance.route) {
                AttendanceScreen(
                    onNavigateToQRScanner = { navController.navigate("qr_scanner") },
                    onBack = { navigateToRoot(Screen.Home.route) }
                )
            }
            composable(Screen.Leave.route) {
                LeaveModuleScreen(
                    onApplyLeave = { navController.navigate("apply_leave") },
                    onOpenApprovalDetails = { requestId ->
                        navController.navigate(leaveApprovalDetailsRoute(requestId))
                    },
                    onBack = { navigateToRoot(Screen.Home.route) }
                )
            }
            composable(LeaveApprovalRoute) {
                LeaveModuleScreen(
                    onApplyLeave = { navController.navigate("apply_leave") },
                    onOpenApprovalDetails = { requestId ->
                        navController.navigate(leaveApprovalDetailsRoute(requestId))
                    },
                    onBack = { navController.popBackStack() },
                    initialSection = LeaveModuleSection.APPROVAL
                )
            }
            composable(
                route = "$LeaveApprovalDetailsRoute/{requestId}",
                arguments = listOf(navArgument("requestId") { type = NavType.IntType })
            ) { backStackEntry ->
                val requestId = backStackEntry.arguments?.getInt("requestId") ?: return@composable
                LeaveApprovalDetailsScreen(
                    requestId = requestId,
                    onBack = { navController.popBackStack() }
                )
            }
            composable("apply_leave") {
                ApplyLeaveScreen(onBack = { navController.popBackStack() })
            }
            composable("qr_scanner") {
                QRScannerScreen(onBack = { navController.popBackStack() })
            }
            composable("menu") {
                MenuScreen(
                    onBack = { navController.popBackStack() },
                    onDashboard = { navigateToRoot(Screen.Home.route) },
                    onApplyLeave = { navController.navigate("apply_leave") },
                    onLeaveList = { navigateToRoot(Screen.Leave.route) },
                    onCalendar = { navController.navigate("calendar") },
                    onSettings = { navigateToRoot(Screen.Settings.route) },
                    onLogout = onLogout
                )
            }
            composable("notifications") {
                NotificationsScreen(
                    onBack = { navController.popBackStack() },
                    onOpenApprovalCenter = { navController.navigate(LeaveApprovalRoute) },
                    onOpenApprovalDetails = { requestId ->
                        navController.navigate(leaveApprovalDetailsRoute(requestId))
                    }
                )
            }
            composable(Screen.Announcements.route) {
                AnnouncementsScreen(onBack = { navigateToRoot(Screen.Home.route) })
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
