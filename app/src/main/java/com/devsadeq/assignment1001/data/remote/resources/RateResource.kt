package com.devsadeq.assignment1001.data.remote.resources


import com.google.gson.annotations.SerializedName

data class RateResource(
    @SerializedName("count")
    val count: Int?,
    @SerializedName("rate")
    val rate: Double?
)