package com.frand.crud.networking

import com.frand.crud_dasar.model.ResponseMock
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface ApiEndpoint {

    @GET("todo")
    fun getMock(): Call<ResponseMock>

    @POST("todo")
    fun postMock(
        @Body jobPost: JsonObject
    ): Call<ResponseMock>

    @PUT("todo/{id}")
    fun putMock(
        @Path("id") id: String,
        @Body jobUpdate: JsonObject
    ):Call<ResponseMock>

    @DELETE("todo/{id}")
    fun delMock(
        @Path("id") id: String
    ):Call<ResponseMock>


    @POST("todo")
    fun postMock(
        @Field("title") title: String,
        @Field("complete") complete: Boolean,
        @Field("content") content: String,
    ): Call<ResponseMock>



}