package com.example.perp_ai.presentation.screens

import android.net.Uri
import android.provider.OpenableColumns
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.perp_ai.domain.models.AtsAnalysisResult
import com.example.perp_ai.features.resume.ResumeAnalyzerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumeAnalyzerScreen(
    navController: NavHostController,
    viewModel: ResumeAnalyzerViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    val filePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            val cursor = context.contentResolver.query(it, null, null, null, null)
            val nameIndex = cursor?.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            cursor?.moveToFirst()
            val fileName = cursor?.getString(nameIndex ?: -1) ?: "resume.pdf"
            cursor?.close()
            viewModel.onFileSelected(it, fileName)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("ATS Resume Analyzer", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { 
                        if (state.analysisResult != null) viewModel.resetAnalysis() 
                        else navController.popBackStack() 
                    }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            AnimatedContent(
                targetState = state.analysisResult == null,
                label = "ScreenTransition"
            ) { isInputMode ->
                if (isInputMode) {
                    ResumeInputSection(
                        fileName = state.fileName,
                        jobDescription = state.jobDescription,
                        isLoading = state.isLoading,
                        error = state.error,
                        onFilePick = { filePickerLauncher.launch("application/*") },
                        onJdChange = { viewModel.onJobDescriptionChange(it) },
                        onAnalyze = { viewModel.analyzeResume() }
                    )
                } else {
                    ResumeAnalysisResultSection(
                        result = state.analysisResult!!,
                        onReset = { viewModel.resetAnalysis() }
                    )
                }
            }
        }
    }
}

@Composable
fun ResumeInputSection(
    fileName: String,
    jobDescription: String,
    isLoading: Boolean,
    error: String?,
    onFilePick: () -> Unit,
    onJdChange: (String) -> Unit,
    onAnalyze: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // File Upload Area
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
                .clip(RoundedCornerShape(24.dp))
                .background(MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.1f))
                .border(2.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f), RoundedCornerShape(24.dp))
                .clickable(enabled = !isLoading) { onFilePick() },
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                    imageVector = if (fileName.isEmpty()) Icons.Default.CloudUpload else Icons.Default.Description,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (fileName.isEmpty()) "Upload Resume (PDF/DOCX)" else fileName,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Job Description Input
        OutlinedTextField(
            value = jobDescription,
            onValueChange = onJdChange,
            modifier = Modifier.fillMaxWidth().weight(1f),
            label = { Text("Job Description") },
            placeholder = { Text("Paste the target job description here to analyze matching score...") },
            shape = RoundedCornerShape(16.dp),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(24.dp))

        if (error != null) {
            Text(error, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(bottom = 16.dp))
        }

        Button(
            onClick = onAnalyze,
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(16.dp),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp), color = Color.White)
            } else {
                Text("Analyze ATS Score", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }
        }
    }
}

@Composable
fun ResumeAnalysisResultSection(
    result: AtsAnalysisResult,
    onReset: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(bottom = 24.dp)
    ) {
        item {
            AtsScoreHeader(score = result.score)
        }

        item {
            AnalysisSectionCard(
                title = "Matched Skills",
                items = result.matchedSkills,
                icon = Icons.Default.CheckCircle,
                iconColor = Color(0xFF4CAF50)
            )
        }

        item {
            AnalysisSectionCard(
                title = "Missing Keywords",
                items = result.missingKeywords,
                icon = Icons.Default.Warning,
                iconColor = Color(0xFFFF9800)
            )
        }

        item {
            AnalysisSectionCard(
                title = "Improvement Tips",
                items = result.suggestions,
                icon = Icons.Default.Lightbulb,
                iconColor = Color(0xFF2196F3)
            )
        }

        item {
            Button(
                onClick = onReset,
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Text("Analyze Another Resume")
            }
        }
    }
}

@Composable
fun AtsScoreHeader(score: Int) {
    val animatedScore by animateFloatAsState(targetValue = score.toFloat(), label = "ScoreAnimation")
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(contentAlignment = Alignment.Center) {
                CircularProgressIndicator(
                    progress = { animatedScore / 100f },
                    modifier = Modifier.size(120.dp),
                    color = Color.White,
                    strokeWidth = 10.dp,
                    trackColor = Color.White.copy(alpha = 0.2f)
                )
                Text(
                    text = "${animatedScore.toInt()}%",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.ExtraBold
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "ATS Compatibility Score",
                color = Color.White.copy(alpha = 0.8f),
                fontWeight = FontWeight.Medium
            )
            Text(
                text = if (score >= 80) "Highly Compatible" else if (score >= 50) "Needs Improvement" else "Low Match",
                color = Color.White,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
fun AnalysisSectionCard(
    title: String,
    items: List<String>,
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconColor: Color
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = iconColor)
                Spacer(modifier = Modifier.width(12.dp))
                Text(title, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            items.forEach { item ->
                Row(modifier = Modifier.padding(vertical = 4.dp)) {
                    Text("•", modifier = Modifier.padding(end = 8.dp), fontWeight = FontWeight.Bold)
                    Text(item, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
    }
}
