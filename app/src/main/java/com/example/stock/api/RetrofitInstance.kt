package com.example.stock.api

import com.example.stock.util.Constants.Companion.API_KEY
import com.example.stock.util.Constants.Companion.BASE_URL
import com.example.stock.util.Constants.Companion.NEW_URL
import com.example.stock.util.Constants.Companion.TICKER_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.internal.connection.ConnectInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitInstance{

    private val requesIterceptor = Interceptor{ chain ->
        val url = chain.request()
            .url
            .newBuilder()
            .addQueryParameter("token", API_KEY)
            .build()
        val request = chain.request()
            .newBuilder()
            .url(url)
            .build()

        return@Interceptor chain.proceed(request)
    }
    private var gson:Gson? = GsonBuilder()
            .setLenient()
            .create()

    val okHttpClient = OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(120, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(requesIterceptor)
            .build()
    val okHttpClient1 = OkHttpClient.Builder()

            .build()

    private val retrofit by lazy{
        Retrofit.Builder().client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val retrofit1 by lazy{
        Retrofit.Builder().client(okHttpClient1)
                .baseUrl(NEW_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }
    private val tickersRetrofit by lazy {
        Retrofit.Builder().client(okHttpClient1)
                .baseUrl(TICKER_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
    }
    val api: FinhubbApi by lazy {
        retrofit.create(FinhubbApi::class.java)
    }
    val myApi: StockApi by lazy {
        retrofit1.create(StockApi::class.java)
    }
    val tickersApi: TickersApi by lazy {
        tickersRetrofit.create(TickersApi::class.java)
    }
}