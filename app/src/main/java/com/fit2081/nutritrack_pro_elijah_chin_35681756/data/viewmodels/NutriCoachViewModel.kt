package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.viewmodels

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.network.Fruit
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.network.FruitAPIRepository
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.nutricoachtips.NutriCoachTips
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.nutricoachtips.NutriCoachTipsRepository
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.PatientRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class NutriCoachViewModel(context : Context) : ViewModel() {
    private val nutriCoachTipsRepository: NutriCoachTipsRepository = NutriCoachTipsRepository(context)

    private val fruitAPIRepository: FruitAPIRepository = FruitAPIRepository(context)

    private val patientRepository: PatientRepository = PatientRepository(context)

    private val _fruit = MutableStateFlow<Fruit?>(null)

    val fruit: StateFlow<Fruit?> = _fruit.asStateFlow()

    fun getFruitByName(name: String) {
        viewModelScope.launch {
            val result = fruitAPIRepository.getFruitByName(name)
            _fruit.value = result
        }
    }

    fun getPatientTips(userId: String): Flow<List<String>> {
       return  nutriCoachTipsRepository.getPatientTips(userId)
    }

    suspend fun insertPatientTips(nutriCoachTips: NutriCoachTips) {
        nutriCoachTipsRepository.insertPatientTipEntry(nutriCoachTips)
    }

    fun clearFruitCards() {
        _fruit.value = null
    }

    suspend fun getFruitServeSize(userId: String?): Float {
        return patientRepository.getFruitServeSize(userId)
    }

    suspend fun getFruitVariationScore(userId: String?): Float {
        return patientRepository.getFruitVariationScore(userId)
    }

    /**
     * ChatGPT was used to generate the following NutriCoachViewModelFactory class.
     * It provides a way to create an instance of NutriCoachViewModel with a Context parameter by
     * implementing a custom factory for the ViewModelProvider.
     */
    class NutriCoachViewModelFactory(appContext: Context) : ViewModelProvider.Factory {
        private val context = appContext.applicationContext

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NutriCoachViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return NutriCoachViewModel(context) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}