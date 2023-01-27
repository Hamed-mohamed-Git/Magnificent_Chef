package com.example.magnificentchef.view.search.presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.magnificentchef.R;
import com.example.magnificentchef.model.remote.model.ingredient_model.MealsItem;

import java.util.List;

public class SearchAdapterIngredients extends RecyclerView.Adapter<SearchAdapterIngredients.ViewHolder> {

    private List<MealsItem>ingredients;
    MealsItem ingredient;
    OnSearchItemListener onSearchItemListener;

    public SearchAdapterIngredients(List<MealsItem> ingredients,OnSearchItemListener onSearchItemListener) {
        this.ingredients = ingredients;
        this.onSearchItemListener=onSearchItemListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.row_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ingredient=ingredients.get(position);
        holder.label.setText(ingredients.get(position).getStrIngredient());
        Glide.with(holder.image.getContext()).load(ingredient.getIngredientImage()).into(holder.image);
        holder.constraintLayout.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              onSearchItemListener.onSuccessClickItemListener(ingredients.get(position).getStrIngredient());

          }
      });
    }


    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView label;
        ImageView image;
        ConstraintLayout constraintLayout;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
          label=itemView.findViewById(R.id.label);
          image=itemView.findViewById(R.id.circle);
          constraintLayout=itemView.findViewById(R.id.search_float_card);



       }


    }




   }

