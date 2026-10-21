package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "patients")
data class Patient (
    @PrimaryKey val userId: String,
    val registered: Boolean,
    val phoneNumber: String,
    val sex: String,
    val name: String,
    val password: String,

    val vegetableScore: Float,
    val fruitsScore: Float,
    val grainsAndCerealsScore: Float,
    val wholeGrainsScore: Float,
    val meatAndAlternativesScore: Float,
    val dairyScore: Float,
    val waterScore: Float,
    val saturatedFatsScore: Float,
    val unsaturatedFatsScore: Float,
    val sodiumScore: Float,
    val sugarScore: Float,
    val alcoholScore: Float,
    val discretionaryFoodsScore: Float,
    val totalFoodQualityScore: Float,
    val fruitServeSizeScore: Float,
    val fruitVariationScore: Float,
)