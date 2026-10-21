package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels

import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.FoodIntakeRepository
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.FoodIntake
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class QuestionnaireViewModel (context : Context) : ViewModel(){

    private val _foodIntake = MutableStateFlow<FoodIntake?>(null)
    val foodIntake: StateFlow<FoodIntake?> = _foodIntake

    private val foodIntakeRepository: FoodIntakeRepository =
        FoodIntakeRepository(context)

    fun setFoodIntake(foodIntake: FoodIntake) {
        viewModelScope.launch { foodIntakeRepository.setFoodIntake(foodIntake) }
    }

    fun getFoodIntake(patientId: String) {
        viewModelScope.launch {
            _foodIntake.value = foodIntakeRepository.getFoodIntake(patientId)
        }
    }


    /**
     * ChatGPT was used to generate the following QuestionnaireViewModelFactory class.
     * It provides a way to create an instance of QuestionnaireViewModel with a Context parameter by
     * implementing a custom factory for the ViewModelProvider.
     */
    class QuestionnaireViewModelFactory(appContext: Context) : ViewModelProvider.Factory {
        private val context = appContext.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(QuestionnaireViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return QuestionnaireViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}