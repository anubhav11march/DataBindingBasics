package com.example.mvvm;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchHelperListener listener;
    private Paint paint;
    private ColorDrawable bg;
    private int bgcolor;

    public RecyclerItemTouchHelper(int dragDir, int swipeDir, RecyclerItemTouchHelperListener listener){
        super(dragDir, swipeDir);
        this.listener = listener;
        paint = new Paint();
        bgcolor = Color.parseColor("#FF0040");
        bg = new ColorDrawable();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
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
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int ht = itemView.getHeight();
        boolean isCancelled = dX == 0 && !isCurrentlyActive;

        if(isCancelled){
            c.drawRect((float)itemView.getRight(), (float) itemView.getTop(), (float) itemView.getRight(),
                    (float) itemView.getBottom(), paint);
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            return;
        }
        bg.setColor(bgcolor);
        bg.setBounds(itemView.getRight()+ (int)dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        bg.draw(c);

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
