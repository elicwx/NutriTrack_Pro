package com.fit2081.nutritrack_pro_elijah_chin_35681756.data
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.nutricoachtips.NutriCoachTips
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.nutricoachtips.NutriCoachTipsDAO
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.FoodIntakeDAO
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.FoodIntake
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.PatientDAO
import com.fit2081.nutritrack_pro_elijah_chin_35681756.data.patient.Patient

@Database(entities = [Patient::class, FoodIntake::class, NutriCoachTips::class], version = 5, exportSchema = false)
abstract class PatientDatabase : RoomDatabase() {

    abstract fun patientDAO(): PatientDAO

    abstract fun foodIntakeDAO(): FoodIntakeDAO

    abstract fun nutriCoachTipsDAO(): NutriCoachTipsDAO

    companion object {
        @Volatile
        private var Instance: PatientDatabase? = null

        /**
         * ChatGPT was used to generate the following codes for MIGRATION_1_2, MIGRATION_2_3,
         * MIGRATION_3_4 and MIGRATION_4_5.
         * They served to handle schema changes when Room Databases were changed. They copy the data
         * from the old tables into new tables, dropping the old tables and renaming the new tables to
         * replace the old ones.
         */
        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
                    CREATE TABLE patients_new (
                        userId TEXT NOT NULL PRIMARY KEY,
                        registered INTEGER NOT NULL,
                        phoneNumber TEXT NOT NULL,
                        sex TEXT NOT NULL,
                        name TEXT NOT NULL,
                        password TEXT NOT NULL,

                        vegetableScore REAL NOT NULL,
                        fruitsScore REAL NOT NULL,
                        grainsAndCerealsScore REAL NOT NULL,
                        wholeGrainsScore REAL NOT NULL,
                        meatAndAlternativesScore REAL NOT NULL,
                        dairyScore REAL NOT NULL,
                        waterScore REAL NOT NULL,
                        saturatedFatsScore REAL NOT NULL,
                        unsaturatedFatsScore REAL NOT NULL,
                        sodiumScore REAL NOT NULL,
                        sugarScore REAL NOT NULL,
                        alcoholScore REAL NOT NULL,
                        discretionaryFoodsScore REAL NOT NULL,
                        totalFoodQualityScore REAL NOT NULL
                    )
                """)

                database.execSQL("""
                    INSERT INTO patients_new (
                        userId, registered, phoneNumber, sex, name, password,
                        vegetableScore, fruitsScore, grainsAndCerealsScore, wholeGrainsScore,
                        meatAndAlternativesScore, dairyScore, waterScore, saturatedFatsScore,
                        unsaturatedFatsScore, sodiumScore, sugarScore, alcoholScore,
                        discretionaryFoodsScore, totalFoodQualityScore
                    )
                    SELECT 
                        userId, registered, phoneNumber, sex, name, password,
                        vegetableScore, fruitsScore, grainsAndCerealsScore, wholeGrainsScore,
                        meatAndAlternativesScore, dairyScore, waterScore, saturatedFatsScore,
                        unsaturatedFatsScore, sodiumScore, sugarScore, alcoholScore,
                        discretionaryFoodsScore, totalFoodQualityScore
                    FROM patients
                """)

                database.execSQL("DROP TABLE patients")

                database.execSQL("ALTER TABLE patients_new RENAME TO patients")

                database.execSQL("""
                    CREATE TABLE foodIntake_new (
                        patientId TEXT NOT NULL PRIMARY KEY,
                        checkBoxFruits INTEGER NOT NULL,
                        checkBoxVegetable INTEGER NOT NULL,
                        checkBoxGrains INTEGER NOT NULL,
                        checkBoxMeat INTEGER NOT NULL,
                        checkBoxSeafood INTEGER NOT NULL,
                        checkBoxPoultry INTEGER NOT NULL,
                        checkBoxFish INTEGER NOT NULL,
                        checkBoxEggs INTEGER NOT NULL,
                        checkBoxNutsSeeds INTEGER NOT NULL,
                        selectedPersona TEXT NOT NULL,
                        timeEat TEXT NOT NULL,
                        timeNight TEXT NOT NULL,
                        timeWake TEXT NOT NULL,
                        FOREIGN KEY(patientId) REFERENCES patients(userId) ON DELETE CASCADE
                    )
                """)

                database.execSQL("""
                    INSERT INTO foodIntake_new (
                        patientId, checkBoxFruits, checkBoxVegetable, checkBoxGrains, checkBoxMeat,
                        checkBoxSeafood, checkBoxPoultry, checkBoxFish, checkBoxEggs, checkBoxNutsSeeds,
                        selectedPersona, timeEat, timeNight, timeWake
                    )
                    SELECT 
                        patientId, checkBoxFruits, checkBoxVegetable, checkBoxGrains, checkBoxMeat,
                        checkBoxSeafood, checkBoxPoultry, checkBoxFish, checkBoxEggs, checkBoxNutsSeeds,
                        selectedPersona, timeEat, timeNight, timeWake
                    FROM foodIntake
                """)

                database.execSQL("DROP TABLE foodIntake")
                database.execSQL("ALTER TABLE foodIntake_new RENAME TO foodIntake")
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
            CREATE TABLE nutriCoachTips_new (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                userId INTEGER NOT NULL,
                tipEntry TEXT NOT NULL
            )
        """)
                database.execSQL("""
            INSERT INTO nutriCoachTips_new (userId, tipEntry)
            SELECT userId, tipEntry FROM nutriCoachTips
        """)
                database.execSQL("DROP TABLE nutriCoachTips")

