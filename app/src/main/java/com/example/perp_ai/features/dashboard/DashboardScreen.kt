package com.example.perp_ai.features.dashboard

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.perp_ai.presentation.components.PerformanceItem
import com.example.perp_ai.presentation.components.StatCard
import com.example.perp_ai.presentation.components.WelcomeCard
import com.example.perp_ai.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Dashboard", fontWeight = FontWeight.Bold) }
            )
        }
    ) { padding ->
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                item {
                    WelcomeCard(userName = state.user?.name ?: "Professional")
                }

                item {
                    AnalyticsStatsRow(state.stats)
                }

                item {
                    WeeklyPerformanceCard(state.stats.recentPerformance)
                }

                item {
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        SkillAreaCard("Strong Areas", listOf("Java", "Logic"), Icons.AutoMirrored.Filled.TrendingUp, Color(0xFF4CAF50), Modifier.weight(1f))
                        SkillAreaCard("Weak Areas", listOf("AWS", "DevOps"), Icons.AutoMirrored.Filled.TrendingDown, Color(0xFFF44336), Modifier.weight(1f))
                    }
                }

                item {
                    Text("Ready for your next challenge?", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                }

                item {
                    PrimaryActions(navController)
                }

                item {
                    Text("Recent Activity", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                }

                items(state.stats.recentPerformance.reversed()) { score ->
                    PerformanceItem(score)
                }
                
                item { Spacer(modifier = Modifier.height(32.dp)) }
            }
        }
    }
}

@Composable
fun AnalyticsStatsRow(stats: com.example.perp_ai.domain.models.DashboardStats) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            title = "Avg. Score",
            value = "${stats.averageScore}%",
            icon = Icons.Default.BarChart,
            modifier = Modifier.weight(1f),
            containerColor = MaterialTheme.colorScheme.primaryContainer
        )
        StatCard(
            title = "Completed",
            value = stats.completedInterviews.toString(),
            icon = Icons.Default.DoneAll,
            modifier = Modifier.weight(1f),
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
        StatCard(
            title = "High Score",
            value = "92%",
            icon = Icons.Default.EmojiEvents,
            modifier = Modifier.weight(1f),
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    }
}

@Composable
fun WeeklyPerformanceCard(performance: List<Int>) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = "Performance Trend", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            
            val primaryColor = MaterialTheme.colorScheme.primary
            Canvas(modifier = Modifier.fillMaxWidth().height(120.dp)) {
                if (performance.size > 1) {
                    val width = size.width
                    val height = size.height
                    val spacing = width / (performance.size - 1)
                    
                    val points = performance.mapIndexed { index, score ->
                        Offset(index * spacing, height - (score / 100f * height))
                    }
                    
                    val path = Path().apply {
                        moveTo(points[0].x, points[0].y)
                        for (i in 1 until points.size) {
                            val prevPoint = points[i-1]
                            val currentPoint = points[i]
                            cubicTo(
                                prevPoint.x + (currentPoint.x - prevPoint.x) / 2, prevPoint.y,
                                prevPoint.x + (currentPoint.x - prevPoint.x) / 2, currentPoint.y,
                                currentPoint.x, currentPoint.y
                            )
                        }
                    }
                    
                    drawPath(path = path, color = primaryColor, style = Stroke(width = 4.dp.toPx()))
                    points.forEach { point ->
                        drawCircle(color = primaryColor, radius = 4.dp.toPx(), center = point)
                    }
                }
            }
        }
    }
}

@Composable
fun SkillAreaCard(title: String, skills: List<String>, icon: ImageVector, color: Color, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.05f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 13.sp, color = color)
            }
            Spacer(modifier = Modifier.height(8.dp))
            skills.forEach { skill ->
                Text(text = "• $skill", style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun PrimaryActions(navController: NavHostController) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Button(
            onClick = { navController.navigate(Screen.TechnicalModule.route) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.Psychology, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Start AI Mock Interview", fontWeight = FontWeight.Bold)
        }
    }
}
