package com.example.myposts

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CommentsClient {
    var retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    fun <T> buildApiClient(CommentsInterface:Class<T>):T{
        return retrofit.create(CommentsInterface)
    }
}