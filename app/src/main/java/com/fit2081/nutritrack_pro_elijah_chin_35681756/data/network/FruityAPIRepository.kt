package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.network

import android.content.Context

class FruitAPIRepository(context : Context) {
    private val apiService = APIService.create()

    suspend fun getFruitByName(name: String): Fruit? {
        val response = apiService.getFruitByName(name)
        return if (response.isSuccessful) {
            response.body()
        } else {
            null
        }
    }
}
