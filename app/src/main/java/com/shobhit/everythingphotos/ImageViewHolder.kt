package com.shobhit.everythingphotos

import android.content.Intent
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image_grid_view_layout.view.*

class ImageViewHolder(itemView: View, var pexelPhotos: PexelPhotos? = null) : RecyclerView.ViewHolder(itemView) {

    companion object {
        val Image_Title = "Image Title"
        val Image_Url = "Image_Url"
        val Pexel_Photo_Obj = "Pexel_Obj"
    }

    init {
        itemView.setOnClickListener {
            val intent = Intent(it.context, ImageDetailsView::class.java)

            intent.putExtra(Image_Title, pexelPhotos?.photographer)
            intent.putExtra(Image_Url, pexelPhotos?.src?.large2x)
            it.context.startActivity(intent)

        }
    }

    fun bindView(pageData: PexelPhotos) {
        itemView.imageName.text = pageData.photographer
        itemView.photoImageView.layoutParams.height = getHeightOfImageView(pageData.height, pageData.width).toInt()

        Glide.with(itemView.context).load(pageData.src.large2x!!).into(itemView.photoImageView)
    }

    fun getHeightOfImageView(origImageHeight: Float, origImageWidth: Float): Float {
        val displayMetrics = DisplayMetrics()
//        windowManager.defaultDisplay.getMetrics(displayMetrics)

        var width = 1080//displayMetrics.widthPixels
//        var height = displayMetrics.heightPixels

        return (origImageHeight * ((width / 2) - 10)) / origImageWidth
    }
}