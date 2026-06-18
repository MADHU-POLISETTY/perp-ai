package com.example.perp_ai.presentation.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.perp_ai.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterviewResultScreen(
    navController: NavHostController,
    resultId: String
) {
    // Mock result for demo
    val score = 85
    val totalQuestions = 10

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Performance Result", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.navigate(Screen.Home.route) { popUpTo(0) } }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Home")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScoreCircle(score)
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Great Job!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            
            Text(
                text = "You scored higher than 75% of candidates in this category.",
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(horizontal = 24.dp, vertical = 8.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))

            ResultMetricRow(
                metrics = listOf(
                    MetricData("Questions", "$totalQuestions", Icons.Default.HelpOutline),
                    MetricData("Correct", "8", Icons.Default.CheckCircleOutline),
                    MetricData("Time", "8:42", Icons.Default.Timer)
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            SkillAnalysisSection(
                title = "Strong Areas",
                skills = listOf("Problem Analysis", "Core Concepts", "Code Quality"),
                color = Color(0xFF4CAF50),
                icon = Icons.Default.TrendingUp
            )

            Spacer(modifier = Modifier.height(16.dp))

            SkillAnalysisSection(
                title = "Areas for Improvement",
                skills = listOf("Time Complexity", "System Constraints"),
                color = Color(0xFFF44336),
                icon = Icons.Default.TrendingDown
            )

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = { navController.navigate(Screen.Home.route) { popUpTo(0) } },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("Back to Home")
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            OutlinedButton(
                onClick = { /* TODO: Detailed Analysis */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp)
            ) {
                Text("View Detailed AI Feedback")
            }
            
            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

@Composable
fun ScoreCircle(score: Int) {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.size(200.dp)) {
        CircularProgressIndicator(
            progress = { score / 100f },
            modifier = Modifier.fillMaxSize(),
            strokeWidth = 12.dp,
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "$score%",
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.ExtraBold
            )
            Text(text = "Overall Score", style = MaterialTheme.typography.labelMedium)
        }
    }
}

@Composable
fun ResultMetricRow(metrics: List<MetricData>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        metrics.forEach { metric ->
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(metric.icon, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                Text(text = metric.value, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = metric.label, style = MaterialTheme.typography.labelSmall)
            }
        }
    }
}

@Composable
fun SkillAnalysisSection(title: String, skills: List<String>, color: Color, icon: ImageVector) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.05f))
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = color)
                Spacer(modifier = Modifier.width(12.dp))
                Text(text = title, fontWeight = FontWeight.Bold, color = color)
            }
            Spacer(modifier = Modifier.height(12.dp))
            skills.forEach { skill ->
                Row(modifier = Modifier.padding(vertical = 4.dp), verticalAlignment = Alignment.CenterVertically) {
                    Box(modifier = Modifier.size(6.dp).padding(1.dp).padding(horizontal = 4.dp)) // Bullet placeholder
                    Text(text = "• $skill", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}

data class MetricData(val label: String, val value: String, val icon: ImageVector)
