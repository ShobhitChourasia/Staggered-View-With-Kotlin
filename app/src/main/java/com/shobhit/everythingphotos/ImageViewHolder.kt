package com.shobhit.everythingphotos

import android.support.v7.widget.RecyclerView
import android.util.DisplayMetrics
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_image_grid_view_layout.view.*

class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    fun bindView(pageData: PexelPhotos){
        itemView.imageName.text = pageData.photographer
        itemView.photoImageView.layoutParams.height = getHeightOfImageView(pageData.height, pageData.width).toInt()

        Glide.with(itemView.context).load(pageData.src.large!!).into(itemView.photoImageView)
    }

    fun getHeightOfImageView(origImageHeight: Float, origImageWidth: Float): Float {
        val displayMetrics = DisplayMetrics()
//        windowManager.defaultDisplay.getMetrics(displayMetrics)

        var width = 1080//displayMetrics.widthPixels
//        var height = displayMetrics.heightPixels

        return (origImageHeight * (width/2 - 10))/origImageWidth
    }
}