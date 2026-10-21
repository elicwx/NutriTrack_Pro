package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient

import android.content.Context
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.PatientDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class PatientRepository(context: Context) {

    private val patientDao = PatientDatabase.getDatabase(context).patientDAO()

    fun getPatientIds() = patientDao.getPatientIds()

    suspend fun updatePatientNameAndPassword(userId: String, registered: Boolean, name: String, password: String) =
        patientDao.updateNameAndPassword(userId, registered, name, password)

    fun getRegisteredPatientUserIds() = patientDao.getRegisteredUserIds()

    fun getPhoneNumbers() = patientDao.getPhoneNumbers()

    suspend fun getPassword(userId: String) = patientDao.getPassword(userId)

    suspend fun getSpecificPhoneNumber(userId: String) = patientDao.getSpecificPhoneNumber(userId)

    fun getPatientName(userId: String) = patientDao.getPatientName(userId)

    suspend fun getPatientTotalFoodScore(userId: String) = patientDao.getPatientTotalFoodScore(userId)

    suspend fun getTotalPatientScores(sex: String) = patientDao.getTotalPatientScores(sex)

    suspend fun getAllPatients(): List<Patient> {
        return patientDao.getAllPatients()
    }

    suspend fun getFruitServeSize(userId: String?) = patientDao.getFruitServeSize(userId)

    suspend fun getFruitVariationScore(userId: String?) = patientDao.getFruitVariationScore(userId)

    fun getPatientScores(userId: String): Flow<List<Float>> {
        return patientDao.getPatient(userId).map { patient ->
            listOf(
                patient.vegetableScore,
                patient.fruitsScore,
                patient.grainsAndCerealsScore,
                patient.wholeGrainsScore,
                patient.meatAndAlternativesScore,
                patient.dairyScore,
                patient.waterScore,
                patient.saturatedFatsScore,
                patient.unsaturatedFatsScore,
                patient.sodiumScore,
                patient.sugarScore,
                patient.alcoholScore,
                patient.discretionaryFoodsScore,
                patient.totalFoodQualityScore
            )
        }
            .filter { scores -> scores.any { it != 0.0f } }
    }

    suspend fun importPatients(context: Context, filename: String) {
        val patients = withContext(Dispatchers.IO) {
            val patientsList = mutableListOf<Patient>()
            val inputStream = context.assets.open(filename)
            inputStream.bufferedReader().useLines { lines ->
                lines.drop(1).forEach { line ->
                    val tokens = line.split(",")
                    if (tokens[2] == "Male"){
                        val patient = Patient(
                            userId = tokens[1],
                            registered = false,
                            phoneNumber = tokens[0],
                            sex = tokens[2],
                            name = "",
                            password = "",
                            vegetableScore = tokens[8].toFloat(),
                            fruitsScore = tokens[19].toFloat(),
                            grainsAndCerealsScore = tokens[29].toFloat(),
                            wholeGrainsScore = tokens[33].toFloat(),
                            meatAndAlternativesScore = tokens[36].toFloat(),
                            dairyScore = tokens[40].toFloat(),
                            waterScore = tokens[49].toFloat(),
                            saturatedFatsScore = tokens[57].toFloat(),
                            unsaturatedFatsScore = tokens[60].toFloat(),
                            sodiumScore = tokens[43].toFloat(),
                            sugarScore = tokens[54].toFloat(),
                            alcoholScore = tokens[46].toFloat(),
                            discretionaryFoodsScore = tokens[5].toFloat(),
                            totalFoodQualityScore = tokens[3].toFloat(),
                            fruitServeSizeScore = tokens[21].toFloat(),
                            fruitVariationScore = tokens[22].toFloat()
                        )
                        patientsList.add(patient)
                    } else{
                        val patient = Patient(
                            userId = tokens[1],
                            registered = false,
                            phoneNumber = tokens[0],
                            sex = tokens[2],
                            name = "",
                            password = "",
                            vegetableScore = tokens[9].toFloat(),
                            fruitsScore = tokens[20].toFloat(),
                            grainsAndCerealsScore = tokens[30].toFloat(),
                            wholeGrainsScore = tokens[34].toFloat(),
                            meatAndAlternativesScore = tokens[37].toFloat(),
                            dairyScore = tokens[41].toFloat(),
                            waterScore = tokens[50].toFloat(),
                            saturatedFatsScore = tokens[58].toFloat(),
                            unsaturatedFatsScore = tokens[61].toFloat(),
                            sodiumScore = tokens[44].toFloat(),
                            sugarScore = tokens[55].toFloat(),
                            alcoholScore = tokens[47].toFloat(),
                            discretionaryFoodsScore = tokens[6].toFloat(),
                            totalFoodQualityScore = tokens[4].toFloat(),
                            fruitServeSizeScore = tokens[21].toFloat(),
                            fruitVariationScore = tokens[22].toFloat()
                        )
                        patientsList.add(patient)
                    }
                }
            }
            patientsList
        }

        patients.forEach { patientDao.insertPatient(it) }
    }

    suspend fun isDatabaseEmpty(): Boolean = patientDao.getPatientCount() == 0

}