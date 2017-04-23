package br.com.pirlamps.yousetest.foundation.custom;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

/**
 * Created by root-matheus on 21/04/17.
 */

public abstract class EndlessRecyclerViewScrollListener extends RecyclerView.OnScrollListener {

    private static final String TAG = "EndlessScroll";
    private int visibleThreshold = 10;

    private int currentPage = 2;

    private int previousTotalItemCount = 0;

    private boolean loading = true;

    private int startingPageIndex = 2;

    RecyclerView.LayoutManager mLayoutManager;

    public EndlessRecyclerViewScrollListener(LinearLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        Log.i(TAG, "initializing EndlessScroll with LinearLayoutManager ");
    }

    public EndlessRecyclerViewScrollListener(GridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
        Log.i(TAG, "initializing EndlessScroll with GridLayouManager ");
    }

    public EndlessRecyclerViewScrollListener(StaggeredGridLayoutManager layoutManager) {
        this.mLayoutManager = layoutManager;
        visibleThreshold = visibleThreshold * layoutManager.getSpanCount();
        Log.i(TAG, "initializing EndlessScroll with StaggeredGridLayoutManager ");
    }

    public int getLastVisibleItem(int[] lastVisibleItemPositions) {
        int maxSize = 0;
        Log.i(TAG, "getLastVisibleItem: preparing...");
        for (int i = 0; i < lastVisibleItemPositions.length; i++) {
            if (i == 0) {
                maxSize = lastVisibleItemPositions[i];
            }
            else if (lastVisibleItemPositions[i] > maxSize) {
                maxSize = lastVisibleItemPositions[i];
            }
        }
        Log.i(TAG, "getLastVisibleItem: return lastVisibleItem, position:"+maxSize);
        return maxSize;
    }


    @Override
    public void onScrolled(RecyclerView view, int dx, int dy) {
        Log.i(TAG, "onScrolled: started...");
        int lastVisibleItemPosition = 0;
        int totalItemCount = mLayoutManager.getItemCount();
        Log.i(TAG, "onScrolled: totalItemCount: "+totalItemCount);
        Log.i(TAG, "onScrolled: preparing to set lastVisibleItem position");
        if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] lastVisibleItemPositions = ((StaggeredGridLayoutManager) mLayoutManager).findLastVisibleItemPositions(null);
             lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions);
        } else if (mLayoutManager instanceof GridLayoutManager) {
            lastVisibleItemPosition = ((GridLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            lastVisibleItemPosition = ((LinearLayoutManager) mLayoutManager).findLastVisibleItemPosition();
        }
        Log.i(TAG, "onScrolled: lastVisibleItem position: "+lastVisibleItemPosition);

        if (totalItemCount < previousTotalItemCount) {
            Log.i(TAG, "onScrolled: previousItemCount gt 0, reseting back to initial state");
            this.currentPage = this.startingPageIndex;
            this.previousTotalItemCount = totalItemCount;
            if (totalItemCount == 0) {
                this.loading = true;
            }
        }

        Log.i(TAG, "onScrolled: checking if loading finished");
        if (loading && (totalItemCount > previousTotalItemCount)) {
            Log.i(TAG, "onScrolled: loading completed, updating values");
            loading = false;
            previousTotalItemCount = totalItemCount;
        }

        Log.i(TAG, "onScrolled: checking if needs to load more");
        if (!loading && (lastVisibleItemPosition + visibleThreshold) > totalItemCount) {
            Log.i(TAG, "onScrolled: calling load more");
            currentPage++;
            onLoadMore(currentPage, totalItemCount, view);
            loading = true;
        }
    }


    public void resetState() {
        Log.i(TAG, "resetState: Reseting state of scroll");
        this.currentPage = this.startingPageIndex;
        this.previousTotalItemCount = 0;
        this.loading = true;
    }


    public int getCurrentPage() {
        return currentPage;
    }

    public abstract void onLoadMore(int page, int totalItemsCount, RecyclerView view);

}