package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FoodIntakeDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodIntake(foodIntake: FoodIntake)

    @Query("SELECT * FROM foodIntake WHERE patientId = :patientId")
    suspend fun getFoodIntake(patientId: String): FoodIntake?
}