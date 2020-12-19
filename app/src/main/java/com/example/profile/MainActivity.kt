package com.example.profile


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.*
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    var isVideoCliked : Boolean = true
    lateinit var video: ImageView
    lateinit var like: ImageView
    lateinit var videoContent: TextView
    lateinit var likeContent: TextView

    lateinit var profilePostRecyclerview : RecyclerView
    var postAdapter: PostAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        registerForContextMenu(more_info_button)

        more_info_button.setOnClickListener {
            val popup = PopupMenu(this@MainActivity, more_info_button)
            //Inflating the Popup using xml file
            popup.menuInflater.inflate(R.menu.menu, popup.menu)

            popup.setOnMenuItemClickListener {
                if (it.itemId == R.id.share) {
                    Toast.makeText(applicationContext, "Share Clicked", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext, "Reposrt Clicked", Toast.LENGTH_SHORT).show()
                }
                true
            }

            popup.show()//showin
        }

        init()

    }

    private fun init() {

        Glide.with(this).load(R.drawable.profile_pic).circleCrop().into(findViewById(R.id.profile_picture))

        video = findViewById(R.id.ic_video)
        like = findViewById(R.id.ic_like)

        likeContent = findViewById(R.id.like_Content)
        videoContent = findViewById(R.id.video_content)

        profilePostRecyclerview = findViewById(R.id.profile_post_recyclerview)

        profilePostRecyclerview.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
        }

        when(isVideoCliked) {
            true -> onVideoClick(findViewById(R.id.video_container))
            false -> onLikeClick(findViewById(R.id.like_container))
        }

    }

    fun onVideoClick(view: View) {

        isVideoCliked = true

        video.setImageResource(R.drawable.ic_videocam_green)
        videoContent.setTextColor(resources.getColor(R.color.icon_green))

        like.setImageResource(R.drawable.ic_love_grayshade)
        likeContent.setTextColor(resources.getColor(R.color.icon_green_shade))

        postAdapter = PostAdapter(Repository.imageList, this)
        profilePostRecyclerview.adapter = postAdapter
    }

    fun onLikeClick(view: View) {

        isVideoCliked = false

        video.setImageResource(R.drawable.ic_videocam_grayshade)
        videoContent.setTextColor(resources.getColor(R.color.icon_green_shade))

        like.setImageResource(R.drawable.ic_love_green)
        likeContent.setTextColor(resources.getColor(R.color.icon_green))

        postAdapter = PostAdapter(Repository.videoList, this)
        profilePostRecyclerview.adapter = postAdapter
    }
}