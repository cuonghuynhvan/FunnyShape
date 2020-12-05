package com.liv3ly.funnyshape.data.api

import com.liv3ly.funnyshape.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object APIBuilder {
    private const val BASE_URL = BuildConfig.API_URL
    private var retrofit: Retrofit? = null

    fun getInstance() : Retrofit {
        if(retrofit ==  null) {
            val clientBuilder = OkHttpClient.Builder()

            if (BuildConfig.DEBUG) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.apply {
                    loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                }
                clientBuilder.addInterceptor(loggingInterceptor)
            }

            val client = clientBuilder.build()

            retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }

        return retrofit!!
    }

    fun getBackgroundAPIService() : BackgroundAPIService = getInstance().create(BackgroundAPIService::class.java)
}
