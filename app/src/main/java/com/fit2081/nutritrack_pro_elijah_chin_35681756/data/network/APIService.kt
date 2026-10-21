package com.fit2081.nutritrack_pro_elijah_chin_35681756.data.network

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface APIService {

    @GET("fruit/{name}")
    suspend fun getFruitByName(@retrofit2.http.Path("name") name: String): Response<Fruit>

    companion object {

        var BASE_URL = "https://www.fruityvice.com/api/"

        fun create(): APIService {

            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(APIService::class.java)

        }
    }
}