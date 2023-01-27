package com.argumelar.aplikasigithubusernavigationdanapi.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val moduleApiConfig = module {
    single { okhttpClient() }
    single { retrofit( get() ) }
    single { serviceApi( get() ) }
}

fun okhttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        ).build()
}

fun retrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun serviceApi(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)