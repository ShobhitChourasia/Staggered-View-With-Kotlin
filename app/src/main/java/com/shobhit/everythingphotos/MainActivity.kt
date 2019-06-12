package com.shobhit.everythingphotos

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

val authKey_Pexel = "563492ad6f917000010000010b539d05730741d9909e8c7769de4389"

//var pexelPageData: PexelPageData? = null
var scrollListner : GridViewPaginationScrollListner? = null
var currentPage = 1
var photoList: List<PexelPhotos>? = null
val imageListAdapter = ImageViewAdapter()

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchDataFromPexelServer()
    }


    fun fetchDataFromPexelServer() {

        val pexelUrl = "https://api.pexels.com/v1/curated?per_page=15&page=$currentPage"

        var request = Request.Builder().url(pexelUrl).build()

        val client = OkHttpClient().newBuilder().addInterceptor { chain ->
            val originalRequest = chain.request()
            val builder = originalRequest.newBuilder()
                    .header("Authorization", authKey_Pexel)
            val newRequest = builder.build()
            chain.proceed(newRequest)
        }.build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                println(call)
            }

            override fun onResponse(call: Call, response: Response) {

                val responseBody = response.body()?.string()

                val gsonData = GsonBuilder().create()

                val pexelData = gsonData.fromJson(responseBody, PexelPageData::class.java)
                if (currentPage == 1) {
                    photoList = pexelData.photos
                    runOnUiThread(this@MainActivity::initView)
                }
                else {
                    runOnUiThread { updatePhotoList(pexelData.photos) }
                }
            }
        })
    }


    private fun initView() {
        var staggeredLayoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        staggeredRecyclerView.layoutManager = staggeredLayoutManager
        //This will for default android divider
        staggeredRecyclerView.addItemDecoration(GridItemDecoration(20, 2))
        photoList?.let(imageListAdapter::setImageList)
        staggeredRecyclerView.adapter = imageListAdapter
//        staggeredRecyclerView.itemAnimator?.animateChange()
//        imageListAdapter.setImageList(pexelPageData?.photos)
        staggeredRecyclerView.getItemAnimator()?.endAnimations()

        scrollListner = object : GridViewPaginationScrollListner(staggeredLayoutManager) {


//            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
//                super.onScrollStateChanged(recyclerView, newState)
//                print("Scrolling new")
//            }

            override fun onLoadMore(current_page: Int) {
                print("Scrolling$current_page")
                currentPage = current_page
                fetchDataFromPexelServer()
            }

//            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
//                super.onScrolled(recyclerView, dx, dy)
//                print("onScrolled new")
//            }

        }
        scrollListner?.setupLayoutManager(staggeredLayoutManager)

        staggeredRecyclerView.addOnScrollListener(scrollListner as GridViewPaginationScrollListner)

    }


    private fun updatePhotoList(latestPhotoList: List<PexelPhotos>) {
        var updatePhotoList: List<PexelPhotos>? = photoList?.plus(latestPhotoList)!!
        photoList = updatePhotoList
        if (updatePhotoList != null) {
            photoList?.let { imageListAdapter.setImageList(it) }
            imageListAdapter.notifyDataSetChanged()
        }
    }
}


class PexelData(val pexelData: List<PexelPageData>)

class PexelPageData(val page: Int, val per_page: Int, val next_page: String,
                    val prev_page: String, val photos: List<PexelPhotos>)

class PexelPhotos(val id: String, val width: Float, val height: Float, val url: String,
                  val photographer: String, val photographer_url: String, val src: PhotoSource)

class PhotoSource(val original: String, val large2x: String, val large: String, val medium: String,
                  val small: String, val portrait: String, val landscape: String, val tiny: String)