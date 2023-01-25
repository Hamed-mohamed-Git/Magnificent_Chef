package com.example.magnificentchef.view.save;

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
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.magnificentchef.R;
import com.example.magnificentchef.model.local.favourite_meal.FavouriteMeal;
import com.example.magnificentchef.model.local.plan_meal.PlanMeal;
import com.example.magnificentchef.view.plan.presenter.ClickAddPlanListener;

import java.util.ArrayList;
import java.util.List;

public class FavouriteMealsAdapter extends RecyclerView.Adapter<FavouriteMealsAdapter.ViewHolder> {

    @LayoutRes
    private final int layoutResource;
    private List<FavouriteMeal> mealList;
    private Context context;
    private ClickAddPlanListener clickAddPlanListener;

    public FavouriteMealsAdapter(@LayoutRes int layoutResource, @NonNull List<FavouriteMeal> mealList, ClickAddPlanListener clickAddPlanListener) {
        this.mealList = new ArrayList<>();
        this.layoutResource = layoutResource;
        this.mealList = mealList;
        this.clickAddPlanListener = clickAddPlanListener;
    }

    @NonNull
    @Override
    public FavouriteMealsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(layoutResource,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteMealsAdapter.ViewHolder holder, int position) {
        FavouriteMeal meal = mealList.get(position);
        Glide.with(context).load(meal.getImage()).into(holder.cardMealImageView);
        holder.cardMealNameTextView.setText(meal.getName());
        holder.mealCardCategoryTextView.setText(meal.getCategory());
        holder.cardMealAddButton.setOnClickListener(buttonView -> {
            clickAddPlanListener.onPlannedMealClick(meal);
        });
        holder.getCardDelete.setOnClickListener(buttonView ->{
            clickAddPlanListener.onDeleteMealClick(meal);
            mealList.remove(meal);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return mealList.size();
    }

     static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mealCardCategoryTextView;
        private TextView cardMealNameTextView;
        private Button cardMealAddButton;
        private ImageView cardMealImageView;
        private ImageView getCardDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            initView(itemView);
        }

        void initView(View view){
            mealCardCategoryTextView = view.findViewById(R.id.Category_TV);
            cardMealNameTextView = view.findViewById(R.id.cardMealTextView);
            cardMealAddButton = view.findViewById(R.id.cardMealAddButton);
            cardMealImageView = view.findViewById(R.id.card_Image);
            getCardDelete = view.findViewById(R.id.deleteImageView);
        }

    }
    @SuppressLint("NotifyDataSetChanged")
    public void setMealItemList(List<FavouriteMeal> mealItemList){
        mealList=mealItemList;
        notifyDataSetChanged();
    }

}
