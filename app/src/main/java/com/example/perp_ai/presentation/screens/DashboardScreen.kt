package com.example.perp_ai.presentation.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import com.example.perp_ai.presentation.viewmodel.DashboardViewModel
import com.example.perp_ai.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashboardScreen(
    navController: NavHostController,
    viewModel: DashboardViewModel = hiltViewModel()
) {
    val user by viewModel.currentUser.collectAsState()
    val stats by viewModel.stats.collectAsState()

    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text("Analytics Dashboard", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                WelcomeCard(userName = user?.name ?: "Professional")
            }

            item {
                AnalyticsStatsRow(stats)
            }

            item {
                WeeklyPerformanceCard(stats.recentPerformance)
            }

            item {
                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    SkillAreaCard("Strong Areas", listOf("Java", "Problem Solving"), Icons.AutoMirrored.Filled.TrendingUp, Color(0xFF4CAF50), Modifier.weight(1f))
                    SkillAreaCard("Weak Areas", listOf("AWS", "System Design"), Icons.AutoMirrored.Filled.TrendingDown, Color(0xFFF44336), Modifier.weight(1f))
                }
            }

            item {
                Text(
                    text = "Recent Assessments",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            items(stats.recentPerformance.reversed()) { score ->
                PerformanceItem(score)
            }

            item {
                Spacer(modifier = Modifier.height(8.dp))
                PrimaryActions(navController)
                Spacer(modifier = Modifier.height(32.dp))
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
            value = "94%",
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
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(text = "Weekly Progress", fontWeight = FontWeight.Bold, fontSize = 18.sp)
            Spacer(modifier = Modifier.height(16.dp))
            
            val primaryColor = MaterialTheme.colorScheme.primary
            Canvas(modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)) {
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
                    
                    drawPath(
                        path = path,
                        color = primaryColor,
                        style = Stroke(width = 4.dp.toPx())
                    )
                    
                    points.forEach { point ->
                        drawCircle(color = primaryColor, radius = 6.dp.toPx(), center = point)
                        drawCircle(color = Color.White, radius = 3.dp.toPx(), center = point)
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
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = title, fontWeight = FontWeight.Bold, fontSize = 14.sp, color = color)
            }
            Spacer(modifier = Modifier.height(12.dp))
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
            Icon(Icons.Default.PlayArrow, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Start AI Mock Interview", fontSize = 16.sp, fontWeight = FontWeight.Bold)
        }
        
        OutlinedButton(
            onClick = { navController.navigate(Screen.AptitudeModule.route) },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Icon(Icons.Default.Timer, contentDescription = null)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Practice Aptitude", fontSize = 16.sp)
        }
    }
}
