package com.one4u.composeex.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface APIInterface {
    @Headers(
        "Content-Type:application/json",
        "Authorization:vAPP:12596d6bcea4d37fcff23de95402daa7.c912116c4882be3c68e2b899d2e840ea.11d80743868057248bc800eedd380262298555387d495012ff576a7e407f45e1",
        "Code:SMS",
        "MbId:SYSTEMADM"
    )
    @POST("sms/SendContents")
    fun sendMsgData(
        @Body obj: MsgData?
    ): Call<APIResponse<String>>

    @GET("sms/GetTargetList")
    fun getSenders(): Call<APIResponse<Array<String>>>
}