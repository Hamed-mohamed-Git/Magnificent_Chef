package com.example.magnificentchef.view.search.presenter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.magnificentchef.R;
import com.example.magnificentchef.view.search.model.Custom;

import java.util.List;

public class SearchAdapterCountres extends RecyclerView.Adapter<SearchAdapterCountres.ViewHolder> {
    private List<Custom>countries;
    private Custom country;
    private OnAreaItemClickListener onAreaItemClickListener;

    public SearchAdapterCountres(List<Custom> countries, OnAreaItemClickListener onAreaItemClickListener) {
        this.countries = countries;
        this.onAreaItemClickListener =onAreaItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from( parent.getContext()).inflate(R.layout.row_search,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        country =countries.get(position);
        holder.image.setImageResource(countries.get(position).getImage());
        holder.label.setText(countries.get(position).getLabel());
        //Glide.with(holder.image.getContext()).load(ingredient.getIngredientImage()).into(holder.image);
        holder.constraintLayout.setOnClickListener(view -> onAreaItemClickListener.onClickItemListener(holder.label.getText().toString()));



    }

    @Override
    public int getItemCount() {
        return countries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        View view;
        TextView label;
        ImageView image;
        ConstraintLayout constraintLayout;

       public ViewHolder(@NonNull View itemView) {
           super(itemView);
          label=itemView.findViewById(R.id.label);
          image=itemView.findViewById(R.id.circle);
           constraintLayout=itemView.findViewById(R.id.search_float_card);


       }

        @Override
        public void onClick(View view) {
           // onSearchItemListener.onSuccessClickItemListener(countries.get(position).getStrIngredient());


        }
    }




   }

