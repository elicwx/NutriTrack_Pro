package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "foodIntake",
    foreignKeys = [ForeignKey(
        entity = Patient::class,
        parentColumns = ["userId"],
        childColumns = ["patientId"],
        onDelete = ForeignKey.CASCADE
    )])
data class FoodIntake(
    @PrimaryKey val patientId: String,
    val checkBoxFruits: Boolean,
    val checkBoxVegetable: Boolean,
    val checkBoxGrains: Boolean,
    val checkBoxMeat: Boolean,
    val checkBoxSeafood: Boolean,
    val checkBoxPoultry: Boolean,
    val checkBoxFish: Boolean,
    val checkBoxEggs: Boolean,
    val checkBoxNutsSeeds: Boolean,
    val selectedPersona: String,
    val timeEat: String,
    val timeNight: String,
    val timeWake: String
)