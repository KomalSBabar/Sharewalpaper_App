package com.komal.memesharingapp

import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.komal.memesharingapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    var currentimageurl:String? = null
    var imgUrl:String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadmeme()

        binding.nextButton.setOnClickListener {
            loadmeme()
        }

        binding.shareButton.setOnClickListener {
            shareMeme()
        }


    }


    private fun loadmeme(){

        binding.progressBar.visibility = View.VISIBLE

        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        currentimageurl = "https://dog.ceo/api/breeds/image/random"

        // Request a string response from the provided URL.
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, currentimageurl, null,
           Response.Listener { response ->
                              imgUrl = response.getString("message")

               Glide.with(this).load(imgUrl).listener(object:RequestListener <Drawable>
               {
                   override fun onLoadFailed(
                       e: GlideException?,
                       model: Any?,
                       target: Target<Drawable>?,
                       isFirstResource: Boolean
                   ): Boolean {
                       binding.progressBar.visibility = View.GONE
                       return false
                   }

                   override fun onResourceReady(
                       resource: Drawable?,
                       model: Any?,
                       target: Target<Drawable>?,
                       dataSource: DataSource?,
                       isFirstResource: Boolean
                   ): Boolean {
                       binding.progressBar.visibility = View.GONE
                       return false

                   }

               }).into(binding.imageView)

//            Log.d("mamta",response)
            },
            {
//
            }
        )
// Add the request to the RequestQueue.
        queue.add(JsonObjectRequest)
    }

    fun shareMeme(){
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"hey , checkout this cute dog wallpaper ! $imgUrl")
        val chooser = Intent.createChooser(intent,"share this meme using ..")
        startActivity(chooser)
    }


}