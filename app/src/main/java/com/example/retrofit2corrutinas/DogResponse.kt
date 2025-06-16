package com.example.retrofit2corrutinas

import com.google.gson.annotations.SerializedName

data class DogResponse (
    @SerializedName ("status") var status:String,
    @SerializedName ("message")var images:List<String>
    )

data class DogRandomResponse(
    @SerializedName("message") var randomImage: String,
    @SerializedName("status") var randomStatus: String
)