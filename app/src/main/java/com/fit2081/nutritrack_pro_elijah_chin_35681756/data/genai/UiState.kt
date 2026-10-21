package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.genai

sealed interface UiState {

    object Initial : UiState

    object Loading : UiState


    data class Success(val outputText: List<String>) : UiState

    data class Error(val errorMessage: String) : UiState
}