package com.example.perp_ai.presentation.navigation

sealed class Screen(val route: String) {
    // Auth
    object Splash : Screen("splash")
    object Onboarding : Screen("onboarding")
    object Login : Screen("login")
    object Register : Screen("register")
    object ForgotPassword : Screen("forgot_password")

    // Main
    object Home : Screen("home")
    object Dashboard : Screen("dashboard")
    object Notifications : Screen("notifications")
    object Profile : Screen("profile")
    object EditProfile : Screen("edit_profile")

    // Modules
    object TechnicalModule : Screen("technical_module")
    object AptitudeModule : Screen("aptitude_module")
    object MockInterview : Screen("mock_interview/{type}/{category}") {
        fun createRoute(type: String, category: String) = "mock_interview/$type/$category"
    }
    object InterviewResult : Screen("interview_result/{resultId}") {
        fun createRoute(resultId: String) = "interview_result/$resultId"
    }
    object ResumeAnalyzer : Screen("resume_analyzer")
    object CodingInterview : Screen("coding_interview")
    object GroupDiscussion : Screen("group_discussion")
    object InterviewHistory : Screen("interview_history")
}
