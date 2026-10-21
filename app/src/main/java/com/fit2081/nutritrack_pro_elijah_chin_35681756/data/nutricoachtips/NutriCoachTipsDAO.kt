package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.nutricoachtips
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NutriCoachTipsDAO {
    @Insert
    suspend fun insertNutriCoachTips(nutriCoachTips: NutriCoachTips)

    @Query("SELECT tipEntry FROM nutriCoachTips WHERE userId = :userId")
    fun getNutriCoachTips(userId: String): Flow<List<String>>
}