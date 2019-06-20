package com.example.mvvm;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Transition;
import android.transition.TransitionManager;
import android.transition.TransitionSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.mvvm.R;

import com.transitionseverywhere.extra.Scale;

import java.util.List;

class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder> {
    List<MainActivity.Order> orders;
    static Context ctx;

    RecyclerViewAdapter(List<MainActivity.Order> orders, Context ctx){
        this.orders = orders;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rvlayout, viewGroup, false);
        viewHolder vh = new viewHolder(view);
        final ViewGroup transitionsContainer = (ViewGroup) view.findViewById(R.id.cv);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        viewHolder.name.setText(orders.get(i).name);
        viewHolder.price.setText(orders.get(i).price);
        viewHolder.pic.setImageResource(orders.get(i).picId);
//        viewHolder.done.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewHolder.done.setTextColor(ContextCompat.getColor(ctx, R.color.white));
//                viewHolder.done.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(ctx, R.color.done)));
//                viewHolder.done.setText("Completed");
//                viewHolder.cancel.setVisibility(View.GONE);
//            }
//        });
//        viewHolder.cancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                viewHolder.cancel.setTextColor(ContextCompat.getColor(ctx, R.color.white));
//                viewHolder.cancel.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(ctx, R.color.cancel)));
//                viewHolder.cancel.setText("Cancelled");
//                viewHolder.done.setVisibility(View.GONE);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public static class viewHolder extends RecyclerView.ViewHolder{
        CardView cv;
        TextView name, price;
        ImageView pic;
        Button cancel, done;
        viewHolder(View itemView){
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            pic = (ImageView) itemView.findViewById(R.id.pic);
            final ViewGroup transitionsContainer = (ViewGroup) itemView.findViewById(R.id.cv);
            cancel = (Button) transitionsContainer.findViewById(R.id.done);
            done = (Button) transitionsContainer.findViewById(R.id.cancel);

            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TransitionManager.beginDelayedTransition(transitionsContainer);
                    done.setTextColor(ContextCompat.getColor(ctx, R.color.white));
                    done.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(ctx, R.color.done)));
                    done.setText("Completed");
                    cancel.setVisibility(View.GONE);
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TransitionManager.beginDelayedTransition(transitionsContainer);

                    done.setVisibility(View.GONE);
                    cancel.setTextColor(ContextCompat.getColor(ctx, R.color.white));
                    cancel.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(ctx, R.color.cancel)));
                    cancel.setText("Cancelled");

                }
            });
        }
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
