package com.example.perp_ai.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.perp_ai.domain.models.Notification
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor() : ViewModel() {

    private val _notifications = MutableStateFlow<List<Notification>>(emptyList())
    val notifications: StateFlow<List<Notification>> = _notifications

    init {
        loadNotifications()
    }

    private fun loadNotifications() {
        // Mocking notifications for professional UI
        _notifications.value = listOf(
            Notification("1", "Welcome to PrepWise AI!", "Start your journey to success today.", System.currentTimeMillis() - 86400000),
            Notification("2", "Interview Tip of the Day", "Confidence is key. Maintain eye contact.", System.currentTimeMillis() - 43200000),
            Notification("3", "New Mock Interview Available", "Check out the AI Mock Interview section.", System.currentTimeMillis() - 3600000)
        )
    }
}
