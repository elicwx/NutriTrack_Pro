package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.nutricoachtips

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "nutriCoachTips")
data class NutriCoachTips (
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val userId: String,
    val tipEntry: String
)