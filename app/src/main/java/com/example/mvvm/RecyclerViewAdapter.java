package com.example.mvvm;

import android.app.AlertDialog;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.mvvm.R;


import com.transitionseverywhere.extra.Scale;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import butterknife.BindView;
import butterknife.ButterKnife;


class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.viewHolder> {
    private List<FoodList> orders;
    static Context ctx;
    public static final int ITEM =0;
    public static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    private MutableLiveData<String> stringLd;

    public RecyclerViewAdapter(List<FoodList> orders, Context ctx){
        this.orders = orders;
        this.ctx = ctx;
//        this.binding = binding;
    }

    @NonNull
    @Override
    public viewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        switch (i){
//            case ITEM:
//                return new viewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rvlayout), viewGroup, false);
//            case LOADING:
//                return new FooterHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rvlayout), viewGroup, false);
//            default:
//                return null;
//        }

//        stringLd = new MutableLiveData<>();
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rvlayout, viewGroup, false);
        viewHolder vh = new viewHolder(view);
        final ViewGroup transitionsContainer = (ViewGroup) view.findViewById(R.id.cv);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
        FoodList foodList = orders.get(i);
        viewHolder.name.setText(foodList.getName());
        viewHolder.price.setText("Rs. " +foodList.getPrice());
    }

//    @Override
//    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
//        FoodList foodList = orders.get(i);
//        viewHolder.name.setText(foodList.getName());
//        viewHolder.price.setText(foodList.getPrice());
//    }

//    @Override
//    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
//        baseViewHolder.onBind(i);
//         FoodList foodList = orders.get(i);
//        viewHolder.name.setText(foodList.getName());
//        viewHolder.price.setText(foodList.getPrice());
//        stringLd.observe(binding.getLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//
//            }
//        });
//        stringLd.observe(binding.getLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//
//            }
//        });






    public void removeItem(int pos){
        orders.remove(pos);
        notifyItemRemoved(pos);

    }



//    @Override
//    public void onBindViewHolder(@NonNull viewHolder viewHolder, int i) {
//        final FoodList foodList = orders.get(i);
//        viewHolder.name.setText(foodList.getName());
//        viewHolder.price.setText(foodList.getPrice());
//        viewHolder.pic.setImageResource(foodList.getPicId());

//        viewHolder.name.setText(orders.get(i).name);
//        viewHolder.price.setText(orders.get(i).price);
//        viewHolder.pic.setImageResource(orders.get(i).picId);
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
//    }

    @Override
    public int getItemCount() {
        return orders.size();
    }



    public class viewHolder extends RecyclerView.ViewHolder{

        RelativeLayout cv, cvbg;
        TextView name;
        TextView price;
        ImageView pic;
        Button cancel, done, cancell, donee;

        public viewHolder(View itemView){
            super(itemView);
            cv = (RelativeLayout) itemView.findViewById(R.id.cv);
//            cvbg = (RelativeLayout) itemView.findViewById(R.id.cvbg);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
            final ViewGroup transitionsContainer = (ViewGroup) itemView.findViewById(R.id.cv);
            cancel = (Button) transitionsContainer.findViewById(R.id.done);
            done = (Button) transitionsContainer.findViewById(R.id.cancel);
            cancell = (Button) transitionsContainer.findViewById(R.id.cancell);
            donee = (Button) transitionsContainer.findViewById(R.id.donee);

            name.setText("momos");
            done.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    cancel.setVisibility(View.GONE);
                    done.setText("Completed");
                    done.setBackground(ctx.getDrawable(R.drawable.doneee));
                    done.setTextColor(ContextCompat.getColor(ctx, android.R.color.white));

//                    TransitionManager.beginDelayedTransition(transitionsContainer);
//                    done.setVisibility(View.GONE);
//                    cancel.setVisibility(View.GONE);
//
////                    done.setTextColor(ContextCompat.getColor(ctx, R.color.white));
////                    done.setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(ctx, R.color.done)));
////                    done.setText("Completed");
//                    donee.setVisibility(View.VISIBLE);
//
//                    donee.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.slide_down));
                }
            });
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    done.setVisibility(View.GONE);
                    cancel.setText("Cancelled");
                    cancel.setBackground(ctx.getDrawable(R.drawable.cancelll));
                    cancel.setTextColor(ContextCompat.getColor(ctx, android.R.color.white));


//                    TransitionManager.beginDelayedTransition(transitionsContainer);
//                        done.setVisibility(View.GONE);
//                        cancel.setVisibility(View.GONE);
//                        cancell.setVisibility(View.VISIBLE);
//
//                        cancell.startAnimation(AnimationUtils.loadAnimation(ctx, R.anim.slide_down));


                }
            });

        }




    }
    public  boolean resp = false;

    public boolean showAlert(){

        final AlertDialog.Builder builder =  new AlertDialog.Builder(ctx);
        builder.setCancelable(false);

        builder.setTitle("Cancel");
        builder.setMessage("Do you really want to Cancel?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resp = true;
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resp = false;
                dialog.dismiss();
            }
        });
        builder.create().show();
        return resp;
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
