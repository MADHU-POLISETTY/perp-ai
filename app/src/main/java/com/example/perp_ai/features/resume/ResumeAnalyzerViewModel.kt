package com.example.perp_ai.features.resume

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.perp_ai.domain.models.AtsAnalysisResult
import com.example.perp_ai.domain.models.Resource
import com.example.perp_ai.domain.repository.AuthRepository
import com.example.perp_ai.domain.repository.ResumeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ResumeUiState(
    val selectedFileUri: Uri? = null,
    val fileName: String = "",
    val jobDescription: String = "",
    val analysisResult: AtsAnalysisResult? = null,
    val history: List<AtsAnalysisResult> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)

@HiltViewModel
class ResumeAnalyzerViewModel @Inject constructor(
    private val resumeRepository: ResumeRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(ResumeUiState())
    val uiState: StateFlow<ResumeUiState> = _uiState.asStateFlow()

    init {
        loadHistory()
    }

    private fun loadHistory() {
        val userId = authRepository.currentUser?.uid ?: return
        viewModelScope.launch {
            when (val result = resumeRepository.getAnalysisHistory(userId)) {
                is Resource.Success -> _uiState.update { it.copy(history = result.data ?: emptyList()) }
                else -> {}
            }
        }
    }

    fun onFileSelected(uri: Uri, name: String) {
        _uiState.update { it.copy(selectedFileUri = uri, fileName = name, error = null) }
    }

    fun onJobDescriptionChange(jd: String) {
        _uiState.update { it.copy(jobDescription = jd) }
    }

    fun analyzeResume() {
        val state = _uiState.value
        if (state.selectedFileUri == null) {
            _uiState.update { it.copy(error = "Please select a resume file") }
            return
        }
        if (state.jobDescription.isBlank()) {
            _uiState.update { it.copy(error = "Please enter a job description") }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            
            // Simulation of text extraction for demo purposes
            val extractedText = "Extracted text from ${state.fileName}. Experience in Kotlin, Android, Firebase."
            
            val result = resumeRepository.analyzeResume(extractedText, state.jobDescription)
            
            when (result) {
                is Resource.Success -> {
                    val analysis = result.data!!.copy(userId = authRepository.currentUser?.uid ?: "")
                    resumeRepository.saveAnalysisHistory(analysis)
                    _uiState.update { it.copy(isLoading = false, analysisResult = analysis) }
                    loadHistory()
                }
                is Resource.Error -> {
                    _uiState.update { it.copy(isLoading = false, error = result.message) }
                }
                else -> {}
            }
        }
    }

    fun resetAnalysis() {
        _uiState.update { it.copy(analysisResult = null, selectedFileUri = null, fileName = "", jobDescription = "") }
    }
}
