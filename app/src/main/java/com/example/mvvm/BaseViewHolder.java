package com.example.mvvm;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class BaseViewHolder extends RecyclerView.ViewHolder {
    private int currentPosition;
    public BaseViewHolder(View itemView){
        super(itemView);
    }

    public abstract void clear();

    public void onBind(int position){
        currentPosition = position;
        clear();
    }

    public int getCurrentPosition(){
        return currentPosition;
    }
}
