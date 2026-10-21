package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels

import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.PatientRepository
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider


class ClinicianDashViewModel (context : Context) : ViewModel() {

    private val patientRepository: PatientRepository = PatientRepository(context)

    suspend fun getAverageMaleScore(sex: String): String {
        val scores = patientRepository.getTotalPatientScores(sex)
        val average = scores.sum() / scores.size
        return String.format("%.1f", average)
    }

    suspend fun getAverageFemaleScore(sex: String): String {
        val scores = patientRepository.getTotalPatientScores(sex)
        val average = scores.sum() / scores.size
        return String.format("%.1f", average)
    }

    /**
     * ChatGPT was used to generate the following ClinicianDashViewModelFactory class.
     * It provides a way to create an instance of ClinicianDashViewModel with a Context parameter by
     * implementing a custom factory for the ViewModelProvider.
     */
    class ClinicianDashViewModelFactory(appContext: Context) : ViewModelProvider.Factory {
        private val context = appContext.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ClinicianDashViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ClinicianDashViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}