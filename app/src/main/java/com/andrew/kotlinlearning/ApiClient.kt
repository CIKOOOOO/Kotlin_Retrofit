package com.andrew.kotlinlearning

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val URL: String = "http://192.168.43.34:80/kotlin_learning/"
    var retrofit: Retrofit? = null

    fun getApiClient(): ApiInterface {
        if (retrofit == null) {
            retrofit = Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
        return retrofit!!.create(ApiInterface::class.java)
    }
}