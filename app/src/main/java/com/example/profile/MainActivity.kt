package com.example.profile


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.StyledPlayerView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnPlayVideo {

    var isVideoCliked: Boolean = true
    lateinit var video: ImageView
    lateinit var like: ImageView
    lateinit var videoContent: TextView
    lateinit var likeContent: TextView

    lateinit var profilePostRecyclerview: RecyclerView
    var postAdapter: PostAdapter? = null

    private val TAG = "MainActivity"

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

        Glide.with(this).load(R.drawable.profile_pic).circleCrop()
            .into(findViewById(R.id.profile_picture))

        video = findViewById(R.id.ic_video)
        like = findViewById(R.id.ic_like)

        likeContent = findViewById(R.id.like_Content)
        videoContent = findViewById(R.id.video_content)

        profilePostRecyclerview = findViewById(R.id.profile_post_recyclerview)

        profilePostRecyclerview.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 3)
        }

        when (isVideoCliked) {
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

        postAdapter = PostAdapter(Repository.videoList, this, this)
        profilePostRecyclerview.adapter = postAdapter
    }

    fun onLikeClick(view: View) {

        isVideoCliked = false

        video.setImageResource(R.drawable.ic_videocam_grayshade)
        videoContent.setTextColor(resources.getColor(R.color.icon_green_shade))

        like.setImageResource(R.drawable.ic_love_green)
        likeContent.setTextColor(resources.getColor(R.color.icon_green))

        postAdapter = PostAdapter(Repository.imageList, this, this)
        profilePostRecyclerview.adapter = postAdapter
    }

    override fun onPlay(post: Post) {

        val alertDialog = AlertDialog.Builder(this)
        val view = LayoutInflater.from(this).inflate(R.layout.dialog_player, null)
        alertDialog.setView(view)
        val dialog = alertDialog.create()

        val closeBtn: Button = view.findViewById(R.id.close_btn)
        val playerView: StyledPlayerView = view.findViewById(R.id.player_View)

        val player = SimpleExoPlayer.Builder(this).build()
        playerView.player = player
        playerView.hideController()
        playerView.useController = true

        player.addMediaItem(MediaItem.fromUri(post.postUrl))
        player.prepare()
        player.playWhenReady = true

        closeBtn.setOnClickListener {
            if (player.isPlaying) {
                player.stop()
            }
            player.release()
            dialog.dismiss()
        }

        dialog.setOnDismissListener {
            if (player.isPlaying) {
                player.stop()
                player.playWhenReady = false
            }
            player.release()
        }

        dialog.show()
    }

}