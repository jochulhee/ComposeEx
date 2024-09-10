package com.one4u.composeex.network

import com.google.gson.annotations.SerializedName

data class APIResponse<T>(
    @SerializedName("validity")
    val validity: String,

    @SerializedName("data")
    val data: T,

    @SerializedName("error")
    val error: Error = Error()
)
data class Error(
    @SerializedName("errorcode")
    val errorcode: String = "",

    @SerializedName("errormsg")
    val errormsg: String = ""
)