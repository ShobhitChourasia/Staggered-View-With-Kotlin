package com.shobhit.everythingphotos

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.StaggeredGridLayoutManager

abstract class GridViewPaginationScrollListner(layoutManager: StaggeredGridLayoutManager) : RecyclerView.OnScrollListener() {

    var TAG = GridViewPaginationScrollListner::class.java!!.getSimpleName()

    private var previousTotal = 0 // The total number of items in the dataset after the last load
    private var loading = true // True if we are still waiting for the last set of data to load.
    private var visibleThreshold = 5 // The minimum amount of items to have below your current scroll position before loading more.
    var pastVisibleItem: Int = 0


    var visibleItemCount:Int = 0
    var totalItemCount:Int = 0

    private var current_page = 1

    private var mLinearLayoutManager: StaggeredGridLayoutManager? = null


    public fun setupLayoutManager(linearLayoutManager: StaggeredGridLayoutManager) {
        this.mLinearLayoutManager = linearLayoutManager
    }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        var firstVisibleItems: IntArray? = null

        visibleItemCount = recyclerView.childCount
        totalItemCount = mLinearLayoutManager?.itemCount!!
        firstVisibleItems = mLinearLayoutManager?.findFirstVisibleItemPositions(firstVisibleItems)//findFirstVisibleItemPosition()


        if(firstVisibleItems != null && firstVisibleItems.size > 0) {
            pastVisibleItem = firstVisibleItems[0];
        }

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false
                previousTotal = totalItemCount
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (pastVisibleItem + visibleThreshold)) {
            // End has been reached

            // Do something
            current_page++

            onLoadMore(current_page)

            loading = true
        }
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        super.onScrollStateChanged(recyclerView, newState)
    }

    abstract fun onLoadMore(current_page: Int)

}