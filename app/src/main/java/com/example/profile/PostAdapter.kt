package com.example.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter(val postList : ArrayList<Post>, val context : Context) : RecyclerView.Adapter<ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_adapter_layout, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context).load(postList[position].postImage).centerCrop().into(holder.postImage)
        holder.viewCount.text = postList[position].viewCount.toString()

    }

    override fun getItemCount(): Int = postList.size

}

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var viewCount : TextView = itemView.findViewById(R.id.view_count)
    var postImage : AppCompatImageView = itemView.findViewById(R.id.post_image)

}
