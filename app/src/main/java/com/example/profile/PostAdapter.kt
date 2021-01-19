package com.example.profile

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class PostAdapter(private val postList : ArrayList<Post>, private val context : Context, val onPlayVideo: OnPlayVideo) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.post_adapter_layout, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        Glide.with(context).load(postList[position].thumbnail).centerCrop().into(holder.thumbnail)

        holder.viewCount.text = postList[position].viewCount.toString()

        holder.itemView.setOnClickListener {
            playVideo(postList[position])
        }

        holder.playBtn.setOnClickListener {
            playVideo(postList[position])
        }

    }

    override fun getItemCount(): Int = postList.size

    private fun playVideo(post: Post) {
        onPlayVideo.onPlay(post)
    }
}


class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    var playBtn : ImageButton = itemView.findViewById(R.id.exo_play)
    var viewCount : TextView = itemView.findViewById(R.id.view_count)
    var thumbnail : AppCompatImageView = itemView.findViewById(R.id.thumbnail_image_view)

}
