package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.PatientRepository
import kotlinx.coroutines.flow.Flow


class InsightsViewModel (context : Context) : ViewModel() {

    private val patientRepository: PatientRepository = PatientRepository(context)

    fun getPatientScores(userId: String): Flow<List<Float>> {
        return patientRepository.getPatientScores(userId)
    }

    /**
     * ChatGPT was used to generate the following InsightsViewModelFactory class.
     * It provides a way to create an instance of InsightsViewModel with a Context parameter by
     * implementing a custom factory for the ViewModelProvider.
     */
    class InsightsViewModelFactory(appContext: Context) : ViewModelProvider.Factory {
        private val context = appContext.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InsightsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return InsightsViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}