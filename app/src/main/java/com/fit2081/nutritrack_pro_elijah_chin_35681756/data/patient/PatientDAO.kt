package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PatientDAO{
    @Insert
    suspend fun insertPatient(patient: Patient)

    @Query("UPDATE patients SET registered = :registered, name = :name, password = :password WHERE userId = :userId")
    suspend fun updateNameAndPassword(userId: String, registered: Boolean, name: String, password: String)

    @Delete
    suspend fun deletePatient(patient: Patient)

    @Query("SELECT * FROM patients WHERE userId = :userId LIMIT 1")
    fun getPatient(userId: String): Flow<Patient>

    @Query("SELECT name FROM patients WHERE userId = :userId LIMIT 1")
    fun getPatientName(userId: String): Flow<String?>

    @Query("SELECT totalFoodQualityScore FROM patients WHERE userId = :userId LIMIT 1")
    suspend fun getPatientTotalFoodScore(userId: String): Float

    @Query("SELECT userId FROM patients WHERE registered = 0")
    fun getPatientIds(): Flow<List<String>>

    @Query("SELECT userId FROM patients WHERE registered = 1")
    fun getRegisteredUserIds(): Flow<List<String>>

    @Query("SELECT phoneNumber FROM patients WHERE registered = 0")
    fun getPhoneNumbers(): Flow<List<String>>

    @Query("SELECT phoneNumber FROM patients WHERE userId = :userId")
    suspend fun getSpecificPhoneNumber(userId: String): String?

    @Query("SELECT password FROM patients WHERE userId = :userId")
    suspend fun getPassword(userId: String): String?

    @Query("SELECT COUNT(*) FROM patients")
    suspend fun getPatientCount(): Int

    @Query("SELECT totalFoodQualityScore FROM patients WHERE sex == :sex")
    suspend fun getTotalPatientScores(sex: String): List<Float>

    @Query("SELECT * FROM patients")
    suspend fun getAllPatients(): List<Patient>

    @Query("SELECT fruitVariationScore FROM patients WHERE userId = :userId")
    suspend fun getFruitVariationScore(userId: String?): Float

    @Query("SELECT fruitServeSizeScore FROM patients WHERE userId = :userId")
    suspend fun getFruitServeSize(userId: String?): Float
}
