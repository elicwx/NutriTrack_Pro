package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.PatientRepository
import kotlinx.coroutines.flow.Flow


class HomeViewModel (context : Context) : ViewModel() {

    private val patientRepository: PatientRepository = PatientRepository(context)

    fun getPatientName(userId: String): Flow<String?> {
        return patientRepository.getPatientName(userId)
    }

    suspend fun getPatientTotalFoodScore(userId: String) : Float {
        return patientRepository.getPatientTotalFoodScore(userId)
    }

    /**
     * ChatGPT was used to generate the following HomeViewModelFactory class.
     * It provides a way to create an instance of HomeViewModel with a Context parameter by
     * implementing a custom factory for the ViewModelProvider.
     */
    class HomeViewModelFactory(appContext: Context) : ViewModelProvider.Factory {
        private val context = appContext.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}