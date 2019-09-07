package com.andrew.kotlinlearning

import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @POST("register.php")
    @FormUrlEncoded
    fun sendData(@Field("response") response: String, @Field("name") name: String, @Field("password") pass: String): Call<Model>

    @GET("register.php")
    fun verifyEmail(@Query("response") response: String, @Query("name") name: String, @Query("password") pass: String): Call<Model>
}