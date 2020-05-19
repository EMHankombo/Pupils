package com.bridge.androidtechnicaltest.db


import com.google.gson.annotations.SerializedName

data class PupilPostBody(
    @SerializedName("country")
    val country: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("latitude")
    val latitude: Double,
    @SerializedName("longitude")
    val longitude: Double
)