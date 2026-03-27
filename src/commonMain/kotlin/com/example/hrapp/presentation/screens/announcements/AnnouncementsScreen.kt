package com.example.hrapp.presentation.screens.announcements

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Description
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.RocketLaunch
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val NewsBg = Color(0xFFF5F7FD)
private val NewsBorder = Color(0xFFDCE3F0)
private val NewsMuted = Color(0xFF7D8699)
private val NewsPrimary = Color(0xFF234AA7)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnnouncementsScreen(
    onBack: () -> Unit = {}
) {
    var searchQuery by remember { mutableStateOf("") }
    var urgentOnly by remember { mutableStateOf(false) }
    var selectedNews by remember { mutableStateOf<NewsItem?>(null) }

    val newsItems = getMockNews().filter { item ->
        val matchesQuery = searchQuery.isBlank() ||
            item.title.contains(searchQuery, ignoreCase = true) ||
            item.body.contains(searchQuery, ignoreCase = true) ||
            item.tag.contains(searchQuery, ignoreCase = true)
        val matchesUrgency = !urgentOnly || item.isUrgent
        matchesQuery && matchesUrgency
    }

    Scaffold(
        containerColor = NewsBg,
        topBar = {
            TopAppBar(
                title = { Text("News & Updates", fontWeight = FontWeight.SemiBold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = { urgentOnly = !urgentOnly }) {
                        Icon(
                            imageVector = Icons.Default.Tune,
                            contentDescription = "Toggle important announcements",
                            tint = if (urgentOnly) NewsPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = NewsBg)
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(NewsBg)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 24.dp)
        ) {
            item {
                SearchBar(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    urgentOnly = urgentOnly,
                    onToggleUrgent = { urgentOnly = !urgentOnly }
                )
            }
            if (newsItems.isEmpty()) {
                item {
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        border = BorderStroke(1.dp, NewsBorder)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(28.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No announcements match your filters.",
                                color = NewsMuted
                            )
                        }
                    }
                }
            } else {
                items(newsItems) { news ->
                    NewsCard(
                        news = news,
                        onOpen = { selectedNews = it }
                    )
                }
            }
        }
    }

    selectedNews?.let { news ->
        AlertDialog(
            onDismissRequest = { selectedNews = null },
            title = {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(news.title, fontWeight = FontWeight.Bold)
                    Text(
                        text = "${news.tag}  |  ${news.date}",
                        style = MaterialTheme.typography.bodySmall,
                        color = NewsMuted
                    )
                }
            },
            text = {
                Text(
                    text = news.body,
                    style = MaterialTheme.typography.bodyMedium,
                    color = NewsMuted,
                    lineHeight = 22.sp
                )
            },
            confirmButton = {
                TextButton(onClick = { selectedNews = null }) {
                    Text("Close", color = NewsPrimary, fontWeight = FontWeight.Bold)
                }
            }
        )
    }
}

@Composable
fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit,
    urgentOnly: Boolean,
    onToggleUrgent: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, NewsBorder)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Search,
                contentDescription = null,
                tint = NewsMuted
            )
            Spacer(modifier = Modifier.width(12.dp))
            TextField(
                value = value,
                onValueChange = onValueChange,
                placeholder = { Text("Search announcements...", fontSize = 16.sp) },
                modifier = Modifier.weight(1f),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                singleLine = true
            )
            IconButton(onClick = onToggleUrgent) {
                Icon(
                    Icons.Default.FilterList,
                    contentDescription = "Filter urgent announcements",
                    tint = if (urgentOnly) NewsPrimary else NewsMuted
                )
            }
        }
    }
}

@Composable
fun NewsCard(
    news: NewsItem,
    onOpen: (NewsItem) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onOpen(news) },
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        border = BorderStroke(1.dp, NewsBorder)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.Top) {
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(news.iconBg.copy(alpha = 0.12f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = news.iconImage,
                        contentDescription = null,
                        tint = news.iconBg,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Spacer(modifier = Modifier.width(16.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(5.dp))
                            .background(if (news.isUrgent) Color(0xFFFFE4E0) else Color(0xFFE9EEF8))
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = news.tag,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = if (news.isUrgent) Color(0xFFD33B34) else NewsMuted
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(news.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(
                        news.date,
                        style = MaterialTheme.typography.labelSmall,
                        color = NewsMuted
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = news.body,
                style = MaterialTheme.typography.bodyMedium,
                color = NewsMuted,
                lineHeight = 22.sp
            )
            if (news.actionText != null) {
                TextButton(
                    onClick = { onOpen(news) },
                    contentPadding = PaddingValues(0.dp),
                    modifier = Modifier.height(32.dp)
                ) {
                    Text(
                        text = news.actionText,
                        fontWeight = FontWeight.Bold,
                        color = NewsPrimary,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

data class NewsItem(
    val id: Int,
    val iconImage: ImageVector,
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
        id = 1,
        iconImage = Icons.Default.EventAvailable,
        iconBg = Color(0xFF66BB6A),
        tag = "General",
        title = "Company Wide Holiday Notice",
        date = "Dec 20, 2025",
        body = "Please be informed that December 25th will be observed as a paid corporate holiday. All offices will remain closed. Teams working on active client SLA rotations should refer to the on-call schedule posted on the intranet...",
        actionText = "Read Full Memo"
    ),
    NewsItem(
        id = 2,
        iconImage = Icons.Default.Description,
        iconBg = Color(0xFFFFA726),
        tag = "Important Policy",
        isUrgent = true,
        title = "FY26 Work From Home Policy",
        date = "Dec 15, 2025",
        body = "We are instituting a new limit on remote work starting in Jan FY26. WFH requests are now capped at 3 per month and must be logged 48 hours in advance through the portal. Over-utilization will automatically block the form options...",
        actionText = "Review Guidelines"
    ),
    NewsItem(
        id = 3,
        iconImage = Icons.Default.RocketLaunch,
        iconBg = Color(0xFF42A5F5),
        tag = "Product Update",
        title = "New Internal Toolkit Launched",
        date = "Dec 01, 2025",
        body = "The IT department has officially deployed the new developer toolkit which can be found in your standard applications folder. Ensure you restart your machines by end of week to receive the patch..."
    )
)
