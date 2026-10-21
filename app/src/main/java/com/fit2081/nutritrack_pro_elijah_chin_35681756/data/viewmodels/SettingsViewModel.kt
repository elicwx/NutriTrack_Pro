package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels

import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.PatientRepository
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SettingsViewModel (context : Context) : ViewModel(){

    private val patientRepository: PatientRepository = PatientRepository(context)

    fun getPatientName(userId: String): Flow<String?> {
        return patientRepository.getPatientName(userId)
    }

    fun getSpecificPhoneNumber(userId: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val phoneNumber = patientRepository.getSpecificPhoneNumber(userId)
            onResult(phoneNumber)
        }
    }

    /**
     * ChatGPT was used to generate the following SettingsViewModelFactory class.
     * It provides a way to create an instance of SettingsViewModel with a Context parameter by
     * implementing a custom factory for the ViewModelProvider.
     */
    class SettingsViewModelFactory(appContext: Context) : ViewModelProvider.Factory {
        private val context = appContext.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SettingsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SettingsViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}