package com.example.linkupaffinity.screens.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.linkupaffinity.R
import com.example.linkupaffinity.screens.login.LoginActivity
import kotlinx.coroutines.launch

class DashboardActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val dashboardPresenter = DashboardPresenter()

        setContent {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = Color(0xFFFDFBF7)
            ) {
                DashboardView(presenter = dashboardPresenter)
            }
        }
    }

    fun performLogout() {
        val intent = Intent(this, LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        startActivity(intent)
        finish()
    }
}

@Composable
fun DashboardView(presenter: DashboardContract.Presenter) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    // UI State Management Handlers
    var isLoading by remember { mutableStateOf(true) }
    var isEmptyState by remember { mutableStateOf(false) }
    var nameOfUser by remember { mutableStateOf("") }
    var eventTitle by remember { mutableStateOf("") }
    var eventDate by remember { mutableStateOf("") }
    var currentProgress by remember { mutableStateOf(0) }

    // 🔥 New Reactive State Handlers for Notifications
    var searchNotificationCount by remember { mutableStateOf(0) }
    var matchNotificationCount by remember { mutableStateOf(0) }

    DisposableEffect(presenter) {
        val viewImpl = object : DashboardContract.View {
            override fun showLoading() {
                isLoading = true
            }

            override fun showDashboardData(
                userName: String,
                upcomingEventTitle: String,
                upcomingEventDate: String,
                dailyProgress: Int,
                searchBadges: Int, // 🔥 Synchronized with interface changes
                matchBadges: Int   // 🔥 Synchronized with interface changes
            ) {
                isLoading = false
                isEmptyState = false
                nameOfUser = userName
                eventTitle = upcomingEventTitle
                eventDate = upcomingEventDate
                currentProgress = dailyProgress
                searchNotificationCount = searchBadges
                matchNotificationCount = matchBadges
            }

            override fun showEmptyState() {
                isLoading = false
                isEmptyState = true
            }

            override fun navigateToLogin() {
                (context as? DashboardActivity)?.performLogout()
            }
        }
        presenter.attachView(viewImpl)
        onDispose { presenter.detachView() }
    }

    Scaffold(
        bottomBar = { DashboardBottomNavigationBar(onLogout = { presenter.onLogoutClicked() }) }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            if (isLoading && nameOfUser.isEmpty()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center), color = Color(0xFF8D7A5D))
            } else if (isEmptyState) {
                Column(
                    modifier = Modifier.fillMaxSize().padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("No Affinity Data Yet", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = Color.Gray)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { presenter.loadDashboardContent() }) {
                        Text("Sync Command Server")
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0xFFEFECE5))
                            .padding(horizontal = 24.dp, vertical = 28.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Text(
                                    text = "Welcome back, $nameOfUser!",
                                    fontSize = 26.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF222222)
                                )
                                Text("Pull down to sync data", fontSize = 12.sp, color = Color.Gray)
                            }

                            IconButton(onClick = {
                                scope.launch {
                                    presenter.onRefreshTriggered()
                                }
                            }) {
                                Icon(
                                    painter = painterResource(id = R.drawable.group),
                                    contentDescription = "Refresh Button",
                                    tint = Color(0xFF8D7A5D),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }

                    Column(modifier = Modifier.padding(24.dp)) {
                        Text("Available Events", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(14.dp)
                        ) {
                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1EDE6))
                            ) {
                                Column {
                                    Box(modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.LightGray))
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("Upcoming:", fontSize = 11.sp, color = Color.Gray)
                                        Text(eventTitle, fontWeight = FontWeight.Bold, fontSize = 13.sp, maxLines = 1)
                                        Text("Date: $eventDate", fontSize = 12.sp, color = Color.DarkGray, modifier = Modifier.padding(top = 4.dp))
                                    }
                                }
                            }

                            Card(
                                modifier = Modifier.weight(1f),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1EDE6))
                            ) {
                                Column {
                                    Box(modifier = Modifier.fillMaxWidth().height(100.dp).background(Color.DarkGray))
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text("Workshop:", fontSize = 11.sp, color = Color.Gray)
                                        Text("Career Paths", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                                        Text("Live in 2 days", fontSize = 12.sp, color = Color.DarkGray, modifier = Modifier.padding(top = 4.dp))
                                    }
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        // 🔥 DYNAMICALLY BOUND LINK ROWS
                        // Badges and red dots will only appear if notification count > 0
                        DashboardLinkRow(
                            title = "Search for Affinity",
                            badgeCount = if (searchNotificationCount > 0) searchNotificationCount.toString() else null,
                            isRedDot = searchNotificationCount > 0
                        )
                        DashboardLinkRow(
                            title = "Affinity Matches",
                            badgeCount = if (matchNotificationCount > 0) matchNotificationCount.toString() else null,
                            isRedDot = matchNotificationCount > 0
                        )
                        DashboardLinkRow(
                            title = "Organization",
                            badgeCount = null,
                            isRedDot = false
                        )

                        Spacer(modifier = Modifier.height(24.dp))
                        Text("Key Metrics", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = Color.Black)
                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            Box(modifier = Modifier.weight(1f).background(Color(0xFFF3ECE0), RoundedCornerShape(16.dp)).padding(14.dp)) {
                                Column {
                                    Text("Recent Activity", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text("Logged-in this week: 5 days", fontSize = 12.sp, color = Color.DarkGray)
                                }
                            }

                            Box(modifier = Modifier.weight(1f).background(Color(0xFFEAE3D7), RoundedCornerShape(16.dp)).padding(14.dp)) {
                                Column {
                                    Text("Daily Goal Progress", fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text("Tasks done: $currentProgress/10", fontSize = 12.sp, color = Color.DarkGray)
                                    LinearProgressIndicator(
                                        progress = { currentProgress / 10f },
                                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp).height(4.dp),
                                        color = Color(0xFF8D7A5D),
                                        trackColor = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }

            if (isLoading && nameOfUser.isNotEmpty()) {
                Card(
                    modifier = Modifier.align(Alignment.TopCenter).padding(top = 8.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        CircularProgressIndicator(modifier = Modifier.size(16.dp), strokeWidth = 2.dp, color = Color(0xFF8D7A5D))
                        Text("Updating...", fontSize = 12.sp, color = Color.Gray)
                    }
                }
            }
        }
    }
}

@Composable
fun DashboardLinkRow(title: String, badgeCount: String?, isRedDot: Boolean) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .background(Color.White, RoundedCornerShape(14.dp))
            .padding(horizontal = 20.dp, vertical = 18.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = title, fontSize = 18.sp, color = Color.Black, modifier = Modifier.weight(1f))

            if (badgeCount != null) {
                Box(
                    modifier = Modifier
                        .padding(end = 8.dp)
                        .background(Color(0xFFEFECE5), RoundedCornerShape(10.dp))
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                ) {
                    Text(text = badgeCount, fontSize = 12.sp, fontWeight = FontWeight.Bold, color = Color.DarkGray)
                }
            }

            if (isRedDot) {
                Box(modifier = Modifier.size(8.dp).background(Color.Red, CircleShape))
            }
        }
    }
}

@Composable
fun DashboardBottomNavigationBar(onLogout: () -> Unit) {
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier.height(70.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {}) { Icon(painter = painterResource(id = R.drawable.group), contentDescription = "Home", modifier = Modifier.size(24.dp)) }
            IconButton(onClick = {}) { Icon(painter = painterResource(id = R.drawable.email), contentDescription = "Messages", modifier = Modifier.size(24.dp)) }
            IconButton(onClick = {}) { Icon(painter = painterResource(id = R.drawable.email), contentDescription = "Calendar", modifier = Modifier.size(24.dp)) }
            IconButton(onClick = onLogout) { Icon(painter = painterResource(id = R.drawable.lock), contentDescription = "Logout", modifier = Modifier.size(24.dp), tint = Color.Red) }
        }
    }
}