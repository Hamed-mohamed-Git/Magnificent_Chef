package com.example.magnificentchef.view.search.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.magnificentchef.R;
import com.example.magnificentchef.view.search.model.Ingredients;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    public SearchAdapter(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    private List<Ingredients>ingredients;
    Ingredients ingredient;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from( parent.getContext()).inflate(R.layout.row_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ingredient=ingredients.get(position);
                //holder.image.setImageResource(ingredients.get(position).());
        holder.label.setText(ingredients.get(position).getStrIngredient());
        Glide.with(holder.image.getContext()).load(ingredient.getIngredientImage()).into(holder.image);

    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        View view;
        TextView label;
        ImageView image;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
          label=itemView.findViewById(R.id.label);
          image=itemView.findViewById(R.id.circle);


       }

        @Override
        public void onClick(View view) {

        }
    }




   }

