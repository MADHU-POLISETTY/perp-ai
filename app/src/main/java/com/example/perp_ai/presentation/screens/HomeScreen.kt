package com.example.perp_ai.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.perp_ai.domain.models.InterviewType
import com.example.perp_ai.domain.models.TechnicalCategory
import com.example.perp_ai.presentation.components.BottomNavigationBar
import com.example.perp_ai.presentation.navigation.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    var searchQuery by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("PrepWise AI", fontWeight = FontWeight.ExtraBold) },
                actions = {
                    IconButton(onClick = { navController.navigate(Screen.Profile.route) }) {
                        Icon(Icons.Default.AccountCircle, contentDescription = "Profile")
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigationBar(navController = navController)
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(8.dp))
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { searchQuery = it },
                    onSearch = {},
                    active = false,
                    onActiveChange = {},
                    placeholder = { Text("Search interview topics...") },
                    leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(16.dp)
                ) {}
            }

            item {
                SectionTitle("Interview Categories")
                Spacer(modifier = Modifier.height(12.dp))
                CategoryGrid(navController)
            }

            item {
                SectionTitle("Key Features")
                Spacer(modifier = Modifier.height(12.dp))
                LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    item { 
                        FeatureCard(
                            "Practice Mode", 
                            "Interactive sessions", 
                            Icons.AutoMirrored.Filled.Chat, 
                            Color(0xFF673AB7)
                        ) {
                            navController.navigate(Screen.TechnicalModule.route)
                        } 
                    }
                    item { 
                        FeatureCard(
                            "Analyze Results", 
                            "Get AI Feedback", 
                            Icons.Default.Analytics, 
                            Color(0xFF2196F3)
                        ) {
                            navController.navigate(Screen.Dashboard.route)
                        } 
                    }
                    item { 
                        FeatureCard(
                            "Resume Analyzer", 
                            "AI-powered feedback", 
                            Icons.Default.Description, 
                            Color(0xFFE91E63)
                        ) {
                            navController.navigate(Screen.ResumeAnalyzer.route)
                        } 
                    }
                    item { 
                        FeatureCard(
                            "History", 
                            "Previous Attempts", 
                            Icons.Default.History, 
                            Color(0xFF4CAF50)
                        ) {
                            navController.navigate(Screen.InterviewHistory.route)
                        } 
                    }
                }
            }

            item {
                SectionTitle("Recommended for You")
                Spacer(modifier = Modifier.height(12.dp))
            }

            items(recommendedTopics) { topic ->
                RecommendedItem(topic) {
                    navController.navigate("quiz/${topic.title}")
                }
            }
            
            item { Spacer(modifier = Modifier.height(24.dp)) }
        }
    }
}

@Composable
fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleLarge,
        fontWeight = FontWeight.Bold
    )
}

@Composable
fun CategoryGrid(navController: NavHostController) {
    val categories = listOf(
        CategoryItemData("HR Interview", Icons.Default.People, Color(0xFF4CAF50), "quiz/General Interview"),
        CategoryItemData("Technical", Icons.Default.Code, Color(0xFF2196F3), Screen.TechnicalModule.route),
        CategoryItemData("Aptitude", Icons.Default.Calculate, Color(0xFFFF9800), Screen.AptitudeModule.route),
        CategoryItemData("AI Mock", Icons.Default.SmartToy, Color(0xFFF44336), Screen.MockInterview.createRoute(InterviewType.AI_MOCK.name, "GENERAL")),
        CategoryItemData("Coding", Icons.Default.Terminal, Color(0xFF673AB7), "quiz/Coding"),
        CategoryItemData("GD Prep", Icons.Default.Groups, Color(0xFF009688), Screen.GroupDiscussion.route)
    )

    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        for (i in categories.indices step 2) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                CategoryCard(categories[i], Modifier.weight(1f)) {
                    navController.navigate(categories[i].route)
                }
                if (i + 1 < categories.size) {
                    CategoryCard(categories[i + 1], Modifier.weight(1f)) {
                        navController.navigate(categories[i + 1].route)
                    }
                }
            }
        }
    }
}

@Composable
fun CategoryCard(data: CategoryItemData, modifier: Modifier = Modifier, onClick: () -> Unit) {
    Card(
        modifier = modifier
            .height(100.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = data.color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(data.icon, contentDescription = null, tint = data.color)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = data.title, fontWeight = FontWeight.Bold, fontSize = 14.sp)
        }
    }
}

@Composable
fun FeatureCard(title: String, subtitle: String, icon: ImageVector, bgColor: Color, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .width(200.dp)
            .height(130.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(24.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.linearGradient(listOf(bgColor, bgColor.copy(alpha = 0.7f))))
                .padding(16.dp)
        ) {
            Column {
                Icon(icon, contentDescription = null, tint = Color.White)
                Spacer(modifier = Modifier.height(12.dp))
                Text(text = title, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(text = subtitle, color = Color.White.copy(alpha = 0.8f), fontSize = 11.sp)
            }
        }
    }
}

@Composable
fun RecommendedItem(topic: RecommendedTopic, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(topic.color.copy(alpha = 0.1f)),
                contentAlignment = Alignment.Center
            ) {
                Icon(topic.icon, contentDescription = null, tint = topic.color)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = topic.title, fontWeight = FontWeight.Bold)
                Text(text = "${topic.level} • ${topic.duration}", style = MaterialTheme.typography.bodySmall)
            }
            Spacer(modifier = Modifier.weight(1f))
            Icon(Icons.Default.ChevronRight, contentDescription = null)
        }
    }
}

data class CategoryItemData(val title: String, val icon: ImageVector, val color: Color, val route: String)
data class RecommendedTopic(val title: String, val level: String, val duration: String, val icon: ImageVector, val color: Color)

val recommendedTopics = listOf(
    RecommendedTopic("System Design", "Advanced", "45 min", Icons.Default.AccountTree, Color(0xFF673AB7)),
    RecommendedTopic("Java Interview", "Intermediate", "30 min", Icons.Default.Code, Color(0xFFE91E63)),
    RecommendedTopic("AWS & DevOps", "Expert", "60 min", Icons.Default.Cloud, Color(0xFFFF9800)),
    RecommendedTopic("AI/ML Interview", "Intermediate", "40 min", Icons.Default.Psychology, Color(0xFF2196F3)),
    RecommendedTopic("Cloud Computing", "Beginner", "25 min", Icons.Default.CloudQueue, Color(0xFF009688))
)
