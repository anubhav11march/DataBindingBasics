package com.example.mvvm;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public abstract class recyclerViewOnClickListener extends RecyclerView.OnScrollListener {
    LinearLayoutManager linearLayoutManager;

    public recyclerViewOnClickListener(LinearLayoutManager linearLayoutManager){
        this.linearLayoutManager = linearLayoutManager;
    }

    @Override
    public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        int visibleItemCount = linearLayoutManager.getChildCount();
        int totalItemCount = linearLayoutManager.getItemCount();
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        if(!isloading() && !isLastPage()){
            if((visibleItemCount + firstVisibleItemPosition) > totalItemCount
                && firstVisibleItemPosition >= 0
                    && totalItemCount >= getTotalPageCount()){
                loadMoreItems();
            }
        }
    }

    public abstract int getTotalPageCount();

    public abstract void loadMoreItems();

    public abstract boolean isloading();

    public abstract boolean isLastPage();
}
