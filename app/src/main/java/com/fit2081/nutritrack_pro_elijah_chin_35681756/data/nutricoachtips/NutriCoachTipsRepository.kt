package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.nutricoachtips

import android.content.Context
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.PatientDatabase
import kotlinx.coroutines.flow.Flow

class NutriCoachTipsRepository(context: Context) {

    private val nutriCoachTipsDao = PatientDatabase.getDatabase(context).nutriCoachTipsDAO()

    fun getPatientTips(userId: String): Flow<List<String>> = nutriCoachTipsDao.getNutriCoachTips(userId)

    suspend fun insertPatientTipEntry(nutriCoachTips: NutriCoachTips) =
        nutriCoachTipsDao.insertNutriCoachTips(nutriCoachTips)

}