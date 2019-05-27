package com.shobhit.everythingphotos

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.StaggeredGridLayoutManager
import com.google.gson.GsonBuilder
import com.google.gson.internal.GsonBuildConfig
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

val authKey_Pexel = "563492ad6f917000010000010b539d05730741d9909e8c7769de4389"

var pexelPageData: PexelPageData? = null

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fetchDataFromPexelServer()
    }


    fun fetchDataFromPexelServer() {

        val pexelUrl = "https://api.pexels.com/v1/curated?per_page=15&page=1"

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
//                println(response.body()?.string())

                val gsonData = GsonBuilder().create()

                val pexelData = gsonData.fromJson(responseBody, PexelPageData::class.java)
                pexelPageData = pexelData

                runOnUiThread { initView() }
            }
        })
    }


    private fun initView() {
        staggeredRecyclerView.layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        //This will for default android divider
        staggeredRecyclerView.addItemDecoration(GridItemDecoration(10, 2))

        val imageListAdapter = ImageViewAdapter()
        staggeredRecyclerView.adapter = imageListAdapter
        pexelPageData?.photos?.let { imageListAdapter.setImageList(it) }
    }
}


class PexelData(val pexelData: List<PexelPageData>)

class PexelPageData (val page: Int, val per_page: Int, val next_page: String,
                     val prev_page: String, val photos: List<PexelPhotos>)

class PexelPhotos (val id: String, val width: Float, val height: Float, val url: String,
                   val photographer: String, val photographer_url: String, val src: PhotoSource)

class PhotoSource (val original: String, val large2x: String, val large: String, val medium: String,
                   val small: String, val portrait: String, val landscape: String, val tiny: String)