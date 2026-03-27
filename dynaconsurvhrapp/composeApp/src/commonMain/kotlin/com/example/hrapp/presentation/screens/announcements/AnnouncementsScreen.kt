package com.example.hrapp.presentation.screens.announcements

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementsScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("News & Updates") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Tune, contentDescription = "Filter")
                    }
                }
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(MaterialTheme.colorScheme.background)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item { SearchBar() }
            items(getMockNews()) { news ->
                NewsCard(news)
            }
            item {
                Box(modifier = Modifier.fillMaxWidth().padding(24.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(modifier = Modifier.size(24.dp), strokeWidth = 2.dp)
                }
            }
            item { Spacer(modifier = Modifier.height(80.dp)) }
        }
    }
}

@Composable
fun SearchBar() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Search, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.width(12.dp))
            TextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Search announcements...", fontSize = 16.sp) },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                singleLine = true
            )
            IconButton(onClick = {}) {
                Icon(Icons.Default.FilterList, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}

@Composable
fun NewsCard(news: NewsItem) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(news.iconBg.copy(alpha = 0.1f)),
                    contentAlignment = Alignment.Center
                ) {
                    if (news.iconText != null) {
                        Text(news.iconText, fontSize = 24.sp)
                    } else if (news.iconImage != null) {
                        Icon(news.iconImage, contentDescription = null, tint = news.iconBg, modifier = Modifier.size(24.dp))
                    }
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(if (news.isUrgent) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.surfaceVariant)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            news.tag,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (news.isUrgent) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(news.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(news.date, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f))
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                news.body,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                lineHeight = 22.sp
            )
            if (news.actionText != null) {
                TextButton(
                    onClick = {},
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(news.actionText, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary, fontSize = 14.sp)
                }
            }
        }
    }
}

data class NewsItem(
    val id: Int,
    val iconText: String? = null,
    val iconImage: androidx.compose.ui.graphics.vector.ImageVector? = null,
    val iconBg: Color,
    val tag: String,
    val isUrgent: Boolean = false,
    val title: String,
    val date: String,
    val body: String,
    val actionText: String? = null
)

fun getMockNews() = listOf(
    NewsItem(
        1, "🎉", null, Color(0xFF66BB6A), "General", false,
        "Company Wide Holiday Notice", "Dec 20, 2025",
        "Please be informed that December 25th will be observed as a paid corporate holiday. All offices will remain closed. Teams working on active client SLA rotations should refer to the on-call schedule posted on the intranet...",
        "Read Full Memo"
    ),
    NewsItem(
        2, "📋", null, Color(0xFFFFA726), "Important Policy", true,
        "FY26 Work From Home Policy", "Dec 15, 2025",
        "We are instituting a new limit on remote work starting in Jan FY26. WFH requests are now capped at 3 per month and must be logged 48 hours in advance through the portal. Over-utilization will automatically block the form options...",
        "Review Guidelines"
    ),
    NewsItem(
        3, null, Icons.Default.RocketLaunch, Color(0xFF42A5F5), "Product Update", false,
        "New Internal Toolkit Launched", "Dec 01, 2025",
        "The IT department has officially deployed the new developer toolkit which can be found in your standard applications folder. Ensure you restart your machines by end of week to receive the patch..."
    )
)
