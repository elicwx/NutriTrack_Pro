package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.genai.UiState
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.Dispatchers
import com.fit2081.nutritrack_pro_elijah_chin_35681756.BuildConfig
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.Patient
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.PatientRepository


class GenAiViewModel(context : Context) : ViewModel() {

    private val patientRepository: PatientRepository = PatientRepository(context)

    private val _uiState: MutableStateFlow<UiState> =
        MutableStateFlow(UiState.Initial)

    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val generativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = BuildConfig.apiKey
    )

    suspend fun getAllPatientData(): List<Patient> {
        return patientRepository.getAllPatients()
    }

    fun sendPrompt(
        prompt: String
    ) {
        _uiState.value = UiState.Loading

        viewModelScope.launch(Dispatchers.IO){
            try {

                val response = generativeModel.generateContent(
                    content {
                        text(prompt)
                    }
                )
                response.text?.let { outputContent ->
                    val outputs: List<String> = if (outputContent.contains("\n")) {
                        outputContent
                            .split(Regex("""\n+"""))
                            .map { it.trim() }
                            .filter { it.isNotBlank() }
                    } else {
                        listOf(outputContent.trim())
                    }
                    _uiState.value = UiState.Success(outputs)
                }
            } catch (e: Exception) {
                _uiState.value = UiState.Error(e.localizedMessage ?: "")
            }
        }
    }


    /**
     * ChatGPT was used to generate the following GenAiViewModelFactory class.
     * It provides a way to create an instance of GenAiViewModel with a Context parameter by
     * implementing a custom factory for the ViewModelProvider.
     */
    class GenAiViewModelFactory(appContext: Context) : ViewModelProvider.Factory {
        private val context = appContext.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(GenAiViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return GenAiViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

}