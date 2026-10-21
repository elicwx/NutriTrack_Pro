package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.PatientRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow


class RegistrationViewModel (context: Context) : ViewModel() {

    private val patientRepository: PatientRepository =
        PatientRepository(context)

    private val _phoneNumbers = MutableStateFlow<List<String>>(emptyList())
    val phoneNumbers: StateFlow<List<String>> = _phoneNumbers

    private val _userIds = MutableStateFlow<List<String>>(emptyList())
    val userIds: StateFlow<List<String>> = _userIds

    init {
        viewModelScope.launch {
            if (patientRepository.isDatabaseEmpty()) {
                patientRepository.importPatients(context, "userData.csv")
            }
        }

        viewModelScope.launch {
            patientRepository.getPhoneNumbers().collect {
                _phoneNumbers.value = it
            }
        }

        viewModelScope.launch {
            patientRepository.getPatientIds().collect {
                _userIds.value = it
            }
        }
    }

    fun getSpecificPhoneNumber(userId: String, onResult: (String?) -> Unit) {
        viewModelScope.launch {
            val phoneNumber = patientRepository.getSpecificPhoneNumber(userId)
            onResult(phoneNumber)
        }
    }

    fun registerUser(userId: String, registered: Boolean, name: String, password: String) {
        viewModelScope.launch { patientRepository.updatePatientNameAndPassword(userId, registered, name, password)}
    }

    /**
     * ChatGPT was used to generate the following RegistrationViewModelFactory class.
     * It provides a way to create an instance of RegistrationViewModel with a Context parameter by
     * implementing a custom factory for the ViewModelProvider.
     */
    class RegistrationViewModelFactory(appContext: Context) : ViewModelProvider.Factory {
        private val context = appContext.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegistrationViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RegistrationViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}

