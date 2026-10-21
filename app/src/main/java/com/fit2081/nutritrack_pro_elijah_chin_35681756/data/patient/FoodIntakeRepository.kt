package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient

import android.content.Context
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.PatientDatabase

class FoodIntakeRepository (context: Context) {

    private val foodIntakeDao = PatientDatabase.getDatabase(context).foodIntakeDAO()

    suspend fun getFoodIntake(patientId: String): FoodIntake? {
        return foodIntakeDao.getFoodIntake(patientId)
    }

    suspend fun setFoodIntake(foodIntake: FoodIntake) = foodIntakeDao.insertFoodIntake(foodIntake)
}