package com.example.myposts


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
    @GET("posts")
    fun getPosts(): Call<List<Post>>

    @GET("posts/{id}")
    fun getPostById(@Path("id") postId: Int): Call<Post>

    @GET("post/{id}/comments")
    fun getCommentById(@Path("id")PostId: Int): Call<Comments>


}
