package com.shobhit.everythingphotos

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

class ImageViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var imageList = listOf<PexelPhotos>()

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {
        val imageViewHolder: ImageViewHolder = viewHolder as ImageViewHolder
        imageViewHolder.bindView(imageList[position])
        imageViewHolder.pexelPhotos = imageList[position]
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.activity_image_grid_view_layout, parent, false)

        return ImageViewHolder(view)
    }

    override fun getItemCount(): Int = imageList.size

    fun setImageList(imageList: List<PexelPhotos>) {
        this.imageList = imageList
        notifyDataSetChanged()
    }
}