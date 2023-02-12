package com.example.magnificentchef.view.meal_details;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
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
        Glide.with(context)
                .asBitmap()
                .load(String.format(Constants.INGREDIENT_THUMBNAIL_URL, ingredient.getIngredientName()))
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.ingredientImageView.setImageBitmap(resource);
                        ColorStateList colorStateList = ColorStateList.valueOf(resource.getPixel(resource.getWidth()/2,
                                resource.getHeight()/2));
                        holder.ingredientView.setBackgroundTintList(colorStateList.withAlpha(35));
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView ingredientImageView;
        private TextView measureTextView;
        private TextView ingredientTextView;
        private View ingredientView;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
           initView(itemView);
       }
       void initView(View view){
           ingredientImageView = view.findViewById(R.id.ingredientImage);
           measureTextView = view.findViewById(R.id.measureTextView);
           ingredientTextView = view.findViewById(R.id.ingredientTextView);
           ingredientView = view.findViewById(R.id.ingredientView);
       }
   }
}
