package com.example.myposts

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RecyclerAdapter(var postList : List<Post>, var context: Context): RecyclerView.Adapter<PostViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val itemView= LayoutInflater.from(parent.context)
                .inflate(R.layout.item_display_file,parent,false)
        return PostViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        val currentPost = postList.get(position)
        holder.tvName.text=currentPost.title
        holder.tvBody.text=currentPost.body
        holder.cvPosts.setOnClickListener {
            var intent = Intent(context, ViewPostActivity::class.java)
            intent.putExtra("POST_ID", currentPost.id)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }


    }

    override fun getItemCount(): Int {
        return postList.size
    }
}
class PostViewHolder(itemView:View): RecyclerView.ViewHolder(itemView){
    var tvName=itemView.findViewById<TextView>(R.id.tvName)
    var tvBody=itemView.findViewById<TextView>(R.id.tvBody)
    var cvPosts=itemView.findViewById<CardView>(R.id.cvclient)
}

class ViewPostAcivity:AppCompatActivity() {
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


        //how to use a progress bar
        //Registration that is the project we are going to use in the next class we will get and log on a user fix errors in that project remove Id and put password
        //fetch comments and display them on the recycler view display the list of comments we fetched the post ad another end point for fetching comments
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
                Toast.makeText(baseContext, t.message, Toast.LENGTH_LONG).show()            }
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