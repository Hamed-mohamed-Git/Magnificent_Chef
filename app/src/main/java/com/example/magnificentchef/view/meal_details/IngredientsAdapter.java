package com.example.magnificentchef.view.meal_details;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.magnificentchef.R;
import com.example.magnificentchef.view.common.Constants;
import com.example.magnificentchef.view.common.Ingredient;

import java.util.List;

public class IngredientsAdapter extends RecyclerView.Adapter<IngredientsAdapter.ViewHolder> {
    private List<Ingredient> ingredientList;
    private Context context;
    public IngredientsAdapter(List<Ingredient> ingredientList) {
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.meal_details_ingredient_card,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        holder.ingredientTextView.setText(ingredient.getIngredientName());
        holder.measureTextView.setText(ingredient.getMeasure());
        Glide.with(context).load(String.format(Constants.INGREDIENT_THUMBNAIL_URL, ingredient.getIngredientName())).into(holder.ingredientImageView);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ingredientImageView;
        private TextView measureTextView;
        private TextView ingredientTextView;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           initView(itemView);
       }
       void initView(View view){
           ingredientImageView = view.findViewById(R.id.ingredientImage);
           measureTextView = view.findViewById(R.id.measureTextView);
           ingredientTextView = view.findViewById(R.id.ingredientTextView);
       }
   }
}
