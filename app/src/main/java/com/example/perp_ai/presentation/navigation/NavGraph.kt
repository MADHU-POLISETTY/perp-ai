package com.example.perp_ai.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.perp_ai.features.auth.login.LoginScreen
import com.example.perp_ai.features.auth.register.RegisterScreen
import com.example.perp_ai.features.dashboard.DashboardScreen
import com.example.perp_ai.features.interview.mock.MockInterviewScreen
import com.example.perp_ai.presentation.quiz.QuizScreen
import com.example.perp_ai.presentation.quiz.QuizViewModel
import com.example.perp_ai.presentation.quiz.ResultScreen
import com.example.perp_ai.presentation.screens.*

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(Screen.Splash.route) { SplashScreen(navController) }
        composable(Screen.Onboarding.route) { OnboardingScreen(navController) }
        composable(Screen.Login.route) { LoginScreen(navController) }
        composable(Screen.Register.route) { RegisterScreen(navController) }
        composable(Screen.ForgotPassword.route) { ForgotPasswordScreen(navController) }
        composable(Screen.Home.route) { HomeScreen(navController) }
        composable(Screen.Profile.route) { ProfileScreen(navController) }

        composable(Screen.Notifications.route) { NotificationsScreen() }
        composable(Screen.Dashboard.route) { DashboardScreen(navController) }
        composable(Screen.EditProfile.route) { EditProfileScreen(navController) }
        composable(Screen.TechnicalModule.route) {
            TechnicalModuleScreen(navController)
        }
        composable(Screen.AptitudeModule.route) {
            AptitudeModuleScreen(navController)
        }
        composable("quiz/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: "Aptitude"
            val viewModel: QuizViewModel = hiltViewModel()
            val questions = viewModel.questionsList
            val currentIndex = viewModel.currentQuestionIndex
            val isFinished = viewModel.isQuizFinished
            val score = viewModel.score

            LaunchedEffect(category) {
                viewModel.loadQuestions(category)
            }

            if (isFinished) {
                ResultScreen(
                    score = score,
                    totalQuestions = questions.size,
                    onRestartClicked = {
                        navController.navigate(Screen.Home.route) {
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    }
                )
            } else if (questions.isNotEmpty()) {
                QuizScreen(
                    question = questions[currentIndex],
                    currentIndex = currentIndex,
                    totalQuestions = questions.size,
                    onNextClicked = { selected ->
                        viewModel.checkAnswer(selected)
                    }
                )
            }
        }
        composable(Screen.ResumeAnalyzer.route) {
            ResumeAnalyzerScreen(navController)
        }
        composable(Screen.InterviewHistory.route) {
            InterviewHistoryScreen(navController)
        }
        composable(Screen.CodingInterview.route) {
            CodingInterviewScreen(navController)
        }

        composable(Screen.GroupDiscussion.route) {
            GroupDiscussionScreen(navController)
        }
        composable(
            route = Screen.MockInterview.route
        ) { backStackEntry ->

            val type =
                backStackEntry.arguments?.getString("type") ?: "TECHNICAL"

            val category =
                backStackEntry.arguments?.getString("category") ?: "GENERAL"

            MockInterviewScreen(
                navController = navController,
                type = type,
                category = category
            )
        }
        composable(
            route = Screen.InterviewResult.route
        ) { backStackEntry ->

            val resultId =
                backStackEntry.arguments?.getString("resultId") ?: ""

            InterviewResultScreen(
                navController = navController,
                resultId = resultId
            )

        }

    }
}
