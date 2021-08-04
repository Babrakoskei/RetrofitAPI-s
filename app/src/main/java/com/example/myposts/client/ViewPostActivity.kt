package com.example.myposts

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewPostActivity : AppCompatActivity() {
    var postId=0
    lateinit var rvComments: RecyclerView
    lateinit var tvPostTitle: TextView
    lateinit var  tvPostBody: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_viewposts)
        postId= intent.getIntExtra("POST_ID",0)

        tvPostTitle= findViewById(R.id.tvPostTitle)
        tvPostBody= findViewById(R.id.tvPostBody)

        rvComments = findViewById(R.id.rvComments)
        rvComments.layoutManager = LinearLayoutManager(baseContext)

        fetchComments()
        fetchPostById()


    }
    fun fetchPostById(){
        val apiClient= ApiClient.buildApiClient(ApiInterface::class.java)
        val request = apiClient.getPostById(postId)
        request.enqueue(object : Callback<Post> {
            override fun onResponse(call: Call<Post>, response: Response<Post>) {
                if (response.isSuccessful){
                    val post = response.body()
                    tvPostBody.text = post?.body
                    tvPostTitle.text= post?.title
                }
            }

            override fun onFailure(call: Call<Post>, t: Throwable) {
                Toast.makeText(baseContext,t.message,Toast.LENGTH_LONG).show()            }
        })
    }
    fun fetchComments() {
        val retrofit = CommentsClient.buildApiClient(CommentsInterface::class.java)
        val request = retrofit.getComments()
        request.enqueue(object : Callback<List<Comments>> {
            override fun onResponse(call: Call<List<Comments>>, response: Response<List<Comments>>) {
                if (response.isSuccessful) {
                    val commentList = response.body()
                    if (commentList != null) {
                        val displayAdapterc = DisplayCommentsAdapter(commentList,baseContext)
                        rvComments.adapter = displayAdapterc
                    }
                }
            }
            override fun onFailure(call: Call<List<Comments>>, t: Throwable) {
                Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()
            }
        })
    }


}


