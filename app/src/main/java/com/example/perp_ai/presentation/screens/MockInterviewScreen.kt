package com.example.perp_ai.presentation.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.perp_ai.domain.models.Question
import com.example.perp_ai.presentation.navigation.Screen
import com.example.perp_ai.presentation.viewmodel.MockInterviewViewModel
import kotlinx.coroutines.delay
import java.util.Locale
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MockInterviewScreen(
    navController: NavHostController,
    type: String,
    category: String,
    viewModel: MockInterviewViewModel = hiltViewModel()
) {
    val session by viewModel.session.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val evaluationResult by viewModel.evaluationResult.collectAsState()

    var currentQuestionIndex by remember { mutableIntStateOf(0) }
    var answerText by remember { mutableStateOf("") }
    var timeLeft by remember { mutableLongStateOf(TimeUnit.MINUTES.toMillis(15)) }

    LaunchedEffect(type, category) {
        viewModel.startInterview(type, category)
    }

    LaunchedEffect(evaluationResult) {
        evaluationResult?.let {
            navController.navigate(Screen.InterviewResult.createRoute(it.id)) {
                popUpTo(Screen.Home.route)
            }
        }
    }

    LaunchedEffect(key1 = true) {
        while (timeLeft > 0) {
            delay(1000)
            timeLeft -= 1000
        }
    }

    if (isLoading || session == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val currentQuestion = session!!.questions[currentQuestionIndex]
    val progress = (currentQuestionIndex + 1).toFloat() / session!!.questions.size

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Column {
                    Text(text = "$type Assessment", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(text = category, style = MaterialTheme.typography.bodySmall)
                } },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Exit")
                    }
                },
                actions = {
                    TimerDisplay(timeLeft)
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
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp)),
            )
            
            Spacer(modifier = Modifier.height(24.dp))
            
            Text(
                text = "Question ${currentQuestionIndex + 1} of ${session!!.questions.size}",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(12.dp))

            Card(
                modifier = Modifier.fillMaxWidth().weight(1f),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(24.dp).verticalScroll(rememberScrollState())) {
                    Text(
                        text = currentQuestion.text,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Medium,
                        lineHeight = 32.sp
                    )
                    
                    Spacer(modifier = Modifier.height(32.dp))

                    if (currentQuestion.options.isNotEmpty()) {
                        currentQuestion.options.forEach { option ->
                            OptionItem(
                                text = option,
                                isSelected = answerText == option,
                                onClick = { 
                                    answerText = option
                                    viewModel.submitAnswer(currentQuestion.id, option)
                                }
                            )
                        }
                    } else {
                        OutlinedTextField(
                            value = answerText,
                            onValueChange = { 
                                answerText = it
                                viewModel.submitAnswer(currentQuestion.id, it)
                            },
                            label = { Text("Your Answer") },
                            modifier = Modifier.fillMaxWidth().heightIn(min = 150.dp),
                            placeholder = { Text("Describe your answer in detail...") }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedButton(
                    onClick = { 
                        if (currentQuestionIndex > 0) {
                            currentQuestionIndex--
                            answerText = session!!.userAnswers[session!!.questions[currentQuestionIndex].id] ?: ""
                        }
                    },
                    enabled = currentQuestionIndex > 0,
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text("Previous")
                }
                
                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = { 
                        if (currentQuestionIndex < session!!.questions.size - 1) {
                            currentQuestionIndex++
                            answerText = session!!.userAnswers[session!!.questions[currentQuestionIndex].id] ?: ""
                        } else {
                            viewModel.finishInterview()
                        }
                    },
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Text(if (currentQuestionIndex == session!!.questions.size - 1) "Submit" else "Next")
                    if (currentQuestionIndex < session!!.questions.size - 1) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.padding(start = 8.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun OptionItem(text: String, isSelected: Boolean, onClick: () -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .selectable(
                selected = isSelected,
                onClick = onClick,
                role = Role.RadioButton
            ),
        shape = RoundedCornerShape(16.dp),
        color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        tonalElevation = if (isSelected) 8.dp else 1.dp,
        border = if (isSelected) null else androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            RadioButton(selected = isSelected, onClick = null)
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text = text,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurface,
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}

@Composable
fun TimerDisplay(millis: Long) {
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis)
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
    
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Default.Timer, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds),
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
