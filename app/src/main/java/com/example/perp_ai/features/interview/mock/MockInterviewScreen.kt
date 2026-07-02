package com.example.perp_ai.features.interview.mock

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.perp_ai.presentation.navigation.Screen
import com.example.perp_ai.presentation.viewmodel.MockInterviewViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MockInterviewScreen(
    navController: NavHostController,
    type: String,
    category: String,
    viewModel: MockInterviewViewModel = hiltViewModel()
) {
    val state by viewModel.uiState.collectAsState()
    var hasPermission by remember { mutableStateOf(false) }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { hasPermission = it }
    )

    LaunchedEffect(Unit) {
        permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
        viewModel.startInterview(type, category)
    }

    LaunchedEffect(state.isInterviewFinished) {
        if (state.isInterviewFinished && state.interviewResult != null) {
            navController.navigate(Screen.InterviewResult.createRoute(state.interviewResult!!.id)) {
                popUpTo(Screen.Home.route)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("$category Interview") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            if (state.isLoading) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    CircularProgressIndicator()
                    Spacer(modifier = Modifier.height(16.dp))
                    Text("AI is analyzing your answers...", style = MaterialTheme.typography.bodyMedium)
                }
            } else if (state.error != null) {
                Column(
                    modifier = Modifier.align(Alignment.Center).padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = state.error!!,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Spacer(modifier = Modifier.height(24.dp))
                    Button(
                        onClick = { viewModel.retrySubmit() },
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Retry")
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    val progress = (state.currentQuestionIndex + 1).toFloat() / state.questions.size.coerceAtLeast(1)
                    LinearProgressIndicator(
                        progress = { progress },
                        modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(8.dp))
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))
                    
                    state.currentQuestion?.let { question ->
                        Text(
                            text = "Question ${state.currentQuestionIndex + 1}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.primary
                        )
                        
                        Spacer(modifier = Modifier.height(16.dp))
                        
                        Text(
                            text = question.text,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        )
                        
                        Spacer(modifier = Modifier.height(48.dp))
                        
                        // Answer Box
                        OutlinedTextField(
                            value = state.spokenText,
                            onValueChange = { viewModel.onAnswerChange(it) },
                            modifier = Modifier.fillMaxWidth().heightIn(min = 200.dp),
                            label = { Text("Your Answer") },
                            placeholder = { Text("Speak or type your answer here...") },
                            shape = RoundedCornerShape(16.dp)
                        )
                        
                        Spacer(modifier = Modifier.height(32.dp))
                        
                        // Mic Button
                        FloatingActionButton(
                            onClick = { 
                                if (hasPermission) viewModel.toggleListening() 
                                else permissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                            },
                            containerColor = if (state.isSpeaking) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer,
                            shape = CircleShape,
                            modifier = Modifier.size(80.dp)
                        ) {
                            Icon(
                                imageVector = if (state.isSpeaking) Icons.Default.Stop else Icons.Default.Mic,
                                contentDescription = "Mic",
                                modifier = Modifier.size(36.dp)
                            )
                        }
                        
                        if (state.isSpeaking) {
                            Text(
                                text = "Listening...",
                                color = MaterialTheme.colorScheme.error,
                                modifier = Modifier.padding(top = 8.dp)
                            )
                        }
                        
                        Spacer(modifier = Modifier.weight(1f))
                        
                        Button(
                            onClick = { viewModel.nextQuestion() },
                            modifier = Modifier.fillMaxWidth().height(56.dp),
                            shape = RoundedCornerShape(16.dp)
                        ) {
                            Text(if (state.isLastQuestion) "Finish Interview" else "Next Question")
                        }
                    }
                }
            }
        }
    }
}
