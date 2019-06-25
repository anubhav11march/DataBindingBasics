package com.example.mvvm;

import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchHelperListener listener;

    public RecyclerItemTouchHelper(int dragDir, int swipeDir, RecyclerItemTouchHelperListener listener){
        super(dragDir, swipeDir);
        this.listener = listener;

    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        listener.onSwiped(viewHolder, i, viewHolder.getAdapterPosition());
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        if(viewHolder !=null){
            final View foreground = ((RecyclerViewAdapter.viewHolder) viewHolder).cv;
            getDefaultUIUtil().onSelected(foreground);
        }
//        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void onChildDrawOver(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final  View foregroundVIew = ((RecyclerViewAdapter.viewHolder) viewHolder).cv;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundVIew, dX, dY, actionState, isCurrentlyActive);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        final  View foregroundVIew = ((RecyclerViewAdapter.viewHolder) viewHolder).cv;
        getDefaultUIUtil().clearView(foregroundVIew);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface RecyclerItemTouchHelperListener{
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
