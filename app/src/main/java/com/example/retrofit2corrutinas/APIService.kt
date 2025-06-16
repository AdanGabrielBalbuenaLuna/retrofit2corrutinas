package com.example.retrofit2corrutinas

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface  APIService {

    @GET
    suspend fun getDogsByBreeds(@Url url:String): Response<DogResponse>

    @GET("/api/breed/{breed}/images/random")
    suspend fun getRandomDog(@Path("breed") breed: String): Response<DogRandomResponse>


}