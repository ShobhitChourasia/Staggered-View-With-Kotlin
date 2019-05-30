package com.shobhit.everythingphotos

import android.app.WallpaperManager
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details_image_view.*
import kotlinx.android.synthetic.main.activity_image_grid_view_layout.view.*
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL

class ImageDetailsView:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_image_view)

        //Nav Bar Title
        val navBarTitle = intent.getStringExtra(ImageViewHolder.Image_Title)
        supportActionBar?.title = navBarTitle

        //Set Image to Image View
        val photoUrl = intent.getStringExtra(ImageViewHolder.Image_Url)
//        val pexelPhotos = intent.getSerializableExtra(ImageViewHolder.Pexel_Photo_Obj) as? PexelPhotos


        Glide.with(fullImageView.context).load(photoUrl).into(this.fullImageView)

//        val wpm = WallpaperManager.getInstance(this)
//        val ins = URL(photoUrl).openStream()//URL("absolute/path/of/image").openStream()
//        wpm.setStream(ins)
    }

}