                database.execSQL("ALTER TABLE nutriCoachTips_new RENAME TO nutriCoachTips")
            }
        }

        private val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DROP TABLE IF EXISTS nutriCoachTips")

                database.execSQL("""
                CREATE TABLE nutriCoachTips (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                userId TEXT NOT NULL,
                tipEntry TEXT NOT NULL
            )
        """)
            }
        }

        private val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("""
            CREATE TABLE patients_new (
                userId TEXT NOT NULL PRIMARY KEY,
                registered INTEGER NOT NULL,
                phoneNumber TEXT NOT NULL,
                sex TEXT NOT NULL,
                name TEXT NOT NULL,
                password TEXT NOT NULL,

                vegetableScore REAL NOT NULL,
                fruitsScore REAL NOT NULL,
                grainsAndCerealsScore REAL NOT NULL,
                wholeGrainsScore REAL NOT NULL,
                meatAndAlternativesScore REAL NOT NULL,
                dairyScore REAL NOT NULL,
                waterScore REAL NOT NULL,
                saturatedFatsScore REAL NOT NULL,
                unsaturatedFatsScore REAL NOT NULL,
                sodiumScore REAL NOT NULL,
                sugarScore REAL NOT NULL,
                alcoholScore REAL NOT NULL,
                discretionaryFoodsScore REAL NOT NULL,
                totalFoodQualityScore REAL NOT NULL,
                fruitServeSizeScore REAL NOT NULL,
                fruitVariationScore REAL NOT NULL
            )
        """)

                database.execSQL("""
            INSERT INTO patients_new (
                userId, registered, phoneNumber, sex, name, password,
                vegetableScore, fruitsScore, grainsAndCerealsScore, wholeGrainsScore,
                meatAndAlternativesScore, dairyScore, waterScore, saturatedFatsScore,
                unsaturatedFatsScore, sodiumScore, sugarScore, alcoholScore,
                discretionaryFoodsScore, totalFoodQualityScore,
                fruitServeSizeScore, fruitVariationScore
            )
            SELECT 
                userId, registered, phoneNumber, sex, name, password,
                vegetableScore, fruitsScore, grainsAndCerealsScore, wholeGrainsScore,
                meatAndAlternativesScore, dairyScore, waterScore, saturatedFatsScore,
                unsaturatedFatsScore, sodiumScore, sugarScore, alcoholScore,
                discretionaryFoodsScore, totalFoodQualityScore,
                0.0 AS fruitServeSizeScore, 0.0 AS fruitVariationScore
            FROM patients
        """)

                database.execSQL("DROP TABLE patients")
                database.execSQL("ALTER TABLE patients_new RENAME TO patients")
            }
        }



        fun getDatabase(context: Context): PatientDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    PatientDatabase::class.java,
                    "item_database"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}






