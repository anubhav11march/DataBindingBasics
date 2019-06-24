package com.example.mvvm;

import android.content.ClipData;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.mvvm.R;

import com.transitionseverywhere.extra.Scale;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

class RecyclerViewAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private List<FoodList> orders;
    static Context ctx;
    public static final int ITEM =0;
    public static final int LOADING = 1;
    private boolean isLoadingAdded = false;

    RecyclerViewAdapter(List<FoodList> orders, Context ctx){
        this.orders = orders;
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
//        switch (i){
//            case ITEM:
//                return new viewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rvlayout), viewGroup, false);
//            case LOADING:
//                return new FooterHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rvlayout), viewGroup, false);
//            default:
//                return null;
//        }

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.rvlayout, viewGroup, false);
        viewHolder vh = new viewHolder(view);
        final ViewGroup transitionsContainer = (ViewGroup) view.findViewById(R.id.cv);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder baseViewHolder, int i) {
        baseViewHolder.onBind(i);
        final FoodList foodList = orders.get(i);
        viewHolder.name.setText(foodList.getName());
        viewHolder.price.setText(foodList.getPrice());
    }

    @Override
    public int getItemViewType(int position) {
        return orders == null ? 0 : orders.size();
    }

    public void add(FoodList response){
        orders.add(response);
        notifyItemInserted(orders.size()-1);
    }

    public void addAll(List<FoodList> items){
        for(FoodList response: items){
            add(response);
        }
    }

    public void addLoading(){
        isLoadingAdded = true;
//        add(new FoodList());
    }

    private void remove(FoodList foodList){
        int pos = orders.indexOf(foodList);
        if(pos > -1){
            orders.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    private void removeLoading(){
        isLoadingAdded = false;
        int pos = orders.size()-1;
        FoodList foodList = getItem(pos);
        if(foodList != null){
            orders.remove(pos);
            notifyItemRemoved(pos);
        }
    }

    public void clear(){
        while (getItemCount() > 0){
            remove(getItem(0));
        }
    }

    FoodList getItem(int pos){
        return orders.get(pos);
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



    public static class viewHolder extends BaseViewHolder{
//        public final ItemBinding binding;
        CardView cv;
        static TextView name;
        static TextView price;
        ImageView pic;
        Button cancel, done;
        viewHolder(View itemView){
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            name = (TextView) itemView.findViewById(R.id.name);
            price = (TextView) itemView.findViewById(R.id.price);
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

//        public viewHolder(ItemBinding itemBinding){
//            super(itemBinding.getRoot());
//            this.binding = itemBinding;
//        }
//
//        public void bind(ClipData.Item item){
//
//        }

        @Override
        public void clear() {

        }
    }

    public class FooterHolder extends BaseViewHolder {
        ProgressBar mProgressBar;


        FooterHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void clear() {

        }

    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
