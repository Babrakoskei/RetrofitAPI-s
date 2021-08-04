package com.example.myposts

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface CommentsInterface {
    @GET("post/1/comments")
    fun getComments(): Call<List<Comments>>
//    @GET("post/{id}/comments")
//    fun getCommentById(@Path("id") id: Int): Call<Comments>
}