package com.example.perp_ai.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.perp_ai.domain.models.InterviewType
import com.example.perp_ai.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AptitudeModuleScreen(navController: NavHostController) {
    val sections = listOf(
        AptitudeSection("Quantitative", "Math & Logic", Icons.Default.Calculate, Color(0xFF673AB7)),
        AptitudeSection("Logical", "Reasoning & Puzzles", Icons.Default.Lightbulb, Color(0xFF2196F3)),
        AptitudeSection("Verbal", "English & Grammar", Icons.Default.Translate, Color(0xFFE91E63))
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Aptitude Test", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
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
        ) {
            Text(
                text = "Test Your Skills",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                items(sections) { section ->
                    AptitudeSectionCard(section) {
                        navController.navigate("quiz/${section.title}")
                    }
                }
            }
        }
    }
}

@Composable
fun AptitudeSectionCard(section: AptitudeSection, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(110.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = section.color.copy(alpha = 0.05f))
    ) {
        Row(
            modifier = Modifier.padding(20.dp).fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(56.dp)
                    .background(section.color.copy(alpha = 0.1f), RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(section.icon, contentDescription = null, tint = section.color, modifier = Modifier.size(32.dp))
            }
            Spacer(modifier = Modifier.width(20.dp))
            Column {
                Text(text = section.title, fontWeight = FontWeight.Bold, fontSize = 18.sp)
                Text(text = section.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, contentDescription = null, tint = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

data class AptitudeSection(val title: String, val subtitle: String, val icon: ImageVector, val color: Color)
