package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.PatientRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow

class LogInViewModel (context: Context) : ViewModel() {

    private val patientRepository: PatientRepository =
        PatientRepository(context)

    fun getRegisteredUserIds() {
        viewModelScope.launch { patientRepository.getRegisteredPatientUserIds() }
    }

    fun getPassword(userId: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val password = patientRepository.getPassword(userId)
            onResult(password)
        }
    }

    private val _registeredUserIds = MutableStateFlow<List<String>>(emptyList())
    val registeredUserIds: StateFlow<List<String>> = _registeredUserIds

    init {

        viewModelScope.launch {
            patientRepository.getRegisteredPatientUserIds().collect {
                _registeredUserIds.value = it
            }
        }
    }


    /**
     * ChatGPT was used to generate the following LogInViewModelFactory class.
     * It provides a way to create an instance of LogInViewModel with a Context parameter by
     * implementing a custom factory for the ViewModelProvider.
     */
    class LogInViewModelFactory(appContext: Context) : ViewModelProvider.Factory {
        private val context = appContext.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LogInViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LogInViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}