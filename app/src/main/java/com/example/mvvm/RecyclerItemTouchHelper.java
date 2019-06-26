package com.example.mvvm;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import static android.support.v7.widget.helper.ItemTouchHelper.ACTION_STATE_SWIPE;
enum ButtonsState {
    GONE,
    LEFT_VISIBLE,
    RIGHT_VISIBLE
}


public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {
    private RecyclerItemTouchHelperListener listener;
    private Paint paint;
    private ColorDrawable bg, bg1;
    private RecyclerView.ViewHolder currentItemVIewHodler = null;
    private int bgcolor, bgcolor1;
    private boolean swipeBack = false;
    private ButtonsState buttonsState = ButtonsState.GONE;
    private actiosn buttonsActions = null;
    private static final float buttonWidth = 300;
    private RectF buttonsInstance = null;



    public RecyclerItemTouchHelper(int dragDir, int swipeDir, RecyclerItemTouchHelperListener listener, actiosn buttonsActions){
        super(dragDir, swipeDir);
        this.buttonsActions = buttonsActions;
        this.listener = listener;
        paint = new Paint();
        bgcolor = Color.parseColor("#FF0040");
        bgcolor1 = Color.parseColor("#3104B4");
        bg = new ColorDrawable();
        bg1 = new ColorDrawable();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        return makeMovementFlags(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
    }



    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
//        listener.onSwiped(viewHolder, i, viewHolder.getAdapterPosition());
    }

//    @Override
//    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
////        if(viewHolder !=null){
////            final View foreground = ((RecyclerViewAdapter.viewHolder) viewHolder).cv;
////            getDefaultUIUtil().onSelected(foreground);
////        }
////        super.onSelectedChanged(viewHolder, actionState);
//    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(actionState == ACTION_STATE_SWIPE){
            if(buttonsState != ButtonsState.GONE){
                if(buttonsState == ButtonsState.LEFT_VISIBLE) dX = Math.max(dX, buttonWidth);
                if(buttonsState == ButtonsState.RIGHT_VISIBLE) dX = Math.min(dX, -buttonWidth);
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
            else {
                setTouchListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        }
        if(buttonsState == ButtonsState.GONE){
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
        currentItemVIewHodler =viewHolder;





//        View itemView = viewHolder.itemView;
//        int ht = itemView.getHeight();
//        boolean isCancelled = dX == 0 && !isCurrentlyActive;
//
//        if(isCancelled){
//            c.drawRect((float)itemView.getRight(), (float) itemView.getTop(), (float) itemView.getRight(),
//                    (float) itemView.getBottom(), paint);
//            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
//            return;
//        }
//
//        bg.setColor(bgcolor);
//        bg.setBounds(itemView.getRight() + (int)dX, itemView.getTop(), itemView.getRight(), itemView.getBottom());
//        bg.draw(c);
//        bg1.setColor(bgcolor1);
//        bg1.setBounds(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + (int)dX, itemView.getBottom());
//        bg1.draw(c);



//        final  View foregroundVIew = ((RecyclerViewAdapter.viewHolder) viewHolder).cv;
//        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundVIew, dX, dY, actionState, isCurrentlyActive);
    }

    private void setTouchListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                swipeBack = event.getAction() == MotionEvent.ACTION_CANCEL || event.getAction() == MotionEvent.ACTION_UP;
                if(swipeBack){
                    if(dX < -buttonWidth) buttonsState = ButtonsState.RIGHT_VISIBLE;
                    else if(dX > buttonWidth) buttonsState = ButtonsState.LEFT_VISIBLE;
                    if(buttonsState!=ButtonsState.GONE){
                        setTouchDownListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                        setItemsClickable(recyclerView, false);
                    }
                }
                return false;
            }
        });
    }

    private void setTouchDownListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN)
                    setTouchUpListener(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
                return false;
            }
        });
    }

    private void setItemsClickable(RecyclerView recyclerView, boolean isClickable){
        for(int i=0; i<recyclerView.getChildCount(); ++i){
            recyclerView.getChildAt(i).setClickable(isClickable);
        }
    }

    private void setTouchUpListener(final Canvas c, final RecyclerView recyclerView, final RecyclerView.ViewHolder viewHolder, final float dX, final float dY, final int actionState, final boolean isCurrentlyActive) {
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_UP){
                    RecyclerItemTouchHelper.super.onChildDraw(c, recyclerView, viewHolder, 0F, dY, actionState, isCurrentlyActive);
                    recyclerView.setOnTouchListener(new View.OnTouchListener() {
                        @Override
                        public boolean onTouch(View v, MotionEvent event) {
                            return false;
                        }
                    });
                    setItemsClickable(recyclerView, true);
                    swipeBack = false;
                    if(buttonsActions!=null && buttonsInstance != null && buttonsInstance.contains(event.getX(), event.getY())){
                        if (buttonsState == ButtonsState.LEFT_VISIBLE) {
                            buttonsActions.onLeftClicked(viewHolder.getAdapterPosition());
                        }
                        else if (buttonsState == ButtonsState.RIGHT_VISIBLE) {
                            buttonsActions.onRightClicked(viewHolder.getAdapterPosition());
                        }
                    }
                    buttonsState = ButtonsState.GONE;
                    currentItemVIewHodler = null;
                }
                return false;
            }
        });
    }

    private void drawButtons(Canvas c, RecyclerView.ViewHolder viewHolder){
        float buttonWidthh = buttonWidth -20;
        float corners = 10;
        View itemView =viewHolder.itemView;
        Paint p = new Paint();
        RectF leftButton = new RectF(itemView.getLeft(), itemView.getTop(), itemView.getLeft() + buttonWidthh, itemView.getBottom());
        p.setColor(Color.BLUE);
        c.drawRoundRect(leftButton, corners, corners, p);
        drawText("EDIT", c, leftButton, p);

        RectF rightButton = new RectF(itemView.getRight() - buttonWidthh, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        p.setColor(Color.RED);
        c.drawRoundRect(rightButton, corners, corners, p);
        drawText("DELETE", c, rightButton, p);

        buttonsInstance = null;
        if(buttonsState == ButtonsState.LEFT_VISIBLE)
            buttonsInstance = leftButton;
        else if(buttonsState == ButtonsState.RIGHT_VISIBLE)
            buttonsInstance = rightButton;
    }

    public void onDraw(Canvas c){
        if(currentItemVIewHodler != null){
            drawButtons(c, currentItemVIewHodler);
        }
    }

    private void drawText(String text, Canvas c, RectF button,Paint p){
        float textSize = 50;
        p.setColor(Color.WHITE);
        p.setAntiAlias(true);
        p.setTextSize(textSize);
        c.drawText(text, button.centerX() - (p.measureText(text)/2), button.centerY() + (textSize/2), p);
    }

        @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        final  View foregroundVIew = ((RecyclerViewAdapter.viewHolder) viewHolder).cv;
        getDefaultUIUtil().clearView(foregroundVIew);
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        if (swipeBack) {
            swipeBack = buttonsState != ButtonsState.GONE;
            return 0;
        }
        return super.convertToAbsoluteDirection(flags, layoutDirection);

    }

    public interface RecyclerItemTouchHelperListener{
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }


}
