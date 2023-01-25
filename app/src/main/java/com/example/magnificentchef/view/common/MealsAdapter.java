package com.example.magnificentchef.view.common;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.magnificentchef.R;
import com.example.magnificentchef.model.remote.model.MealsItem;
import com.example.magnificentchef.view.base.BaseFragmentDirections;

import java.util.ArrayList;
import java.util.List;

public class MealsAdapter extends RecyclerView.Adapter<MealsAdapter.ViewHolder> {

    @LayoutRes
    private final int layoutResource;
    private List<MealsItem> mealList;
    private Context context;
    private NavController navController;
    private OnMealClickListener onMealClickListener;
    public MealsAdapter(@LayoutRes int layoutResource, @NonNull List<MealsItem> mealList, NavController navController,OnMealClickListener onMealClickListener) {
        this.mealList = new ArrayList<>();
        this.layoutResource = layoutResource;
        this.mealList = mealList;
        this.navController = navController;
        this.onMealClickListener=onMealClickListener;
    }

    @NonNull
    @Override
    public MealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(layoutResource,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MealsAdapter.ViewHolder holder, int position) {
        MealsItem meal = mealList.get(position);
        Glide.with(context).load(meal.getStrMealThumb()).into(holder.cardMealImageView);
        holder.cardMealNameTextView.setText(meal.getStrMeal());
        holder.mealCardCategoryTextView.setText(meal.getStrCategory());
        holder.cardMealCheckButton.setOnClickListener(buttonView -> {
            navController.navigate(BaseFragmentDirections.actionBaseFragmentToMealDetailsFragment(meal).setMealItem(meal));
        });
        holder.saveButtonImageView.setOnClickListener(buttonView -> {

            onMealClickListener.onMealFavouriteClickListener(meal);
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

     static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mealCardCategoryTextView;
        private TextView cardMealNameTextView;
        private Button cardMealCheckButton;
        private ImageView saveButtonImageView;
        private ImageView cardMealImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        void initView(View view){
            mealCardCategoryTextView = view.findViewById(R.id.Category_TV);
            cardMealNameTextView = view.findViewById(R.id.cardMealTextView);
            cardMealCheckButton = view.findViewById(R.id.cardMealCheckButton);
            saveButtonImageView = view.findViewById(R.id.saveButtonImageView);
            cardMealImageView = view.findViewById(R.id.card_Image);
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    public void setMealItemList(List<MealsItem> mealItemList){
        mealList=mealItemList;
        notifyDataSetChanged();
    }

}
