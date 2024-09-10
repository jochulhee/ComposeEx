package com.one4u.composeex.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {
    companion object {
        fun getClient(domain: String): Retrofit {
            val okHttpClient: OkHttpClient = UnsafeOkHttpClient.getUnsafeOkHttpClient()
            return Retrofit.Builder()
                .baseUrl(domain)
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
        }
    }
}