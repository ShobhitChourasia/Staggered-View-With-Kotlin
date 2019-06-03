package com.shobhit.everythingphotos

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details_image_view.*
import kotlinx.android.synthetic.main.activity_image_grid_view_layout.view.*
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URL
import android.graphics.Bitmap
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.SharedMemory
import com.bumptech.glide.request.target.BitmapImageViewTarget
import com.bumptech.glide.request.transition.Transition
import java.util.UUID.randomUUID
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v7.widget.ActivityChooserView
import java.io.ByteArrayOutputStream
import java.util.*
import java.util.jar.Manifest


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


//        Glide.with(fullImageView.context).load(photoUrl).into(this.fullImageView)

//        val wpm = WallpaperManager.getInstance(applicationContext)
//        val ins = URL(photoUrl).openStream()//URL("absolute/path/of/image").openStream()
//        wpm.setStream(ins)
//
//
//        val result = Glide.with(this).asBitmap()//Picasso.with(context).load(imageURL).get()

        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            1
        )
//        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        Glide.with(this)
            .asBitmap()
            .load(photoUrl)
            .into(object : BitmapImageViewTarget(fullImageView){
//                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
//                    imageView.setImageBitmap(resource)
//                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    super.onResourceReady(resource, transition)
                    println(resource)

                    val wallPaperMgr = WallpaperManager.getInstance(applicationContext)
                    wallPaperMgr.setWallpaperOffsetSteps(0f, 0f)
//                    wallPaperMgr.
//                    wallPaperMgr.setBitmap(resource, fullImageView.clipBounds, false)
//                    println(Uri.parse(photoUrl))

//                    println("Image Uri ${getImageUri(applicationContext, resource)}")
//                    wallPaperMgr.getCropAndSetWallpaperIntent()

                    val intent = wallPaperMgr.getCropAndSetWallpaperIntent(getImageUri(applicationContext, resource))
//                    applicationContext.startActivity(intent)
                    this@ImageDetailsView.startActivity(intent)
//                    wallPaperMgr.setBitmap(resource)
                }

                override fun onLoadCleared(placeholder: Drawable?) {
                    super.onLoadCleared(placeholder)
                }
//                override fun onLoadCleared(placeholder: Drawable?) {
//                    // this is called when imageView is cleared on lifecycle call or for
//                    // some other reason.
//                    // if you are referencing the bitmap somewhere else too other than this imageView
//                    // clear it here as you can no longer have the bitmap
//                }
            })

//        val result = Glide.with(applicationContext).asBitmap().load(photoUrl)//Glide.with(this).asBitmap().load(photoUrl).into(fullImageView)

//        val wallpaperManager = WallpaperManager.getInstance(applicationContext).also {
            //        try {
//            it.setBitmap(result.addListener())
//        }
//        } catch (ex: IOException) {
//            ex.printStackTrace()
//        }



    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(
            this.getContentResolver(),
            inImage,
            UUID.randomUUID().toString() + ".png",
            "drawing"
        )
        return Uri.parse(path)
    }

}